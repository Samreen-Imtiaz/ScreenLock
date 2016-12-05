package test.screenlocker.com.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;

import test.screenlocker.com.myapplication.listener.SliderListener;
import test.screenlocker.com.myapplication.utils.PreferencesConstants;
import test.screenlocker.com.myapplication.utils.PreferencesHandler;

import static android.app.Activity.RESULT_OK;

public class UserFregment extends Fragment {
    private EditText etEmailAddrss;
    private EditText etPhoneNumber;
    private Button btnSubmit, btnclear,btnImage;
    TextView heading;
    View view;
    SlidingPaneLayout mSlidingLayout;
    ImageButton slidingPaneButton;
    ImageView imageView;
    PreferencesHandler prefs;
    Bitmap btmap;
    private static int RESULT_LOAD_IMAGE = 1;
    String u;
    public UserFregment() {
    }

    public static UserFregment newInstance() {
        Bundle args = new Bundle();
        UserFregment sampleFragment = new UserFregment();
        sampleFragment.setArguments(args);
        return sampleFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
       if(etEmailAddrss==null && etPhoneNumber==null) {
        //   new AlertDialog.Builder(getActivity()).setTitle("Alert!").setMessage("Insert Phone number and Email ID.").setIcon(R.drawable.icon).setNeutralButton("OK", null).show();
       }
        initView();
        setListeners();
       // u=prefs.getStringPreferences(PreferencesConstants.image);
       // btmap=decodeBase64(u);
       // imageView.setImageBitmap(btmap);

        return view;
    }

    public void initView() {
        etPhoneNumber = (EditText) view.findViewById(R.id.editText4);
        etEmailAddrss = (EditText) view.findViewById(R.id.editText5);
        btnclear = (Button) view.findViewById(R.id.button3);
        btnSubmit = (Button) view.findViewById(R.id.button2);
       imageView = (ImageView) view.findViewById(R.id.imageView2);
        btnImage = (Button) view.findViewById(R.id.image);
        heading = (TextView) view.findViewById(R.id.textview);
        heading.setText("User Profile");

       //  u=prefs.getStringPreferences(PreferencesConstants.image);
       //  btmap=decodeBase64(u);
        // imageView.setImageBitmap(btmap);


        etPhoneNumber.setText(PreferencesHandler.getStringPreferences(PreferencesConstants.phone));
        etEmailAddrss.setText(PreferencesHandler.getStringPreferences(PreferencesConstants.email));


        mSlidingLayout = (SlidingPaneLayout) getActivity().findViewById(R.id.sliding_pane_layout);
        mSlidingLayout.setPanelSlideListener(SliderListener.getInstance(getActivity()));
        slidingPaneButton = (ImageButton) view.findViewById(R.id.left_button_header);
        slidingPaneButton.setVisibility(View.VISIBLE);
        slidingPaneButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.top_left_menu_icon));


    }

    public void setListeners() {
        etEmailAddrss.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validate_profile.isEmailAddress(etEmailAddrss, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validate_profile.isPhoneNumber(etPhoneNumber, false);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            etEmailAddrss.setText("");
                etPhoneNumber.setText("");
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkValidation()) {
                    submitForm();
                } else
                    Toast.makeText(getActivity(), "Please fill the fields properly!", Toast.LENGTH_LONG).show();
            }
        });

        slidingPaneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlidingLayout.openPane();
            }
        });

      /*  btnImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }

        });
*/

    }

    private void submitForm() {
        PreferencesHandler.updatePreferences(PreferencesConstants.email, etEmailAddrss.getText().toString().trim());
        PreferencesHandler.updatePreferences(PreferencesConstants.phone, etPhoneNumber.getText().toString().trim());

        Toast.makeText(getActivity(), "Profile Created!", Toast.LENGTH_SHORT).show();

    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validate_profile.isEmailAddress(etEmailAddrss, true)) ret = false;
        if (!Validate_profile.isPhoneNumber(etPhoneNumber, true)) ret = false;

        return ret;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            imageView.setImageBitmap(bmp);
               //  prefs.updatePreferences(PreferencesConstants.image, encodeTobase64(bmp));

        }

    }

    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;

    }
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
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