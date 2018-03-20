package com.rumahkpr.akses.aksesrumahkpr.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rumahkpr.akses.aksesrumahkpr.Data.Online.API;
import com.rumahkpr.akses.aksesrumahkpr.R;
import com.rumahkpr.akses.aksesrumahkpr.adapter.ImageSliderAdapter;
import com.rumahkpr.akses.aksesrumahkpr.adapter.RecyclerViewAdapter;
import com.rumahkpr.akses.aksesrumahkpr.fragment.DialogSimulasiFragment;
import com.rumahkpr.akses.aksesrumahkpr.model.Rumah;
import com.rumahkpr.akses.aksesrumahkpr.util.formatNominal;

import java.util.ArrayList;

public class DetailProductActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback{

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ViewPager pagerImageSlider;
    private ImageSliderAdapter imageSliderAdapter;
    private ArrayList<String> dataImage;
    private LinearLayout dotLayout;
    private RecyclerView moreTipeRecycler;
    private RecyclerViewAdapter adapter;
    private Button simulasi, ajukan;
    private FragmentManager fragmentManager;
    private Rumah rumah;
    private API api;
    private TextView nama, alamat, notelp, harga, fasilitas, sertifikat,
            klaster, lantai, luas_bangunan, luas_tanah, jumlah_unit, subsidi,
            tahun_bangun, developer;
    private String kamar_mandi="", garasi="", kamar_tidur="", msubsidi="Non Subsidi";
    private GoogleMap googleMap;
    private Double latHouse, longHouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        api = new API();
        initColaps();
        initXML();
        getDataIntent();

        setDataDetail();
        getDataMoreTipe();
    }

    private void getDataMoreTipe() {
        api.getListHouse(this, "moreDetail",  new Fragment());
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        rumah = (Rumah)intent.getSerializableExtra("rumah");
        latHouse = Double.valueOf(rumah.getLatitude());
        longHouse = Double.valueOf(rumah.getLongitude());
        Log.d("gambar51", rumah.getImg5());

        if (rumah.getKamar_mandi().equals("true")){
            kamar_mandi = "Kamar Mandi, ";
        }
        if(rumah.getKamar_tidur().equals("true")){
            kamar_tidur = "Kamar Tidur, ";
        }
        if(rumah.getGarasi().equals("true")){
            garasi = "Garasi.";
        }
        if(rumah.getSubsidi().equals("true")){
            msubsidi = "Bersubsidi";
        }
        nama.setText(rumah.getKlaster());
        alamat.setText(rumah.getAlamat());
        harga.setText("Rp."+new formatNominal().nominal(Integer.valueOf(rumah.getHarga())));
        fasilitas.setText(kamar_mandi + kamar_tidur+ garasi);
        sertifikat.setText(rumah.getSertifikat());
        klaster.setText(rumah.getKlaster());
        lantai.setText(rumah.getLantai() +" Lantai");
        luas_bangunan.setText(rumah.getLuas_bangunan()+" m2");
        luas_tanah.setText(rumah.getLuas_tanah()+ " m2");
        jumlah_unit.setText(rumah.getStatus_unit()+" Unit");
        subsidi.setText(msubsidi);
        tahun_bangun.setText(rumah.getTahun_bangun());
        developer.setText(rumah.getKlaster());

    }

    public void setDataMoreTipe(ArrayList<Rumah> mdata) {
        adapter = new RecyclerViewAdapter(this, this, mdata, 1);
        moreTipeRecycler.setAdapter(adapter);
    }

    private void setDataDetail() {
        dataImage = new ArrayList<>();

        if (!rumah.getImg1().equals("null") && rumah.getImg1()!= null) {
            dataImage.add(rumah.getImg1());
        }
        if (!(rumah.getImg2().equals("null")) && rumah.getImg2()!= null){
            dataImage.add(rumah.getImg2());
        }
        if (!(rumah.getImg3().equals("null")) && rumah.getImg3()!= null) {
            dataImage.add(rumah.getImg3());
        }
        if (!(rumah.getImg4().equals("null")) && rumah.getImg4()!= null) {
            dataImage.add(rumah.getImg4());
        }
        if (!(rumah.getImg5().equals("null")) && rumah.getImg5()!= null) {
            dataImage.add(rumah.getImg5());
        }

        imageSliderAdapter = new ImageSliderAdapter(this, dataImage);
        pagerImageSlider.setAdapter(imageSliderAdapter);

        pagerImageSlider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                addDot(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void initXML() {
        fragmentManager = getSupportFragmentManager();
        pagerImageSlider = (ViewPager) findViewById(R.id.imageSlider);
        dotLayout = (LinearLayout)findViewById(R.id.dotLayout);
        simulasi = (Button)findViewById(R.id.simulasicicilan);
        ajukan = (Button)findViewById(R.id.ajukankpr);
        simulasi.setOnClickListener(this);
        ajukan.setOnClickListener(this);

        nama = (TextView)findViewById(R.id.detail_name);
        developer = (TextView)findViewById(R.id.detail_developer);
        alamat = (TextView)findViewById(R.id.alamat);
        notelp = (TextView)findViewById(R.id.notelp);
        harga = (TextView)findViewById(R.id.hrg_detail);
        fasilitas = (TextView)findViewById(R.id.fasilitas);
        sertifikat = (TextView)findViewById(R.id.sertifikat);
        klaster = (TextView)findViewById(R.id.klaster);
        lantai = (TextView)findViewById(R.id.lantai);
        luas_bangunan = (TextView)findViewById(R.id.luas_bangunan);
        luas_tanah = (TextView)findViewById(R.id.luas_tanah);
        jumlah_unit = (TextView)findViewById(R.id.jumlah_unit);
        subsidi = (TextView)findViewById(R.id.stts_subsidi);
        tahun_bangun = (TextView)findViewById(R.id.tahun_bangun);

        moreTipeRecycler = (RecyclerView)findViewById(R.id.moreTipeRecycler);
        moreTipeRecycler.setLayoutManager(new LinearLayoutManager(DetailProductActivity.this, LinearLayoutManager.HORIZONTAL,  false));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.detailMap);
        mapFragment.getMapAsync(this);
    }

    private void initColaps() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        toolbar.setTitle(null);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(null);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.callapsToolbar);
//        collapsingToolbarLayout.setTitle("Rumah Idaman");
//        dynamicToolbarcolor();
//
//        toolbarTextApperence();
    }
//
//    private void toolbarTextApperence() {
//        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
//        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
//    }
//
//    private void dynamicToolbarcolor() {
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aparment);
//        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
//                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark));
//            }
//        });
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pdp_menu, menu);
        return true;
    }

    public void addDot(int position){

        TextView[] dots = new TextView[dataImage.size()];
        int[] colorActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInActive = getResources().getIntArray(R.array.array_dot_inactive);

        dotLayout.removeAllViews();
        for(int i=0; i<dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&middot;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInActive[0]);
            dotLayout.addView(dots[i]);
        }

        if(dots.length > 0)
            dots[position].setTextColor(colorActive[0]);
    }

    public void openSimulasi(){
        FragmentManager fm = getSupportFragmentManager();
        DialogSimulasiFragment dialog = new DialogSimulasiFragment();
        dialog.setCancelable(false);
        dialog.show(fm, "DialogSimulasiFragment");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.simulasicicilan:
                    openSimulasi();
                break;
            case R.id.ajukankpr:
                Toast.makeText(this, "Development", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMaps) {
        googleMap = googleMaps;
        LatLng location = new LatLng( latHouse, longHouse);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.title("This build is here.");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker));
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));
    }
}
