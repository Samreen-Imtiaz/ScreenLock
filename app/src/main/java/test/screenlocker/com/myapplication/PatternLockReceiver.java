package test.screenlocker.com.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PatternLockReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_SCREEN_OFF) || action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent i = new Intent(context, UnlockPattern.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}