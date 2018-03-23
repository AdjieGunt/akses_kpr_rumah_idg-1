package com.rumahkpr.akses.aksesrumahkpr.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rumahkpr.akses.aksesrumahkpr.Data.Online.API;
import com.rumahkpr.akses.aksesrumahkpr.Listener.SimulasiKPR;
import com.rumahkpr.akses.aksesrumahkpr.R;
import com.rumahkpr.akses.aksesrumahkpr.adapter.ViewPagerAdapter;
import com.rumahkpr.akses.aksesrumahkpr.model.Rumah;
import com.rumahkpr.akses.aksesrumahkpr.util.DialogManager;
import com.rumahkpr.akses.aksesrumahkpr.util.formatNominal;

import org.json.JSONException;
import org.json.JSONObject;

public class SimulasiKPRActivity extends AppCompatActivity implements SimulasiKPR {

    private int mbunga=0, mlama=0, mnilaiPinjaman=0;
    private Toolbar toolbar;
    private Intent intent;
    private Rumah rumah;
    private ViewPagerAdapter adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Bundle bundle;
    private RelativeLayout hitung;
    private API api;
    private formatNominal formatNominal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulasi_kpr);

        api = new API();
        formatNominal = new formatNominal();

        toolbar = (Toolbar)findViewById(R.id.toolbarSimulasi);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tabLayout = (TabLayout)findViewById(R.id.tabSimulasi);
        viewPager = (ViewPager)findViewById(R.id.viewPagerSimulasi);
        hitung = (RelativeLayout)findViewById(R.id.hitung);
        getDataIntent();

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), 1, bundle);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Hitung", mnilaiPinjaman+", "+mlama+", "+mbunga);
                if(mnilaiPinjaman <= 0){
                    new DialogManager().AlertOK(SimulasiKPRActivity.this, "Jumlah uang muka belum terisi", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                }else if(mbunga <= 0 ){
                    new DialogManager().AlertOK(SimulasiKPRActivity.this, "Nilai bunga belum terisi", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                }else if(mlama <= 0 ){
                    new DialogManager().AlertOK(SimulasiKPRActivity.this, "Lama pinjaman belum terisi", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                }else{
                    Log.d("Hitung222", mnilaiPinjaman+", "+mlama+", "+mbunga);
                    sendData();
                }
            }
        });

    }

    private void sendData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nilai_pinjam", String.valueOf(mnilaiPinjaman));
            jsonObject.put("interest", String.valueOf(mbunga));
            jsonObject.put("jangka_waktu", mlama);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        api.allConfig(SimulasiKPRActivity.this, jsonObject, new Fragment(), "credit-simulation.json", SimulasiKPR.class.getName());
    }

    private void getDataIntent() {
        intent = getIntent();
        rumah = (Rumah)intent.getSerializableExtra("rumah");
        bundle = new Bundle();
        bundle.putSerializable("rumah", rumah);

    }

    @Override
    public void hitungSimulasi(String nilaiPinjaman, String lamaPinjaman, String sukuBunga) {
        mbunga = Integer.valueOf(sukuBunga);
        mlama = Integer.valueOf(lamaPinjaman);
        mnilaiPinjaman = Integer.valueOf(nilaiPinjaman);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition( 0, R.anim.activity_slide_out_down);
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

    public void hasilSimulasi(String hasil){
        View view = getLayoutInflater().inflate(R.layout.activity_hasil_simulasi, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        TextView hasilTxt = (TextView)dialog.findViewById(R.id.nilasiHasil);
        ImageView close = (ImageView)dialog.findViewById(R.id.close_hasilSimulasi);
        double mhasil = Double.valueOf(hasil);
        int nhasil = Integer.valueOf((int) mhasil);
        hasilTxt.setText("Rp. "+formatNominal.nominal(nhasil));

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}