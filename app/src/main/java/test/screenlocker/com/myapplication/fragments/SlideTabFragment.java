package test.screenlocker.com.myapplication.fragments;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import test.screenlocker.com.myapplication.FAQ;
import test.screenlocker.com.myapplication.Preferences;
import test.screenlocker.com.myapplication.R;
import test.screenlocker.com.myapplication.Settings;
import test.screenlocker.com.myapplication.WelcomeActivity;
import test.screenlocker.com.myapplication.utils.PreferencesConstants;
import test.screenlocker.com.myapplication.utils.PreferencesHandler;

import static android.app.Activity.RESULT_OK;

public class SlideTabFragment extends Fragment {
    View rootView;
    //int position;
    TextView t1, t2, t3, t4;
    Button btnImage;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    Bitmap btmap;
    ImageView imageView;
    PreferencesHandler prefs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        t1 = (TextView) rootView.findViewById(R.id.set);
        t2 = (TextView) rootView.findViewById(R.id.man);
        t3 = (TextView) rootView.findViewById(R.id.pref);
        t4 = (TextView) rootView.findViewById(R.id.FAQ);
        imageView = (ImageView) rootView.findViewById(R.id.imageView2);
        btnImage = (Button) rootView.findViewById(R.id.button);
        String u = prefs.getStringPreferences(PreferencesConstants.image);
        if (!u.equals("")) {
            btmap = decodeBase64(u);
            imageView.setImageBitmap(btmap);
        }

        btnImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //  Intent i = new Intent(
                //         Intent.ACTION_PICK,
                //         android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // startActivityForResult(i, RESULT_LOAD_IMAGE);

                final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].equals("Take Photo")) {

                            requestForCameraPermission("Take Photo");
                        } else if (items[item].equals("Choose from Gallery")) {

                            requestForCameraPermission("Choose from Gallery");
                        } else if (items[item].equals("Cancel")) {

                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }

        });
        t1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Settings.class);
                startActivity(intent);
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                startActivity(intent);
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Preferences.class);
                startActivity(intent);
            }
        });

        t4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FAQ.class);
                startActivity(intent);
            }
        });
        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);
                PreferencesHandler.updatePreferences(PreferencesConstants.image,encodeTobase64(photo));
            }
        } else if (requestCode == 1) {

            Log.e("", "image from gallery ");
            if (resultCode == RESULT_OK) {

                try {
                    Uri selectedImageUri = data.getData();
                    String picturePath = getPath(selectedImageUri);
                    Bitmap bitmapSelectedImage = BitmapFactory.decodeFile(picturePath);
                    imageView.setImageBitmap(bitmapSelectedImage);
                    PreferencesHandler.updatePreferences(PreferencesConstants.image,encodeTobase64(bitmapSelectedImage));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;

    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }
    private int REQUEST_CAMERA_PERMISSION = 100;
    final private int REQUEST_EXTERNAL_PERMISSIONS = 124;

    public void requestForCameraPermission(String request) {

        int rCode = REQUEST_EXTERNAL_PERMISSIONS;

        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();

        if (request.equals("Take Photo")) {
            rCode = REQUEST_CAMERA_PERMISSION;
            if (!addPermission(permissionsList, android.Manifest.permission.CAMERA))
                permissionsNeeded.add("Camera");
        } else
            rCode = REQUEST_EXTERNAL_PERMISSIONS;

        if (!addPermissionGal(permissionsList, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Write External Storage");
        if (!addPermissionGal(permissionsList, android.Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add("Read External Storage");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);

                final int finalRCode = rCode;
                createDialog(getActivity(), message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(getActivity(),
                                permissionsList.toArray(new String[permissionsList.size()]),
                                finalRCode);
                    }
                });

            }
            ActivityCompat.requestPermissions(getActivity(),
                    permissionsList.toArray(new String[permissionsList.size()]),
                    rCode);
        } else {

            if (rCode == REQUEST_CAMERA_PERMISSION)
                dispatchTakePictureIntent();
            else
                openGallery();
        }
    }

    private boolean addPermissionGal(List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                // Show permission rationale
                return false;
            }
        }
        return true;
    }

    public Dialog createDialog(Context context, String messageId,
                               DialogInterface.OnClickListener onClick) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(messageId)
                .setPositiveButton(context.getString(R.string.activity_dialog_accept), onClick)
                .setNegativeButton(context.getString(R.string.activity_dialog_decline),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
        return builder.create();

    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ActivityCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    // New function for captureImageIntent
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, 0);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getActivity().getApplicationContext().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

}
