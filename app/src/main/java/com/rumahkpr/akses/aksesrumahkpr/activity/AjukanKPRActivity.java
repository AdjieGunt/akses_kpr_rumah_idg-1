package com.rumahkpr.akses.aksesrumahkpr.activity;

import android.content.DialogInterface;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.rumahkpr.akses.aksesrumahkpr.Data.Online.API;
import com.rumahkpr.akses.aksesrumahkpr.R;
import com.rumahkpr.akses.aksesrumahkpr.util.DialogManager;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;


public class AjukanKPRActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Toolbar toolbar;
    private EditText nik, nm, tgl, nmIbu, pendapatan, email, noHp, noCif, platond_kredit, tenor;
    private RelativeLayout bt_ajukan;
    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajukan_kpr);

        initXml();
    }

    private void initXml() {
        api = new API();
        toolbar = (Toolbar)findViewById(R.id.toolbarPengajuan);
        setSupportActionBar(toolbar);

        nik = (EditText)findViewById(R.id.nik);
        nm = (EditText)findViewById(R.id.namaLengkap);
        tgl = (EditText)findViewById(R.id.tglLahir);
//        tgl.setInputType(InputType.TYPE_NULL);
        tgl.setOnClickListener(this);
        nmIbu = (EditText)findViewById(R.id.namaIbu);
        pendapatan = (EditText)findViewById(R.id.pendapatan);
        email = (EditText)findViewById(R.id.email);
        noHp = (EditText)findViewById(R.id.notelpAjukan);
//        noCif = (EditText)findViewById(R.id.no_cif);
        platond_kredit = (EditText)findViewById(R.id.platonKredit);
        tenor = (EditText)findViewById(R.id.tenor);
        bt_ajukan = (RelativeLayout)findViewById(R.id.bt_ajukan);
        bt_ajukan.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }

        // User didn't trigger a refresh, let the superclass handle this action
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition( 0, R.anim.activity_slide_out_down);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_ajukan:
                validateData();
                break;
            case R.id.tglLahir:
                openDate();
                break;
        }
    }

    private void openDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                AjukanKPRActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    private void validateData() {
        if(nik.getText().toString().length() == 0 || nm.getText().toString().length()==0 || tgl.getText().toString().length()==0
                || nmIbu.getText().toString().length()==0 || pendapatan.getText().toString().length()==0|| email.getText().toString().length()==0
                || noHp.getText().toString().length()==0|| noCif.getText().toString().length()==0 || platond_kredit.getText().toString().length()==0
                || tenor.getText().toString().length()==0){
            openAler("Masih ada data yang belum terisi.");
        } else{
            new DialogManager().AlertOKNO(this, "Apakah data-data kamu sudah benar ?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    sendData();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
        }
    }

    private void sendData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nik", nik.getText().toString());
            jsonObject.put("nama", nm.getText().toString());
            jsonObject.put("tgl_lahir", tgl.getText().toString());
            jsonObject.put("nama_ibu_kandung", nmIbu.getText().toString());
            jsonObject.put("pendapatan", Integer.valueOf(pendapatan.getText().toString()));
            jsonObject.put("email", email.getText().toString());
            jsonObject.put("nomor_hp", nik.getText().toString());
            jsonObject.put("nomor_cif", nm.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        api.allConfig(this, jsonObject, new Fragment(), "credit-submission.json", AjukanKPRActivity.class.getName());
    }

    public void openAler(String msg){
        new DialogManager().AlertOK(this, msg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }

    public void openFinish(){
        View view = getLayoutInflater().inflate(R.layout.layout_hasil_pengajuan, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        final ImageView bt_close = (ImageView)dialog.findViewById(R.id.close_pengajuan);
        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        tgl.setText(String.valueOf(dayOfMonth)+"-"+String.valueOf(monthOfYear)+"-"+String.valueOf(year));
    }
}
