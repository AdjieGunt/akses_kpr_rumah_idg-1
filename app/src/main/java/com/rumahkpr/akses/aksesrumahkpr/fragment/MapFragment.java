package com.rumahkpr.akses.aksesrumahkpr.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
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
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rumahkpr.akses.aksesrumahkpr.Data.Online.API;
import com.rumahkpr.akses.aksesrumahkpr.Listener.BaseLocation;
import com.rumahkpr.akses.aksesrumahkpr.R;
import com.rumahkpr.akses.aksesrumahkpr.adapter.RecyclerViewAdapter;
import com.rumahkpr.akses.aksesrumahkpr.model.Rumah;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap googleMaps;
    private GoogleApiClient googleApiClient;
    private static final int LOCATION_REQ = 101;
    private Location location;
    private LocationRequest locationRequest;
    private Marker marker;
    private ArrayList<LatLng> latLngArrayList;
    private RecyclerView recyclerMap;
    private RecyclerViewAdapter adapter;
    private API api;
    private String mLocation, latitude = null, longitude = null;
    private Double currentLatitude, currentLongitude;
    private String baseLocation;
    private String[]kota={
            "jakarta","depok","bandung", "tangerang","surabaya"
    };

    BaseLocation baseLocationLis;
    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        api = new API();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            checkPermission();
        }

        initMap();
        initRecycler(view);

    }

    private void getDataRumahMap() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                api.getListHouse(getActivity(), baseLocation, MapFragment.this,"listHouseMap");
            }
        });
        thread.start();
    }

    public void setDataRumah(ArrayList<Rumah> mdata) {

        adapter = new RecyclerViewAdapter(getActivity(), getActivity(), mdata, 1);
        recyclerMap.setAdapter(adapter);
    }

    private void initRecycler(View view) {
        FragmentActivity activity = getActivity();
        recyclerMap = (RecyclerView) view.findViewById(R.id.recyclerMap);
        recyclerMap.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQ);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQ);
            }
            return false;
        } else {
            return true;
        }
    }

    private void initMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.viewMaps);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMaps = googleMap;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                initGoogleMaps();
                googleMaps.setMyLocationEnabled(true);
                googleMaps.getUiSettings().setMyLocationButtonEnabled(true);
            }
        } else {
            initGoogleMaps();
            googleMaps.setMyLocationEnabled(true);
            googleMaps.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }

    private void setMylocations(Location location) {
        Log.d("setMylocation", "<<===");
        LatLng newLat = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(newLat);
        markerOptions.title("This build is here.");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker));
        googleMaps.addMarker(markerOptions);
        googleMaps.moveCamera(CameraUpdateFactory.newLatLng(newLat));
        googleMaps.animateCamera(CameraUpdateFactory.zoomTo(17));
    }

    private void doPermission() {
        //Checking if the user has granted the permission
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Requesting the Location permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQ);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQ:
                Log.d("onRequestPermission", "<<===");
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        if (checkPermission()) {
                            initGoogleMaps();
                            googleMaps.setMyLocationEnabled(true);
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "You need allow the permission.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onLocationChanged(Location locations) {
        location = locations;

        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        if (marker != null) {
            marker.remove();
        }

        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
        getDetailLocation(location);
    }

    public void getDetailLocation(Location location) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());


        try {
            addresses = geocoder.getFromLocation(currentLatitude, currentLongitude, 1);
            setMylocations(location);

            Log.d("Current Location2", "==>>"  + addresses.get(0).getLocality() + "-- " + addresses.get(0).getAdminArea() +"--"+ addresses.get(0).getSubLocality());
            for(int i=0; i<kota.length; i++){
                if(addresses.get(0).getAdminArea().toLowerCase().contains(kota[i])){
                    baseLocation = kota[i];
                }
            }

            Log.d("baseLocation", baseLocation);
            baseLocationLis.baseLocation(baseLocation);
            getDataRumahMap();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (!latitude.equals("0.0") && !longitude.equals("0.0")) {
            try {
                addresses = geocoder.getFromLocation(currentLatitude, currentLongitude, 1);
                Log.d("Current Location2", "==>>"  + addresses.get(0).getLocality() + "++ " + addresses.get(0).getAdminArea() +"++"+ addresses.get(0).getSubLocality());
                setMylocations(location);
            } catch (Exception e) {

            }
        }
    }

    private synchronized void initGoogleMaps() {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (checkPermission()) {
            //get Current position when connected
            try {
                Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                if (location == null) {
                    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
                } else {
                    //If everything went fine lets get latitude and longitude
                    currentLatitude = location.getLatitude();
                    currentLongitude = location.getLongitude();
                }
                getDetailLocation(location);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("onConnectionSuspended", "<<---");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("onConnectionFailed", "<<---");
    }


    public void search(String data) {
        latLngArrayList = new ArrayList<>();
        Log.d("dataSearchParentFrag", data);
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            List<Address> addressList = geocoder.getFromLocationName(data, 5);

            List<Address> addressList1 = geocoder.getFromLocation(addressList.get(0).getLatitude(), addressList.get(0).getLongitude(), 100);
            Log.d("sizeLocation", String.valueOf(addressList1.size()));
            if (addressList1.size() > 0) {
                for (int i = 0; i < addressList1.size(); i++) {
                    Double lat = (double) (addressList1.get(i).getLatitude());
                    Double lon = (double) (addressList1.get(i).getLongitude());
                    Log.d("location", "==>>" + lat + " , " + lon + "," + addressList1.get(i).getAddressLine(i) + "," + addressList1.get(i).getCountryName());

                    final LatLng locations = new LatLng(lat, lon);
                    latLngArrayList.add(locations);
                    //setNew Marker
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker));
                    markerOptions.position(locations);
                    googleMaps.addMarker(markerOptions);
                }
                googleMaps.moveCamera(CameraUpdateFactory.newLatLng(latLngArrayList.get(0)));
                googleMaps.animateCamera(CameraUpdateFactory.zoomTo(15));

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ErrorSearch", "<<===");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initGoogleMaps();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof BaseLocation){
            baseLocationLis = (BaseLocation) context;
        }
    }
}
