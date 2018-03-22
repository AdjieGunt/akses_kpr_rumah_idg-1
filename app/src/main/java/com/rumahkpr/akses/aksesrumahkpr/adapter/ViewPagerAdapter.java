package com.rumahkpr.akses.aksesrumahkpr.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.rumahkpr.akses.aksesrumahkpr.fragment.ListFragment;
import com.rumahkpr.akses.aksesrumahkpr.fragment.MapFragment;
import com.rumahkpr.akses.aksesrumahkpr.fragment.SimulasikprFragment;

/**
 * Created by JEMMY CALAK on 2/24/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter{

    private SparseArray<Fragment> fragmentSparseArray = new SparseArray<>();
    private int codeLayout;

    public ViewPagerAdapter(FragmentManager fm, int codeLayouts) {
        super(fm);
        this.codeLayout = codeLayouts;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (codeLayout){
            case 0:
                switch (position){
                    case 0:
                        fragment = new ListFragment();
                        break;
                    case 1:
                        fragment = new MapFragment();
                        break;
                }
                break;
            case 1:
                switch (position){
                    case 0:
                        fragment = new SimulasikprFragment();
                        break;
                    case 1:
                        fragment = new SimulasikprFragment();
                        break;
                }
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title=null;
        switch (codeLayout){
            case 0:
                switch (position){
                    case 0:
                        title ="LIST";
                        Log.d("size", String.valueOf(fragmentSparseArray.size()));
                        break;
                    case 1:
                        title ="MAP";
                        break;
                }
                break;
            case 1:
                switch (position){
                    case 0:
                        title ="Konvensional";
                        Log.d("size", String.valueOf(fragmentSparseArray.size()));
                        break;
                    case 1:
                        title ="Syariah";
                        break;
                }
                break;
        }
        return title;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment)super.instantiateItem(container, position);
        fragmentSparseArray.put(position, fragment);

        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        fragmentSparseArray.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegistrationFragment(int position){
        Log.d("size", String.valueOf(fragmentSparseArray.size()));
        return fragmentSparseArray.get(position);
    }
}