package com.rumahkpr.akses.aksesrumahkpr.Data.Local;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by JEMMY CALAK on 3/18/2018.
 */

public class AssetJSON {
    public String JSONLOCATION(Activity activity, String location){
        String json = null;
        try {
            InputStream is = activity.getAssets().open("");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read();
            is.close();
            json = new String(buffer, "UTF-8");

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return json;
    }

    public void getListHouse(Activity activity, String location){
        try {
            JSONObject jsonObject = new JSONObject(JSONLOCATION(activity, location));
            //tinggal arahkan kemana
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
