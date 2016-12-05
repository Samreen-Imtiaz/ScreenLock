package test.screenlocker.com.myapplication;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;

import test.screenlocker.com.myapplication.utils.PreferencesConstants;
import test.screenlocker.com.myapplication.utils.PreferencesHandler;

public class User_slide extends Activity {

    private EditText etEmailAddrss;
    private EditText etPhoneNumber;
    private Button btnSubmit, btnskip;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    Bitmap btmap;
    ImageView imageView;
    PreferencesHandler prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_slide);
        prefs.updatePreferences("isRegistered ",true);

        imageView = (ImageView) findViewById(R.id.imageView3);
        etEmailAddrss= (EditText) findViewById(R.id.editText7);
        etEmailAddrss.setText(PreferencesHandler.getStringPreferences(PreferencesConstants.email));
        etEmailAddrss.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validate_profile.isEmailAddress(etEmailAddrss, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        etPhoneNumber= (EditText) findViewById(R.id.editText6);
        etPhoneNumber.setText(PreferencesHandler.getStringPreferences(PreferencesConstants.phone));
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validate_profile.isPhoneNumber(etPhoneNumber, false);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        btnskip = (Button) findViewById(R.id.button5);
        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(User_slide.this, MainActivity.class));
                finish();
            }
        });


        btnSubmit = (Button) findViewById(R.id.button4);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( checkValidation () )
                {
                    submitForm();
                    startActivity(new Intent(User_slide.this, MainActivity.class));
                    finish();
                }
                else
                    Toast.makeText(User_slide.this, "Please fill the fields properly!", Toast.LENGTH_LONG).show();
            }
        });

        String u=prefs.getStringPreferences(PreferencesConstants.image);
        btmap=decodeBase64(u);
        imageView.setImageBitmap(btmap);

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

              //  Intent i = new Intent(
                  //      Intent.ACTION_PICK,
                   //     android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

              //  startActivityForResult(i, RESULT_LOAD_IMAGE);

               final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(User_slide.this);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].equals("Take Photo")) {
                            RESULT_LOAD_IMAGE = 1;
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, REQUEST_CAMERA);

                        } else if (items[item].equals("Choose from Gallery")) {
                            RESULT_LOAD_IMAGE = 1;
                            Intent intent = new Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent,SELECT_FILE);

                        } else if (items[item].equals("Cancel")) {
                            RESULT_LOAD_IMAGE = 0;
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }

        });
    }
    private void submitForm() {
        PreferencesHandler.updatePreferences(PreferencesConstants.email, etEmailAddrss.getText().toString().trim());
        PreferencesHandler.updatePreferences(PreferencesConstants.phone, etPhoneNumber.getText().toString().trim());

        Toast.makeText(this, "Profile Created!", Toast.LENGTH_LONG).show();
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

            Cursor cursor = getContentResolver().query(selectedImage,
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
            prefs.updatePreferences(PreferencesConstants.image, encodeTobase64(bmp));

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
               getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

}
