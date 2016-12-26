

package com.manusunny.pinlock;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.manusunny.pinlock.components.Keypad;
import com.manusunny.pinlock.components.StatusDots;



public abstract class BasePinActivity extends Activity implements PinListener {
    private TextView label;
    private StatusDots statusDots;
    private TextView forgetButton;
    private TextView cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        Keypad keypad = (Keypad) findViewById(R.id.keypad);
        keypad.setPinListener(this);

        label = (TextView) findViewById(R.id.label);
        statusDots = (StatusDots) findViewById(R.id.statusDots);

        setupButtons();
        setupStyles();
    }

    private void setupButtons() {
        cancelButton = (TextView) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(CANCELLED);
                finish();
            }
        });
        forgetButton = (TextView) findViewById(R.id.forgotPin);
        forgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onForgotPin();
                setResult(FORGOT);
                finish();
            }
        });
    }

    private void setupStyles() {
        TypedArray styledAttributes = obtainStyledAttributes(R.style.PinLock, R.styleable.PinLock);

        final int layoutBackground = styledAttributes.getColor(R.styleable.PinLock_backgroundColor, Color.WHITE);
        View layout = findViewById(R.id.pinLockLayout);
        layout.setBackgroundColor(layoutBackground);

        final int cancelForgotTextSize = styledAttributes.getInt(R.styleable.PinLock_cancelForgotTextSize, 20);
        cancelButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, cancelForgotTextSize);
        forgetButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, cancelForgotTextSize);


        final int cancelForgotTextColor = styledAttributes.getColor(R.styleable.PinLock_cancelForgotTextColor, Color.BLACK);
        cancelButton.setTextColor(cancelForgotTextColor);
        if(forgetButton.isEnabled()) {
            forgetButton.setTextColor(cancelForgotTextColor);
        } else {
            forgetButton.setTextColor(Color.parseColor("#a9abac"));
        }

        final int infoTextSize = styledAttributes.getInt(R.styleable.PinLock_infoTextSize, 20);
        final int infoTextColor = styledAttributes.getColor(R.styleable.PinLock_infoTextColor, Color.BLACK);
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, infoTextSize);
        label.setTextColor(infoTextColor);
    }

    public void disableForgotButton(){
        forgetButton.setEnabled(false);
        forgetButton.setTextColor(Color.parseColor("#a9abac"));
    }
    public void setLabel(String text) {
        label.setText(text);
    }
    public void resetStatus() {
        statusDots.initialize();
    }
    @Override
    public abstract void onCompleted(String pin);
    @Override
    public void onPinValueChange(int length) {
        statusDots.updateStatusDots(length);
    }
    @Override
    public void onForgotPin() {
    }
}
