package test.screenlocker.com.myapplication.PinLock;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.Patterns;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.github.orangegangsters.lollipin.lib.managers.AppLockActivity;

import test.screenlocker.com.myapplication.R;
import test.screenlocker.com.myapplication.utils.GMailSender;
import test.screenlocker.com.myapplication.utils.GPSTracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import uk.me.lewisdeane.ldialogs.BaseDialog;
import uk.me.lewisdeane.ldialogs.CustomDialog;
@SuppressLint("SimpleDateFormat")
public class CustomPinActivity extends AppLockActivity {
    String fullpath;
    String stringLatitude;
    String stringLongitude;
    GPSTracker gpsTracker;
    String date_and_time;
    String email_add;
    SmsManager smsManager = SmsManager.getDefault();
    private SurfaceHolder sHolder;
    static Camera  mCamera=null;
    private Parameters parameters;

    SharedPreferences pref, sharedPreferences;
    public static final String mypreference = "mypref";
    public static final String Number = "numberKey";
    public static final String Email = "emailKey";

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        mCamera = Camera.open(findFrontFacingCamera());

        sharedPreferences =getSharedPreferences("SmartPinLock", Context.MODE_PRIVATE);;

        SurfaceView sv = new SurfaceView(this);
//////

        try {
            mCamera.setPreviewDisplay(sv.getHolder());
            parameters = mCamera.getParameters();


            mCamera.setParameters(parameters);
            mCamera.startPreview();



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        sHolder = sv.getHolder();

        sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void showForgotDialog() {
        Resources res = getResources();
        // Create the builder with required paramaters - Context, Title, Positive Text
        CustomDialog.Builder builder = new CustomDialog.Builder(this,
                res.getString(R.string.activity_dialog_title),
                res.getString(R.string.activity_dialog_accept));
        builder.content(res.getString(R.string.activity_dialog_content));
        builder.negativeText(res.getString(R.string.activity_dialog_decline));

        //Set theme
        builder.darkTheme(false);
        builder.typeface(Typeface.SANS_SERIF);
        builder.positiveColor(res.getColor(R.color.light_blue_500)); // int res, or int colorRes parameter versions available as well.
        builder.negativeColor(res.getColor(R.color.light_blue_500));
        builder.rightToLeft(false); // Enables right to left positioning for languages that may require so.
        builder.titleAlignment(BaseDialog.Alignment.CENTER);
        builder.buttonAlignment(BaseDialog.Alignment.CENTER);
        builder.setButtonStacking(false);

        //Set text sizes
        builder.titleTextSize((int) res.getDimension(R.dimen.activity_dialog_title_size));
        builder.contentTextSize((int) res.getDimension(R.dimen.activity_dialog_content_size));
        builder.positiveButtonTextSize((int) res.getDimension(R.dimen.activity_dialog_positive_button_size));
        builder.negativeButtonTextSize((int) res.getDimension(R.dimen.activity_dialog_negative_button_size));

        //Build the dialog.
        CustomDialog customDialog = builder.build();
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.setClickListener(new CustomDialog.ClickListener() {
            @Override
            public void onConfirmClick() {
                Toast.makeText(getApplicationContext(), "Yes", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelClick() {
                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
            }
        });

        // Show the dialog.
        customDialog.show();

    }

    @Override
    public void onPinFailure(int attempts) {
        // if(!sharedPreferences.getBoolean("SmartPinLock", false)) {
        //     SharedPreferences.Editor editor = sharedPreferences.edit();
        //     editor.putBoolean("SmartPinLock", true);
        //     editor.commit();
        gpsTracker = new GPSTracker(this);
        stringLatitude = String.valueOf(gpsTracker.latitude);
        stringLongitude = String.valueOf(gpsTracker.longitude);
        //getting current date and time
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        date_and_time = dateFormat.format(cal.getTime());
        //getting email address
        getEmailId();
        //getting image path
        getLastImageId();
        //getting current location params
        if (attempts == 3) {

            Toast.makeText(getApplicationContext(), "Wrong Pin, Try Again!", Toast.LENGTH_SHORT).show();
            new SaveAsyncTask().execute();
        }
        //   }
        //   else

    }

    @Override
    public void onPinSuccess(int attempts) {

    }
    private void getEmailId() {
        // TODO Auto-generated method stub
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches())
            {
                email_add=account.name;
                break;
            }
        }
    }
    private int getLastImageId(){
        final String[] imageColumns = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA };
        final String imageOrderBy = MediaStore.Images.Media._ID+" DESC";
        @SuppressWarnings("deprecation")
        Cursor imageCursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, null, null, imageOrderBy);
        if(imageCursor.moveToFirst()){
            int id = imageCursor.getInt(imageCursor.getColumnIndex(MediaStore.Images.Media._ID));
            fullpath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            return id;
        }else{
            return 0;
        }
    }

    class SaveAsyncTask extends AsyncTask<Void, Integer, Boolean> {


        private Boolean status = false;

        @Override
        protected Boolean doInBackground(Void... arg0) {
            try {
                ArrayList<Uri> uList = new ArrayList<Uri>();
                uList.add(Uri.parse(fullpath));
                mCamera.takePicture(null, null, mCall);
                try {
                    if (isNetworkConnected()) {
                        if (pref.contains(Email)) {
                            GMailSender sender = new GMailSender("intruderalert.smartAppLocker@gmail.com",
                                    "sm@rt@pp");
                            Log.d("TAG: ", "Mail SENT");
                            sender.sendMail("Alert SmartAppLocker!", "Violation detected! \nLocation: http://maps.google.com/?q=" + stringLatitude + "," + stringLongitude + "\n" + ""
                                            + "Date and Time: " + date_and_time
                                    , "intruderalert.smartAppLocker@gmail.com", String.valueOf(pref.getString(Email, "")), uList);
                        }
                    } else {
                        if (pref.contains(Number)) {
                            smsManager.sendTextMessage(String.valueOf(pref.getString(Number, "")), null,
                                    "Alert SmartAppLocker! Violation detected!" + "\n" + "" + "Location:" + "http://maps.google.com/?q=" + stringLatitude
                                            + "," + stringLongitude + "\n" + ""
                                            + "Date and Time: " + date_and_time, null, null);
                        }
                    }
                }catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return status;
            } catch (Exception e) {
                e.printStackTrace();
                return Boolean.FALSE;
            }

        }

        protected void onPreExecute() {

        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Boolean result) {
            // result is the value returned from doInBackground

        }

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }


    @SuppressLint("NewApi") private int findFrontFacingCamera() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;

                break;
            }
        }
        return cameraId;
    }
    Camera.PictureCallback mCall = new Camera.PictureCallback()
    {

        public void onPictureTaken(byte[] data, Camera camera)
        {


            File pictureFile = getOutputMediaFile();
            try{

                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                //  Toast toast = Toast.makeText(CustomPinActivity.this, "Picture saved: " + pictureFile.getName(), Toast.LENGTH_LONG);
                //  toast.show();
                MediaScannerConnection.scanFile(CustomPinActivity.this, new String[]{pictureFile.getPath()}, new String[]{"image/jpeg"}, null);

            } catch (FileNotFoundException e){
                Log.d("CAMERA", e.getMessage());
            } catch (IOException e){
                Log.d("CAMERA", e.getMessage());
            }

        }
    };
    @SuppressLint("SdCardPath") private static File getOutputMediaFile() {

        File mediaStorageDir = new File("/sdcard/", "SmartAppLocker");
        if (!mediaStorageDir.exists()) {

            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }


        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;

        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCamera != null) {
            // mCamera.release();
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCamera != null) {
            // mCamera.release();
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCamera != null) {
            // mCamera.release();
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }
}
