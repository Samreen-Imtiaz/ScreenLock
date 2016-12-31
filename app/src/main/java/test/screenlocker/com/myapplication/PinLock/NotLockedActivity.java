package test.screenlocker.com.myapplication.PinLock;

import android.app.Activity;
import android.os.Bundle;

import test.screenlocker.com.myapplication.R;


public class NotLockedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_locked);
    }

}
