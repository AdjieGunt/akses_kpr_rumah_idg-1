package com.rumahkpr.akses.aksesrumahkpr.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by JEMMY CALAK on 3/18/2018.
 */

public class Singletone {
    private static Singletone mSingletone;
    private RequestQueue requestQueue;
    private static Context mcontext;

    private Singletone(Context context){
        mcontext = context;
        requestQueue = getRequestquee();
    }

    public RequestQueue getRequestquee(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(mcontext.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized Singletone getmSingletone(Context context){
        if(mSingletone == null){
            mSingletone = new Singletone(context);
        }
        return  mSingletone;
    }

    public<T> void addToRequestqueue(Request<T> request){
        requestQueue.add(request);
    }
}
