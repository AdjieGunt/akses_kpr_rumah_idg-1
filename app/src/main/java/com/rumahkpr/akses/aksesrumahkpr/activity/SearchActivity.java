package com.rumahkpr.akses.aksesrumahkpr.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.rumahkpr.akses.aksesrumahkpr.Data.Online.API;
import com.rumahkpr.akses.aksesrumahkpr.R;
import com.rumahkpr.akses.aksesrumahkpr.adapter.RecyclerViewAdapter;
import com.rumahkpr.akses.aksesrumahkpr.adapter.SpinnerAdapter;
import com.rumahkpr.akses.aksesrumahkpr.model.Rumah;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private Toolbar toolbar;
    private SearchView searchView;
    private RecyclerView recyclerSearch;
    private RecyclerViewAdapter adapter;
    private API api;
    private ArrayList<Rumah> mdata;
    private LinearLayout layoutFilter, layoutAdvanFilter, bt_advandFilter;
    private Spinner jns_propertySpinner;
    private int showingAdvanFilter = 0, showingFilter=0;
    private ArrayList<String> jnisPorpertyArray;
    private SpinnerAdapter adapterSpinner;
    private Animation animShow, animHide;
    private ImageView dropDownUp;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initXml();
        initSearch();
        initSpinner();
    }

    private void initSpinner() {
        jnisPorpertyArray = new ArrayList<String>();
        jnisPorpertyArray.add("Semua");
        jnisPorpertyArray.add("Hunian");
        jnisPorpertyArray.add("Tempat Usaha");
        jnisPorpertyArray.add("Industri");
        jnisPorpertyArray.add("Gedung");

        adapterSpinner = new SpinnerAdapter(this, android.R.layout.simple_list_item_1, jnisPorpertyArray);
        jns_propertySpinner.setAdapter(adapterSpinner);

    }

    private void initSearch() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Jakarta, Surabaya, Bandung");
//        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.onActionViewExpanded();

        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(getResources().getColor(R.color.gray));
        searchAutoComplete.setTextColor(getResources().getColor(R.color.darkGray));
        searchAutoComplete.setBackgroundColor(getResources().getColor(R.color.white));
    }

    private void initXml() {
        handler = new Handler();
        api = new API();
        toolbar = (Toolbar)findViewById(R.id.toolbarSearch);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
//        toolbar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//        toolbar.setTitle(null);

        searchView = (SearchView)findViewById(R.id.searchs);
        recyclerSearch = (RecyclerView)findViewById(R.id.recyclerSearch);
        recyclerSearch.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerSearch.setHasFixedSize(true);
        layoutFilter = (LinearLayout)findViewById(R.id.layoutFilter);
        layoutAdvanFilter = (LinearLayout)findViewById(R.id.lay_advandFilter);
        bt_advandFilter = (LinearLayout)findViewById(R.id.bt_advandFilter);
        layoutFilter.setVisibility(View.GONE);
        recyclerSearch.setVisibility(View.GONE);
        mdata = new ArrayList<>();
        jns_propertySpinner = (Spinner)findViewById(R.id.spinnerJenisProperti);
        animShow = AnimationUtils.loadAnimation(this, R.anim.activity_slide_in_down);
        animHide = AnimationUtils.loadAnimation(this, R.anim.activity_slide_out_up);
        dropDownUp = (ImageView)findViewById(R.id.dropDownUp);
        dropDownUp.setVisibility(View.GONE);
        dropDownUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(showingFilter ==0){
                    layoutFilter.startAnimation(animShow);
                    layoutFilter.setVisibility(View.VISIBLE);
                    showingFilter =1;
                    dropDownUp.setImageDrawable(getResources().getDrawable(R.drawable.ic_dropup24));
                }else{
                    layoutFilter.startAnimation(animHide);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            layoutFilter.setVisibility(View.GONE);
                            dropDownUp.setImageDrawable(getResources().getDrawable(R.drawable.ic_dropdown_24));
                            layoutAdvanFilter.setVisibility(View.GONE);
                        }
                    }, 400);
                    showingFilter=0;
                    showingAdvanFilter =0;
                }
            }
        });

        bt_advandFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(showingAdvanFilter ==0){
                    showingAdvanFilter =1;
                    layoutAdvanFilter.setVisibility(View.VISIBLE);
                }else{
                    showingAdvanFilter =0;
                    layoutAdvanFilter.setVisibility(View.GONE);
                }
            }
        });
    }

    public void getData(String location){
        api.getListHouse(this, "searchActivity", new Fragment());
    }

    public void setDataHouseList(ArrayList<Rumah> data){
        mdata = data;
        adapter = new RecyclerViewAdapter(this, this, mdata, 0);
        recyclerSearch.setAdapter(adapter);
        recyclerSearch.setVisibility(View.VISIBLE);
        dropDownUp.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        getData(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }

        // User didn't trigger a refresh, let the superclass handle this action
        return super.onOptionsItemSelected(item);
    }


}
