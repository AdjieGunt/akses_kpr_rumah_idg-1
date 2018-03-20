package com.rumahkpr.akses.aksesrumahkpr.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rumahkpr.akses.aksesrumahkpr.R;
import com.rumahkpr.akses.aksesrumahkpr.activity.DetailProductActivity;
import com.rumahkpr.akses.aksesrumahkpr.adapter.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogSimulasiFragment extends DialogFragment {
    Dialog dialog;


    public DialogSimulasiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().getWindow().getAttributes().windowAnimations= R.style.AnimationUpDown;

        View view = inflater.inflate(R.layout.dialog_simulasi, container);
        ImageView exitSimulasi = (ImageView) view.findViewById(R.id.exit);
        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tabSimulasi);
        ViewPager viewPager = (ViewPager)view.findViewById(R.id.viewPagerSimulasi);
        LinearLayout hitung = (LinearLayout)view.findViewById(R.id.hitung);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), 1);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        exitSimulasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Development", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

}
