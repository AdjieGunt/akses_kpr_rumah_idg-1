package com.rumahkpr.akses.aksesrumahkpr;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.rumahkpr.akses.aksesrumahkpr.Data.Online.API;
import com.rumahkpr.akses.aksesrumahkpr.Listener.HomeListener;
import com.rumahkpr.akses.aksesrumahkpr.activity.SearchActivity;
import com.rumahkpr.akses.aksesrumahkpr.fragment.DaftarPengajuanFragment;
import com.rumahkpr.akses.aksesrumahkpr.fragment.HomeFragment;
import com.rumahkpr.akses.aksesrumahkpr.fragment.ProfileFragment;
import com.rumahkpr.akses.aksesrumahkpr.fragment.WishlistFragment;
import com.rumahkpr.akses.aksesrumahkpr.util.BottomNavigationHelper;
import com.rumahkpr.akses.aksesrumahkpr.util.DialogManager;


public class MainActivity extends AppCompatActivity implements HomeListener{

    private BottomNavigationView bottomNavigationView;
    private int backBton = 0;
    private FragmentTransaction transaction;
    private Fragment currentFragment, exitingFragment;
    private HomeFragment home;
    private ProfileFragment profile;
    private WishlistFragment wishlist;
    private DaftarPengajuanFragment daftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniXml();

        home = new HomeFragment();
        profile = new ProfileFragment();
        wishlist = new WishlistFragment();
        daftar = new DaftarPengajuanFragment();

        openHome();
    }

    private void openHome() {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameUtama, profile);
        transaction.hide(profile);
        transaction.add(R.id.frameUtama, wishlist);
        transaction.hide(wishlist);
        transaction.add(R.id.frameUtama, daftar);
        transaction.hide(daftar);
        transaction.replace(R.id.frameUtama, home);
        transaction.commit();
        currentFragment = home;
    }

    public void switchFragment(Fragment fragment) {

        if(currentFragment != fragment) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.activity_slide_in_up, R.anim.activity_slide_out_down);
            if (fragment.isAdded()) {
                transaction.show(fragment);
            } else {
                transaction.add(R.id.frameUtama, fragment);
            }
            if (currentFragment.isAdded()) {
                transaction.hide(currentFragment);
            }
            transaction.commit();
            currentFragment = fragment;
        }
    }

    private void iniXml() {

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navBottom);
        BottomNavigationHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        switchFragment(home);
                        break;
                    case R.id.profil:
                        switchFragment(profile);
                        break;
                    case R.id.wishlist:
                        switchFragment(wishlist);
                        break;
                    case R.id.kpr:
                        switchFragment(daftar);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        switch (backBton) {
            case 0:
                backBton = 1;
                break;
            case 1:
                new DialogManager().AlertOKNO(this, getResources().getString(R.string.exit), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                System.exit(0);
                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                backBton = 0;
                break;
        }
    }


    @Override
    public void goSearch() {
        startActivity(new Intent(this, SearchActivity.class));
    }

}
