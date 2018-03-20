package com.rumahkpr.akses.aksesrumahkpr;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.rumahkpr.akses.aksesrumahkpr.Listener.HomeListener;
import com.rumahkpr.akses.aksesrumahkpr.activity.SearchActivity;
import com.rumahkpr.akses.aksesrumahkpr.fragment.HomeFragment;
import com.rumahkpr.akses.aksesrumahkpr.fragment.ProfileFragment;
import com.rumahkpr.akses.aksesrumahkpr.fragment.WishlistFragment;
import com.rumahkpr.akses.aksesrumahkpr.util.BottomNavigationHelper;
import com.rumahkpr.akses.aksesrumahkpr.util.DialogManager;

public class MainActivity extends AppCompatActivity implements HomeListener {

    private BottomNavigationView bottomNavigationView;
    private int backBton = 0;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniXml();
        openHome();
    }

    public void openHome() {
        HomeFragment home = new HomeFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.anime_dialog_left_side, R.anim.anime_dialog_right_side);
        transaction.replace(R.id.frameUtama, home);
        transaction.commit();

    }

    public void openProfile() {

        ProfileFragment profile = new ProfileFragment();
        String backStack = profile.getClass().getName();
        FragmentManager fragmentManager = getSupportFragmentManager();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStack, 0);
        fragmentManager.popBackStack(backStack, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        if(!fragmentPopped){
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.anime_dialog_left_side, R.anim.anime_dialog_right_side);
            transaction.replace(R.id.frameUtama, profile);
            transaction.addToBackStack(backStack);
            transaction.commit();
        }
    }

    public void openFragment(int position){
        Fragment newFragment = null;
        String backStack = null;
        switch (position){
            case 0:
                newFragment = new HomeFragment();
                backStack = newFragment.getClass().getName();
                break;
            case 1:
                HomeFragment home = new HomeFragment();
                getSupportFragmentManager().beginTransaction().remove(home).commit();

                newFragment = new ProfileFragment();
                backStack = newFragment.getClass().getName();
                break;
            case 2:
                newFragment = new WishlistFragment();
                backStack = newFragment.getClass().getName();
                break;
            case 3:

                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(backStack, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transactions = getSupportFragmentManager().beginTransaction();

        transactions.setCustomAnimations(R.anim.anime_dialog_left_side, R.anim.anime_dialog_right_side);
        transactions.replace(R.id.frameUtama, newFragment, newFragment.getTag());
        transactions.addToBackStack(backStack);
        transactions.commit();

    }


    private void iniXml() {

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navBottom);
        BottomNavigationHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        openFragment(0);
                        break;
                    case R.id.profil:
                        openFragment(1);
                        break;
                    case R.id.wishlist:
                        openFragment(2);
                        break;
                    case R.id.kpr:
                        Toast.makeText(MainActivity.this, "Developmet", Toast.LENGTH_SHORT).show();
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
