package com.rumahkpr.akses.aksesrumahkpr.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by JEMMY CALAK on 2/27/2018.
 */

public class UtilStartapps {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;
    int private_mode =0;

    private static final String PREF_NAME = "start_apps";
    private static final String IS_FIRTS="is_firts";

    public UtilStartapps(Context contexts){
        this.context = contexts;
        preferences = context.getSharedPreferences(PREF_NAME, private_mode);
        editor = preferences.edit();
    }

    public void setFirtsTimeLaund(boolean isFirtsTime){
        editor.putBoolean(IS_FIRTS, isFirtsTime);
        editor.commit();
    }

    public boolean getFirtsTime(){
        return preferences.getBoolean(IS_FIRTS, true);
    }
}
