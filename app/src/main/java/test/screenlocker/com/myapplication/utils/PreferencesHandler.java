package test.screenlocker.com.myapplication.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class PreferencesHandler {

    public static final String FILE_NAME_SHARED_PREF = "lockapp.xml";
    private static Context context;

    public PreferencesHandler(Context context) {
        PreferencesHandler.context = context;
    }

    public static void setContext(Context context) {
        PreferencesHandler.context = context;
    }

    public static void updatePreferences(String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void updatePreferences(String key, Boolean value) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }

    public static void updatePreferences(String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    public static void updatePreferences(String key, float value) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        editor.commit();

    }

    public static void updatePreferences(String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        editor.commit();
    }


    public static String getStringPreferences(String key) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF,  Context.MODE_PRIVATE);
        return settings.getString(key, "");

    }
    public static float getFloatPreferences(String key) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, 0);
        return settings.getFloat(key, -1);

    }
    public static Boolean getBooleanPreferences(String key) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, 0);
        return settings.getBoolean(key, false);

    }

    public static int getIntPreferences(String key) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, 0);
        return settings.getInt(key, -1);
    }

    public static Long getLongPreferences(String key) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, 0);
        return settings.getLong(key, -1);
    }

    public static Long getLongPreferences(String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, 0);
        return settings.getLong(key, defaultValue);
    }


    public static void deletePreference(String key) {
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        editor.commit();

    }

    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;

    }
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }




}
