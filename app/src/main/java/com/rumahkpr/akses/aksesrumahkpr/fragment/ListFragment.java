package com.rumahkpr.akses.aksesrumahkpr.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rumahkpr.akses.aksesrumahkpr.Data.Online.API;
import com.rumahkpr.akses.aksesrumahkpr.R;
import com.rumahkpr.akses.aksesrumahkpr.adapter.RecyclerViewAdapter;
import com.rumahkpr.akses.aksesrumahkpr.model.Rumah;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private RecyclerView recyclerViewSekitar,recyclerViewDaerah;
    private Button moreSekitar, moreDaerah;
    private ArrayList<Rumah> dData;
    private ArrayList<Rumah> sData;
    private RecyclerViewAdapter adapter;
    private RecyclerViewAdapter adapter1;
    private API api;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        api = new API();
        dData = new ArrayList<>();
        sData = new ArrayList<>();
        initXml(view);
        getDataHouseList();
        return view;
    }

    private void getDataHouseList() {
        swipeRefreshLayout.setRefreshing(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                api.getListHouse(getActivity(), "disekitar", ListFragment.this);
                api.getListHouse(getActivity(), "daerah", ListFragment.this);
            }
        }).start();
    }

    public void setRumahDisekitar(ArrayList<Rumah> data){
        this.sData = data;
        adapter = new RecyclerViewAdapter(getActivity(), getActivity(), data, 0);
        recyclerViewSekitar.setAdapter(adapter);
    }

    public void setRumahDaerah(ArrayList<Rumah> data){
        this.dData = data;
        adapter1 = new RecyclerViewAdapter(getActivity(), getActivity(), data, 0);
        recyclerViewDaerah.setAdapter(adapter1);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void initXml(View view) {
        recyclerViewSekitar = (RecyclerView)view.findViewById(R.id.recler);
        recyclerViewSekitar.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewSekitar.addItemDecoration(new DividerItemDecoration(getContext(), 1));
        recyclerViewSekitar.setHasFixedSize(true);
        recyclerViewSekitar.setNestedScrollingEnabled(false);
        recyclerViewDaerah = (RecyclerView)view.findViewById(R.id.reclerDaerah);
        recyclerViewDaerah.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewDaerah.addItemDecoration(new DividerItemDecoration(getContext(), 1));
        recyclerViewDaerah.setHasFixedSize(true);
        recyclerViewDaerah.setNestedScrollingEnabled(false);
        moreSekitar = (Button)view.findViewById(R.id.moreSekitar);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipList);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dData.clear();
                sData.clear();
                getDataHouseList();
            }
        });
    }
}
