package com.manusunny.pinlock;

import android.os.Bundle;

public abstract class ConfirmPinActivity extends BasePinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLabel(getString(R.string.message_enter_pin));
    }

    @Override
    public final void onCompleted(String pin) {
        resetStatus();
        if (isPinCorrect(pin)) {
            setResult(SUCCESS);
            finish();
        } else {
            setLabel(getString(R.string.message_invalid_pin));
        }
    }
    public abstract boolean isPinCorrect(String pin);

    @Override
    public abstract void onForgotPin();


    @Override
    public void onBackPressed() {
        setResult(CANCELLED);
        finish();
    }
}
