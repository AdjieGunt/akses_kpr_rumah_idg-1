package com.rumahkpr.akses.aksesrumahkpr.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rumahkpr.akses.aksesrumahkpr.Listener.HomeListener;
import com.rumahkpr.akses.aksesrumahkpr.R;
import com.rumahkpr.akses.aksesrumahkpr.adapter.ViewPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener{


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private SearchView searchView;
    private HomeListener homeListener;
    private int positionTab;
    private SearchView.SearchAutoComplete searchAutoComplete;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initXml(view);
        initViewPager();
        return view;
    }

    private void initXml(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tabMenu);
        viewPager = (ViewPager) view.findViewById(R.id.pagerMenu);
        searchView = (SearchView) view.findViewById(R.id.searchView);
        initSearchView();
    }

    private void initSearchView() {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setQueryHint("Cari Rumah, contoh 'Rumah di depok'");
        searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setBackgroundColor(Color.WHITE);
        searchAutoComplete.setHintTextColor(getResources().getColor(R.color.darkGray));
        searchAutoComplete.setTextColor(getResources().getColor(R.color.darkGray));
    }

    private void initViewPager() {
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), 0, new Bundle());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        positionTab = tabLayout.getSelectedTabPosition();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                initTabChange();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        int space = 30;
        ViewGroup slide = (ViewGroup) tabLayout.getChildAt(0);
        for (int i = 0; i < slide.getChildCount() - 1; i++) {
            View view = slide.getChildAt(i);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            params.rightMargin = space;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("searOnQuerySubmit", query);
        sendToChieldFragment(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeListener) {
            homeListener = (HomeListener) context;
        } else {
            throw new RuntimeException(context.toString() + "Must be implement HomeLister");
        }
    }

    public void initTabChange() {
        positionTab = tabLayout.getSelectedTabPosition();
        Log.d("positionTab", String.valueOf(positionTab));

        if (positionTab == 0) {
            searchAutoComplete.setFocusableInTouchMode(false);
            searchView.setFocusable(false);

        } else {
            searchAutoComplete.setFocusableInTouchMode(true);
            searchView.setFocusable(true);
            searchView.setOnQueryTextListener(this);

        }
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("positionTab3", String.valueOf(positionTab));
                if (positionTab == 0) {
                    homeListener.goSearch();
                } else {
                    Log.d("searClick", searchAutoComplete.getText().toString());
                    sendToChieldFragment(searchAutoComplete.getText().toString());

                }
            }
        });

        searchAutoComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (positionTab == 0) {
                    homeListener.goSearch();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initTabChange();
    }

    public void sendToChieldFragment(String query){
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pagerMenu + ":" + viewPager.getCurrentItem());
        Log.d("Tag", fragment.getTag());
        ((MapFragment)fragment).search(query);
    }
}
