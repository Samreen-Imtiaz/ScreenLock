package test.screenlocker.com.myapplication;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class User_slide extends Activity {

    private EditText etEmailAddrss;
    private EditText etPhoneNumber;
    private Button btnSubmit, btnskip;

    SharedPreferences pref;
    public static final String mypreference = "mypref";
    public static final String Number = "numberKey";
    public static final String Email = "emailKey";

    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_slide);

      /*  new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(User_slide.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT); */

        ////////////////////////////////////////////////////////////
        pref = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (pref.contains(Number)) {
            etPhoneNumber.setText(pref.getString(Number, ""));
        }
        if (pref.contains(Email)) {
            etEmailAddrss.setText(pref.getString(Email, ""));

        }
        ////////////////////////////////////////////////////////////

        etEmailAddrss= (EditText) findViewById(R.id.editText7);
        etEmailAddrss.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validate_profile.isEmailAddress(etEmailAddrss, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        etPhoneNumber= (EditText) findViewById(R.id.editText6);
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validate_profile.isPhoneNumber(etPhoneNumber, false);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        btnskip = (Button) findViewById(R.id.button3);
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
                    ////////////////////////////////////////////////////////
                /*   String n = etPhoneNumber.getText().toString();
                    String e = etEmailAddrss.getText().toString();
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(Number, n);
                    editor.putString(Email, e);
                    editor.commit();*/
                    //////////////////////////////////////
                    submitForm();
                    startActivity(new Intent(User_slide.this, MainActivity.class));
                    finish();
                }
                else
                    Toast.makeText(User_slide.this, "Please fill the fields properly!", Toast.LENGTH_LONG).show();
            }
        });


    }
    private void submitForm() {
         Toast.makeText(this, "Profile Created!", Toast.LENGTH_LONG).show();
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validate_profile.isEmailAddress(etEmailAddrss, true)) ret = false;
        if (!Validate_profile.isPhoneNumber(etPhoneNumber, true)) ret = false;

        return ret;
    }
}
