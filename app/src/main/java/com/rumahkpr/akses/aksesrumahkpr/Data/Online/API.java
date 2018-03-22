package com.rumahkpr.akses.aksesrumahkpr.Data.Online;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rumahkpr.akses.aksesrumahkpr.activity.DetailProductActivity;
import com.rumahkpr.akses.aksesrumahkpr.activity.SearchActivity;
import com.rumahkpr.akses.aksesrumahkpr.activity.SimulasiKPRActivity;
import com.rumahkpr.akses.aksesrumahkpr.fragment.ListFragment;
import com.rumahkpr.akses.aksesrumahkpr.fragment.MapFragment;
import com.rumahkpr.akses.aksesrumahkpr.model.Rumah;
import com.rumahkpr.akses.aksesrumahkpr.util.DialogManager;
import com.rumahkpr.akses.aksesrumahkpr.util.Singletone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by JEMMY CALAK on 3/18/2018.
 */

public class API {

    public final String IP_ADDRESS = "https://btn-rest-api.firebaseio.com/";
    ProgressBar progressBar;

    public void getListHouse(final Activity activity, final String parameter, final Fragment fragment){
        Log.d("parameter", parameter);
        startLoading(activity);
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("keyword", parameter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, IP_ADDRESS+"house-list.json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", String.valueOf(response));
                try {
                    JSONObject jsonObject1 = new JSONObject(String.valueOf(response)) ;
                    String status = jsonObject1.getString("status");
                    if(status.equals("success")){
                        JSONArray jsonArray = jsonObject1.getJSONArray("payload");
                        ArrayList<Rumah> data = new ArrayList<>();
                        for(int i=0; i<jsonArray.length(); i++){
                            JSONObject json = jsonArray.getJSONObject(i);
                            Rumah rumah = new Rumah();
                            rumah.setAlamat(json.getString("alamat"));
                            rumah.setBlok(json.getString("blok"));
                            rumah.setDaya_listrik(json.getString("daya_listrik"));
                            rumah.setDeskripsi(json.getString("deskripsi"));
                            rumah.setGarasi(json.getString("garasi"));
                            rumah.setImg1(json.getString("gbr1"));
                            rumah.setImg2(json.getString("gbr2"));
                            rumah.setImg3(json.getString("gbr3"));
                            rumah.setImg4(json.getString("gbr4"));
                            rumah.setImg5(json.getString("gbr5"));

                            if(json.getString("gbr1").equals("")){
                                rumah.setImg1("null");
                            }
                            if(json.getString("gbr2").equals("")){
                                rumah.setImg2("null");
                            }
                            if(json.getString("gbr3").equals("")){
                                rumah.setImg3("null");
                            }
                            if(json.getString("gbr4").equals("")){
                                rumah.setImg4("null");
                            }
                            if(json.getString("gbr5").equals("")){
                                rumah.setImg5("null");
                            }

                            Log.d("imga2", json.getString("gbr5"));
                            rumah.setHarga(json.getString("harga"));
                            rumah.setId_booking(json.getString("id_booking"));
                            rumah.setId_desa_keluraha(json.getString("id_desa_kelurahan"));
                            rumah.setId_kab_kota(json.getString("id_kab_kota"));
                            rumah.setId_kecamatan(json.getString("id_kecamatan"));
                            rumah.setId_provinsi(json.getString("id_propinsi"));
                            rumah.setId_stk_dev(json.getString("id_stk_dev"));
                            rumah.setId_stk_kavling(json.getString("id_stk_kavling"));
                            rumah.setId_stk_proyek(json.getString("id_stk_proyek"));
                            rumah.setId_tipe_rumah(json.getString("id_tipe_rumah"));
                            rumah.setJalur_pdam(json.getString("jalur_pdam"));
                            rumah.setJalur_pdam_lama(json.getString("jalur_pdam_lama"));
                            rumah.setJalur_telepon_lama(json.getString("jalur_telepon_lama"));
                            rumah.setJalur_telp(json.getString("jalur_telp"));
                            rumah.setJenis(json.getString("jenis"));
                            rumah.setJenis_lama(json.getString("jenis_lama"));
                            rumah.setJenis_lelang(json.getString("jenis_lelang"));
                            rumah.setJml_dilihat(json.getString("jml_dilihat"));
                            rumah.setKamar_mandi(json.getString("kamar_mandi"));
                            rumah.setKamar_tidur(json.getString("kamar_tidur"));
                            rumah.setKategori_agunan(json.getString("kategori_agunan"));
                            rumah.setKeyword(json.getString("keyword"));
                            rumah.setKlaster(json.getString("klaster"));
                            rumah.setKodepos(json.getString("kodepos"));
                            rumah.setLantai(json.getString("lantai"));
                            rumah.setLatitude(json.getString("latitude"));
                            rumah.setLongitude(json.getString("longitude"));
                            rumah.setLuas_bangunan(json.getString("luas_bangunan"));
                            rumah.setLuas_tanah(json.getString("luas_tanah"));
                            rumah.setNama(json.getString("nama"));
                            rumah.setNomor(json.getString("no"));
                            rumah.setRespons_eloan(json.getString("respon_eloan"));
                            rumah.setSertifikat(json.getString("sertifikat"));
                            rumah.setStatus_eloan(json.getString("status_eloan"));
                            rumah.setStatus_jual(json.getString("status_jual"));
                            rumah.setStatus_pengajuan(json.getString("status_pengajuan"));
                            rumah.setStatus_pengajuan_lama(json.getString("status_pengajuan_lama"));
                            rumah.setStatus_unit(json.getString("status_unit"));
                            rumah.setStatus_unit_lama(json.getString("status_unit_lama"));
                            rumah.setSubsidi(json.getString("subsidi"));
                            rumah.setSubsidi_lama(json.getString("subsidi_lama"));
                            rumah.setTahun_bangun(json.getString("tahun_bangun"));
                            rumah.setVideo(json.getString("video"));
                            data.add(rumah);
                        }
                        if(parameter.equals("disekitar")){
                            ((ListFragment)fragment).setRumahDisekitar(data);
                        }else if(parameter.equals("daerah")){
                            ((ListFragment)fragment).setRumahDaerah(data);
                        }else if(parameter.equals("moreDetail")){
                            ((DetailProductActivity)activity).setDataMoreTipe(data);
                        }else if(parameter.equals("listHouseMap")){
                            ((MapFragment)fragment).setDataRumah(data);
                        }else if(parameter.equals("searchActivity")){
                            ((SearchActivity)activity).setDataHouseList(data);
                        }

                    }else{
                        Log.d("Error", "status faild");
                        new DialogManager().AlertOK(activity, "Gagal mengambil data", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                    }
                    stopLoading(activity);
                } catch (JSONException e) {
                    stopLoading(activity);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                stopLoading(activity);
                Log.d("Error", error.getMessage().toString());
                new DialogManager().AlertOK(activity, "Gagal mengambil data", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        Singletone.getmSingletone(activity).addToRequestqueue(jsonObjectRequest);
    }

    public void simulasiKPR(final Activity activity, final String nilai_pinjam, final String bunga, final int bulan){
        Log.d("parameter", nilai_pinjam+" , "+bunga+" , "+bulan);
        startLoading(activity);
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nilai_pinjam", nilai_pinjam);
            jsonObject.put("interest", bunga);
            jsonObject.put("jangka_waktu", bulan);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, IP_ADDRESS+"credit-simulation.json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", String.valueOf(response));
                try {
                    JSONObject jsonObject1 = new JSONObject(String.valueOf(response)) ;
                    String status = jsonObject1.getString("status");
                    if(status.equals("success")){
                        JSONObject json = jsonObject1.getJSONObject("payload");
                        ((SimulasiKPRActivity)activity).hasilSimulasi(json.getString("angsuran_perbulan"));
//                        ((SimulasiKPRActivity)activity).hasilSimulasiFragment(json.getString("angsuran_perbulan"));

                    }else{
                        Log.d("Error", "status faild");
                        new DialogManager().AlertOK(activity, "Coba lagi pengunjung sedang penuh.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                    }
                    stopLoading(activity);
                } catch (JSONException e) {
                    stopLoading(activity);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                stopLoading(activity);
                Log.d("Error", error.getMessage().toString());
                new DialogManager().AlertOK(activity, "Gagal mengambil data", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        Singletone.getmSingletone(activity).addToRequestqueue(jsonObjectRequest);
    }

    public void startLoading(Context context){
        if(progressBar == null){
            progressBar = new ProgressBar(context);
            progressBar.setProgress(1);
            progressBar.setMax(50);
        }
        progressBar.setVisibility(View.VISIBLE);
    }

    public void stopLoading(Context context){
        if(progressBar != null && progressBar.isShown()){
            progressBar.setVisibility(View.GONE);
        }
    }



}
