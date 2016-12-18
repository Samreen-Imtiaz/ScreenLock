package com.manusunny.pinlock.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.manusunny.pinlock.PinListener;
import com.manusunny.pinlock.R;

public class KeypadAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private final TypedArray styledAttributes;
    private final Context context;
    private PinListener pinListener;


    public KeypadAdapter(Context context, TypedArray styledAttributes, PinListener pinListener) {
        this.styledAttributes = styledAttributes;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.pinListener = pinListener;
    }

    @Override
    public int getCount() {
        return 11;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (position == 10)
            return 0;
        return ((position + 1) % 10);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Button view;
        if (convertView == null) {
            view = (Button) inflater.inflate(R.layout.pin_input_button, null);
        } else {
            view = (Button) convertView;
        }

        setStyle(view);
        setValues(position, view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int duration = styledAttributes.getInt(R.styleable.PinLock_keypadClickAnimationDuration, 100);
                TransitionDrawable transition = (TransitionDrawable) v.getBackground();
                transition.startTransition(duration);

                final int pinLength = styledAttributes.getInt(R.styleable.PinLock_pinLength, 4);
                Button key = (Button) v;
                final String keyText = key.getText().toString();
                String currentPin = Keypad.onKeyPress(keyText);
                int currentPinLength = currentPin.length();
                vibrateIfEnabled();
                pinListener.onPinValueChange(currentPinLength);
                if (currentPinLength == pinLength) {
                    pinListener.onCompleted(currentPin);
                    Keypad.resetPin();
                }
                transition.reverseTransition(duration);
            }
        });
        return view;
    }


    private void vibrateIfEnabled() {
        final boolean enabled = styledAttributes.getBoolean(R.styleable.PinLock_vibrateOnClick, false);
        if(enabled){
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            final int duration = styledAttributes.getInt(R.styleable.PinLock_vibrateDuration, 20);
            v.vibrate(duration);
        }
    }



    private void setStyle(Button view) {
        final int textSize = styledAttributes.getInt(R.styleable.PinLock_keypadTextSize, 12);
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

        final int color = styledAttributes.getColor(R.styleable.PinLock_keypadTextColor, Color.BLACK);
        view.setTextColor(color);

        final int background = styledAttributes
                .getResourceId(R.styleable.PinLock_keypadButtonShape, R.drawable.rectangle);
        view.setBackgroundResource(background);
    }



    private void setValues(int position, Button view) {
        if (position == 10) {
            view.setText("0");
        } else if (position == 9) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setText(String.valueOf((position + 1) % 10));
        }
    }
}
