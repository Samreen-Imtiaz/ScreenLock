package test.screenlocker.com.myapplication;

import android.app.Application;

import test.screenlocker.com.myapplication.utils.PreferencesHandler;

/**
 * Created by Ali on 11/7/2016.
 */

public class LocKAppApplicaton extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesHandler preferencesHandler=new PreferencesHandler(this);
        preferencesHandler.setContext(this);
    }

}
