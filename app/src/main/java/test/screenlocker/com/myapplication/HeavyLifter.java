package test.screenlocker.com.myapplication;
import java.io.IOException;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
public class HeavyLifter {
 
    public static final int SUCCESS = 0;
    public static final int FAIL = 1;

    private final Context context;
    private final Handler callback;
    private WallpaperManager manager;


    public HeavyLifter(Context context, Handler callback) {
        this.context = context;
        this.callback = callback;
        this.manager = (WallpaperManager) context.getSystemService(Context.WALLPAPER_SERVICE);
    }
 


    public void setResourceAsWallpaper(final int resourceId) {
        new Thread() {
            @Override
            public void run() {
                try {

                    manager.setBitmap(getImage(resourceId));
                    callback.sendEmptyMessage(SUCCESS);
                } catch (IOException e) {
                    Log.e("Main", "Cant set wallpaper");
                    callback.sendEmptyMessage(FAIL);
                }
            }
        }.start();
    }
 //WallpaperManager.getInstance(this).setStream(inputStream, null, true, WallpaperManager.FLAG_LOCK);
/*
* int setStream (InputStream bitmapData,  Rect visibleCropHint,  boolean allowBackup, int which)

int setResource (int resid, int which)

int setBitmap (Bitmap fullImage, Rect visibleCropHint,  boolean allowBackup,  int which)*/

    private Bitmap getImage(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, null);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, manager.getDesiredMinimumWidth(), manager.getDesiredMinimumHeight(), true);
        bitmap.recycle();
        bitmap = null;
        return scaledBitmap;
    }
}