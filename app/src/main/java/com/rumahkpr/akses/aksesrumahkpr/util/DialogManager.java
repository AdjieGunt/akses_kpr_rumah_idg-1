package com.rumahkpr.akses.aksesrumahkpr.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.rumahkpr.akses.aksesrumahkpr.R;

/**
 * Created by JEMMY CALAK on 2/27/2018.
 */

public class DialogManager {
    public void AlertOKNO(Context context, String pesan, DialogInterface.OnClickListener OK, DialogInterface.OnClickListener NO){
        new AlertDialog.Builder(context)
                .setMessage(pesan)
                .setPositiveButton("YA", OK)
                .setNegativeButton("NO", NO)
                .setIcon(R.drawable.ic_alert)
                .create()
                .show();
    }

    public void AlertOK(Context context, String pesan, DialogInterface.OnClickListener OK){
        new AlertDialog.Builder(context)
                .setMessage(pesan)
                .setPositiveButton("YA", OK)
                .setIcon(R.drawable.ic_alert)
                .create()
                .show();
    }
}
