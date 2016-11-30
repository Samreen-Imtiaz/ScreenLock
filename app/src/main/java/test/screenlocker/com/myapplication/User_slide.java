package test.screenlocker.com.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import test.screenlocker.com.myapplication.utils.PreferencesConstants;
import test.screenlocker.com.myapplication.utils.PreferencesHandler;

public class User_slide extends Activity {

    private EditText etEmailAddrss;
    private EditText etPhoneNumber;
    private Button btnSubmit, btnskip;

    PreferencesHandler prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_slide);
        prefs.updatePreferences("isRegistered ",true);


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
}
