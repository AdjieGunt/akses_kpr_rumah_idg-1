package com.rumahkpr.akses.aksesrumahkpr.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JEMMY CALAK on 2/27/2018.
 */

public class AdapterWelcomeApps extends PagerAdapter {

    private LayoutInflater layoutInflater;
    private int []layout;
    private Context context;

    public AdapterWelcomeApps(int[]layouts, Context contexts){
        this.layout = layouts;
        this.context = contexts;
    }

    @Override
    public int getCount() {
        return layout.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layout[position], container, false);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View)object;
        container.removeView(view);
    }
}
