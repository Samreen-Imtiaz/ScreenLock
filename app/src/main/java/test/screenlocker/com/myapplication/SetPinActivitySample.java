package test.screenlocker.com.myapplication;

import android.content.SharedPreferences;

import com.manusunny.pinlock.SetPinActivity;

public class SetPinActivitySample extends SetPinActivity {

    @Override
    public void onPinSet(String pin) {
        SharedPreferences.Editor edit = PinActivity.pinLockPrefs.edit();
        edit.putString("pin", pin);
        edit.commit();
        setResult(SUCCESS);
        finish();
    }
}
