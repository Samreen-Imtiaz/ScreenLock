package test.screenlocker.com.myapplication;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import test.screenlocker.com.myapplication.utils.PreferencesConstants;
import test.screenlocker.com.myapplication.utils.PreferencesHandler;

public class User_slide extends Activity {

    private EditText etEmailAddrss;
    private EditText etPhoneNumber;
    private Button btnSubmit;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_slide);
        TextView textView = (TextView) findViewById(R.id.textview);
        ImageButton imageButton = (ImageButton) findViewById(R.id.left_button_header);
        imageButton.setVisibility(View.INVISIBLE);
        textView.setText("User Profile");
        imageView = (ImageView) findViewById(R.id.imageView3);
        etEmailAddrss = (EditText) findViewById(R.id.editText7);
        etEmailAddrss.setText(PreferencesHandler.getStringPreferences(PreferencesConstants.email));
        etEmailAddrss.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validate_profile.isEmailAddress(etEmailAddrss, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        etPhoneNumber = (EditText) findViewById(R.id.editText6);
        etPhoneNumber.setText(PreferencesHandler.getStringPreferences(PreferencesConstants.phone));
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validate_profile.isPhoneNumber(etPhoneNumber, false);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        TextView btnskip = (TextView) findViewById(R.id.textView13);
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

                if (checkValidation()) {
                    submitForm();
                } else
                    Toast.makeText(User_slide.this, "Please fill the fields properly!", Toast.LENGTH_LONG).show();
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(User_slide.this);
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
    }

    private void submitForm() {

        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        PreferencesHandler.updatePreferences(PreferencesConstants.email, etEmailAddrss.getText().toString().trim());
        PreferencesHandler.updatePreferences(PreferencesConstants.phone, etPhoneNumber.getText().toString().trim());
        PreferencesHandler.updatePreferences(PreferencesConstants.image, encodeTobase64(bitmap));
        startActivity(new Intent(this, MainActivity.class));
        finish();
        Toast.makeText(this, "Profile Created!", Toast.LENGTH_LONG).show();
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
                createDialog(User_slide.this, message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(User_slide.this,
                                permissionsList.toArray(new String[permissionsList.size()]),
                                finalRCode);
                    }
                });

            }
            ActivityCompat.requestPermissions(User_slide.this,
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
        if (ContextCompat.checkSelfPermission(User_slide.this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale(User_slide.this, permission)) {
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
        if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
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

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validate_profile.isEmailAddress(etEmailAddrss, true)) ret = false;
        if (!Validate_profile.isPhoneNumber(etPhoneNumber, true)) ret = false;

        return ret;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);
            }
        } else if (requestCode == 1) {

            Log.e("", "image from gallery ");
            if (resultCode == RESULT_OK) {

                try {
                    Uri selectedImageUri = data.getData();
                    String picturePath = getPath(selectedImageUri);
                    Bitmap bitmapSelectedImage = BitmapFactory.decodeFile(picturePath);
                    imageView.setImageBitmap(bitmapSelectedImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
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


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

}
