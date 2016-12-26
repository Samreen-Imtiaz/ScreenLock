package com.manusunny.pinlock;

import android.os.Bundle;



public abstract class SetPinActivity extends BasePinActivity {

    private String firstPin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLabel(getString(R.string.message_enter_new_pin));
        disableForgotButton();
    }


    @Override
    public final void onCompleted(String pin) {
        resetStatus();
        if ("".equals(firstPin)) {
            firstPin = pin;
            setLabel(getString(R.string.message_confirm_pin));
        } else {
            if (pin.equals(firstPin)) {
                onPinSet(pin);
                setResult(SUCCESS);
                finish();
            } else {
                setLabel(getString(R.string.message_pin_mismatch));
                firstPin = "";
            }
        }
        resetStatus();
    }
    public abstract void onPinSet(String pin);

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(CANCELLED);
        finish();
    }
}
