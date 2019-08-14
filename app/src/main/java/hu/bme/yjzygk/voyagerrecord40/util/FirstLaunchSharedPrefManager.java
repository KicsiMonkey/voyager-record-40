package hu.bme.yjzygk.voyagerrecord40.util;

import android.content.Context;
import android.content.SharedPreferences;

public class FirstLaunchSharedPrefManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    private static String APP_NAME = "hu.bme.yjzygk.voyagerrecord40";
    private int PRIVATE_MODE = 0;
    private static String IS_FIRST_LAUNCH = "IsFirstLaunch";

    public FirstLaunchSharedPrefManager(Context c) {
        this.context = c;
        preferences = context.getSharedPreferences(APP_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void setFirstLaunch(boolean isFirstLaunch) {
        editor.putBoolean(IS_FIRST_LAUNCH, isFirstLaunch).commit();
    }

    public boolean isFirstLaunch() {
        return preferences.getBoolean(IS_FIRST_LAUNCH, true);
    }
}
