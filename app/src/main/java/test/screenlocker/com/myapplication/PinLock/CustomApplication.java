package test.screenlocker.com.myapplication.PinLock;

import android.app.Application;

import com.github.orangegangsters.lollipin.lib.managers.LockManager;

import test.screenlocker.com.myapplication.R;


public class CustomApplication extends Application {

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate() {
        super.onCreate();

        LockManager<CustomPinActivity> lockManager = LockManager.getInstance();
        lockManager.enableAppLock(this, CustomPinActivity.class);
        lockManager.getAppLock().setLogoId(R.drawable.ic_action_lock);
    }
}
