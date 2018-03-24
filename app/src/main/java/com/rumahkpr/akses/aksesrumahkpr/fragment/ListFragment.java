package com.rumahkpr.akses.aksesrumahkpr.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.rumahkpr.akses.aksesrumahkpr.Data.Online.API;
import com.rumahkpr.akses.aksesrumahkpr.Listener.BaseLocation;
import com.rumahkpr.akses.aksesrumahkpr.R;
import com.rumahkpr.akses.aksesrumahkpr.activity.SearchActivity;
import com.rumahkpr.akses.aksesrumahkpr.adapter.RecyclerViewAdapter;
import com.rumahkpr.akses.aksesrumahkpr.model.Rumah;
import com.rumahkpr.akses.aksesrumahkpr.util.RoundRectCornerImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements BaseLocation, View.OnClickListener {

    private RecyclerView recyclerViewSekitar, recyclerViewDaerah;
    private Button moreSekitar, moreDaerah;
    private ArrayList<Rumah> dData;
    private ArrayList<Rumah> sData;
    private RecyclerViewAdapter adapter;
    private RecyclerViewAdapter adapter1;
    private API api;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GoogleMap googleMap;
    private GoogleApiClient googleApiClient;
    private String[] kota = {
            "jakarta", "depok", "bandung", "tangerang", "surabaya"
    };
    private String baseLocation;
    private LocationRequest locationRequest;
    private static final int LOCATION_REQ = 101;
    private Double currentLatitude, currentLongitude;
    private NestedScrollView mainLayout;
    private RoundRectCornerImageView catRumah;
    private RoundRectCornerImageView catApartment;

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
        api.startLoading(getActivity());
        new Thread(new Runnable() {
            @Override
            public void run() {
                api.getListHouse(getActivity(), "jakarta", ListFragment.this, "disekitar");
                api.getListHouse(getActivity(), "tangerang", ListFragment.this, "daerah");
            }
        }).start();
    }

    public void setRumahDisekitar(ArrayList<Rumah> data) {
        this.sData = data;
        adapter = new RecyclerViewAdapter(getActivity(), getActivity(), data, 0);
        recyclerViewSekitar.setAdapter(adapter);
    }

    public void setRumahDaerah(ArrayList<Rumah> data) {
        this.dData = data;
        adapter1 = new RecyclerViewAdapter(getActivity(), getActivity(), data, 0);
        recyclerViewDaerah.setAdapter(adapter1);
        api.stopLoading();
        mainLayout.setVisibility(View.VISIBLE);
    }

    private void initXml(View view) {
        recyclerViewSekitar = (RecyclerView) view.findViewById(R.id.recler);
        recyclerViewSekitar.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewSekitar.addItemDecoration(new DividerItemDecoration(getContext(), 1));
        recyclerViewSekitar.setHasFixedSize(true);
        recyclerViewSekitar.setNestedScrollingEnabled(false);
        recyclerViewDaerah = (RecyclerView) view.findViewById(R.id.reclerDaerah);
        recyclerViewDaerah.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewDaerah.addItemDecoration(new DividerItemDecoration(getContext(), 1));
        recyclerViewDaerah.setHasFixedSize(true);
        recyclerViewDaerah.setNestedScrollingEnabled(false);
        moreSekitar = (Button) view.findViewById(R.id.moreSekitar);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipList);
        mainLayout = (NestedScrollView)view.findViewById(R.id.layoutListHouse);
        mainLayout.setVisibility(View.GONE);

        catApartment = (RoundRectCornerImageView)view.findViewById(R.id.catApartment);
        catRumah = (RoundRectCornerImageView)view.findViewById(R.id.catRumah);
        catRumah.setOnClickListener(this);
        catApartment.setOnClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                dData.clear();
                sData.clear();
                getDataHouseList();
            }
        });
    }

    private void getBaseLocation(Location location) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            Log.d("Current Location1", "==>>" + addresses.get(0).getLocality() + "-- " + addresses.get(0).getAdminArea() + "--" + addresses.get(0).getSubLocality());
            for (int i = 0; i < kota.length; i++) {
                if (addresses.get(0).getAdminArea().toLowerCase().contains(kota[i])) {
                    baseLocation = kota[i];
                }
            }
            Log.d("baseLocationListLayout", baseLocation);
            getDataHouseList();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void baseLocation(String location) {
        baseLocation = location;
        Log.d("locationList", baseLocation);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.catRumah:
                intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("code", 1);
                intent.putExtra("catRumah", "jakarta");
                startActivity(intent);
                break;
            case R.id.catApartment:
                intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("code", 2);
                intent.putExtra("catApartemen", "apartemen");
                startActivity(intent);
                break;
        }
    }
}
