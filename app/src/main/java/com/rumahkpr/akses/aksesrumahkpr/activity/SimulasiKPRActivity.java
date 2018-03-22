package com.rumahkpr.akses.aksesrumahkpr.activity;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.LinearLayout;

import com.rumahkpr.akses.aksesrumahkpr.Listener.SimulasiKPR;
import com.rumahkpr.akses.aksesrumahkpr.R;
import com.rumahkpr.akses.aksesrumahkpr.adapter.ViewPagerAdapter;
import com.rumahkpr.akses.aksesrumahkpr.fragment.SimulasikprFragment;
import com.rumahkpr.akses.aksesrumahkpr.model.Rumah;
import com.rumahkpr.akses.aksesrumahkpr.util.DialogManager;

public class SimulasiKPRActivity extends AppCompatActivity implements SimulasiKPR {

    private int mbunga, mlama, mnilaiPinjaman;
    private Toolbar toolbar;
    private Intent intent;
    private Rumah rumah;
    private ViewPagerAdapter adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulasi_kpr);

        toolbar = (Toolbar)findViewById(R.id.toolbarSimulasi);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tabLayout = (TabLayout)findViewById(R.id.tabSimulasi);
        viewPager = (ViewPager)findViewById(R.id.viewPagerSimulasi);
        LinearLayout hitung = (LinearLayout)findViewById(R.id.hitung);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), 1);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        getDataIntent();


        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Hitung", mnilaiPinjaman+", "+mlama+", "+mbunga);
                if(mnilaiPinjaman == 0){
                    new DialogManager().AlertOK(SimulasiKPRActivity.this, "Nilai bunga belum terisi", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                }else if(mbunga == 0 ){
                    new DialogManager().AlertOK(SimulasiKPRActivity.this, "Jumlah Lama angsuran belum terisi", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                }else if(mlama == 0 ){
                    new DialogManager().AlertOK(SimulasiKPRActivity.this, "Jumlah uang muka belum terisi", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                }else{
                    Log.d("Hitung222", mnilaiPinjaman+", "+mlama+", "+mbunga);

                    Fragment fragment = adapter.getRegistrationFragment(0);
                    Log.d("fragment", fragment.getTag());
                    ((SimulasikprFragment)fragment).setDataHouse(rumah);
                }
            }
        });

    }

    private void getDataIntent() {
        intent = getIntent();
        rumah = (Rumah)intent.getSerializableExtra("rumah");


//        Fragment fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPagerSimulasi + ":" + viewPager.getCurrentItem());
//        Log.d("Tag", fragment.getTag());

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
}
