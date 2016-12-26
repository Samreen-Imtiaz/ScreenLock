

package com.manusunny.pinlock.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.manusunny.pinlock.PinListener;
import com.manusunny.pinlock.R;



public class Keypad extends GridView {

    private static String pin = "";
    private final TypedArray styledAttributes;
    private final Context context;

    public Keypad(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        styledAttributes = context.obtainStyledAttributes(R.style.PinLock, R.styleable.PinLock);
        setNumColumns(3);
        setSpacing();
    }



    private void setSpacing() {
        final int verticalSpacing = styledAttributes.getDimensionPixelOffset(R.styleable.PinLock_keypadVerticalSpacing, 2);
        final int horizontalSpacing = styledAttributes.getDimensionPixelOffset(R.styleable.PinLock_keypadHorizontalSpacing, 2);
        setVerticalSpacing(verticalSpacing);
        setHorizontalSpacing(horizontalSpacing);
    }
    public void setLayoutParameters() {
        final int keypadWidth = styledAttributes.getDimensionPixelOffset(R.styleable.PinLock_keypadWidth, 200);
        final int keypadHeight = styledAttributes.getDimensionPixelOffset(R.styleable.PinLock_keypadHeight, 230);
        final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(keypadWidth, keypadHeight);
        params.gravity = 17;
        setLayoutParams(params);
    }


    public void setPinListener(PinListener pinListener) {
        setAdapter(new KeypadAdapter(context, styledAttributes, pinListener));
    }



    public static String onKeyPress(String key) {
        pin = pin.concat(key);
        return pin;
    }

    public static void resetPin(){
        pin = "";
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayoutParameters();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        styledAttributes.recycle();
    }
}
