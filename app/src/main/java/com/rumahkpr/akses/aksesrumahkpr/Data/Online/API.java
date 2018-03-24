package com.rumahkpr.akses.aksesrumahkpr.Data.Online;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rumahkpr.akses.aksesrumahkpr.Listener.SimulasiKPR;
import com.rumahkpr.akses.aksesrumahkpr.R;
import com.rumahkpr.akses.aksesrumahkpr.activity.AjukanKPRActivity;
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
import java.util.HashMap;
import java.util.Map;


/**
 * Created by JEMMY CALAK on 3/18/2018.
 */

public class API {

    public final String IP_ADDRESS = "http://mortgtech-eval-prod.apigee.net/btn-mortgtech/";
    ProgressDialog progressDialog = null;

    public void startLoading(final Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context, R.style.TrasparantDialog);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    public void stopLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void getListHouse(final Activity activity, final String parameter, final Fragment fragment, final String layout) {

        if (!layout.equals("moreDetail")) {
            startLoading(activity);
        }
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("keyword", parameter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("parameter " + layout, String.valueOf(jsonObject));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, IP_ADDRESS + "house-list", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response " + layout, String.valueOf(response));
                try {
                    JSONObject jsonObject1 = new JSONObject(String.valueOf(response));
                    String status = jsonObject1.getString("status");
                    if (status.equals("success")) {
                        JSONArray jsonArray = jsonObject1.getJSONArray("payload");
                        ArrayList<Rumah> data = new ArrayList<>();
                        int codition = 0;
                        Log.d("arrayLenght", String.valueOf(jsonArray.length()));
                        for (int i = 0; i < jsonArray.length(); i++) {
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

                            if (json.getString("gbr1").equals("")) {
                                rumah.setImg1("null");
                            }
                            if (json.getString("gbr2").equals("")) {
                                rumah.setImg2("null");
                            }
                            if (json.getString("gbr3").equals("")) {
                                rumah.setImg3("null");
                            }
                            if (json.getString("gbr4").equals("")) {
                                rumah.setImg4("null");
                            }
                            if (json.getString("gbr5").equals("")) {
                                rumah.setImg5("null");
                            }
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

                            if (layout.equals("searchActivity")) {
                                data.add(rumah);
                            } else if (layout.equals("searchActivity_rumah")) {
                                String keyword = json.getString("keyword");
                                if (codition == 0 && !keyword.contains("apartemen")) {
                                    Log.d("firts Add", "<<==");
                                    data.add(rumah);
                                    codition = 1;
                                } else {
                                    Log.d("return", "<<==" + isExist2(keyword, "apartemen"));
                                    if (isExist2(keyword, "apartemen")) {
                                        Log.d("data added", "<<==");
                                        data.add(rumah);
                                    }
                                }
                            } else {
                                String klaster = json.getString("klaster");
                                if (codition == 0) {
                                    Log.d("firts Add1", "<<==");
                                    data.add(rumah);
                                    codition = 1;
                                } else {
                                    Log.d("second Add1", "<<==");
                                    if (isExist(data, klaster)) {
                                        Log.d("data added1", "<<==");
                                        data.add(rumah);
                                    }
                                }
                            }
                        }

                        if (layout.equals("disekitar")) {
                            ((ListFragment) fragment).setRumahDisekitar(data);
                        } else if (layout.equals("daerah")) {
                            ((ListFragment) fragment).setRumahDaerah(data);
                        } else if (layout.equals("moreDetail")) {
                            ((DetailProductActivity) activity).setDataMoreTipe(data);
                        } else if (layout.equals("listHouseMap")) {
                            ((MapFragment) fragment).setDataRumah(data);
                        } else if (layout.equals("searchActivity")) {
                            ((SearchActivity) activity).setDataHouseList(data);
                        } else if (layout.equals("searchActivity_rumah")) {
                            ((SearchActivity) activity).setDataHouseList(data);
                        }

                    } else {
                        Log.d("Error", "status faild");
                        if (layout.equals("searchActivity")) {
                            new DialogManager().AlertOK(activity, "Data yang anda cari tidak ada.", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                        } else {
                            new DialogManager().AlertOK(activity, "Gagal mengambil data", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                        }
                    }
                    stopLoading();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                stopLoading();
                if (layout.equals("searchActivity")) {
                    new DialogManager().AlertOK(activity, "Data yang anda cari tidak ada.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                } else {
                    new DialogManager().AlertOK(activity, "Gagal mengambil data", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("Apikey", activity.getResources().getString(R.string.API_KEY_APPS));
                return hashMap;
            }
        };

        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 1;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                Log.d("RTO Result=>", error.getMessage().toString());
            }
        });
        Singletone.getmSingletone(activity).addToRequestqueue(jsonObjectRequest);
    }

    public Boolean isExist(ArrayList<Rumah> data, String name) {
        boolean result = false;
        for (int i = 0; i < data.size(); i++) {
            Log.d("kalster 1", data.get(i).getKlaster().toLowerCase() + " klaster 2" + name.toLowerCase());
            if (data.get(i).getKlaster().toLowerCase().equals(name.toLowerCase())) {
                result = false;
            } else {
                result = true;
            }
        }
        return result;
    }

    public Boolean isExist2(String keyword, String name) {
        if (keyword.toLowerCase().contains(name.toLowerCase())) {
            return  false;
        } else {
            return true;
        }
    }

    public void allConfig(final Activity activity, JSONObject parameter, Fragment fragment, String url, final String layout) {
        Log.d("parameter", parameter + " , " + url + " , " + layout);
        startLoading(activity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, IP_ADDRESS + url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", String.valueOf(response));
                try {
                    JSONObject jsonObject1 = new JSONObject(String.valueOf(response));
                    String status = jsonObject1.getString("status");
                    if (status.equals("success")) {
                        JSONObject json = jsonObject1.getJSONObject("payload");
                        if (layout.equals(SimulasiKPR.class.getName())) {
                            ((SimulasiKPRActivity) activity).hasilSimulasi(json.getString("angsuran_perbulan"));
                        } else if (layout.equals(AjukanKPRActivity.class.getName())) {
                            ((AjukanKPRActivity) activity).openFinish();
                        }
                    } else {
                        Log.d("Error", "status faild");
                        new DialogManager().AlertOK(activity, "Coba lagi pengunjung sedang penuh.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                    }
                    stopLoading();
                } catch (JSONException e) {
                    stopLoading();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage());
                NetworkResponse errorCode = error.networkResponse;
                if (errorCode != null && errorCode.data != null) {
                    Log.d("ErrorCode =>", String.valueOf(errorCode.statusCode));
                }
                Log.d("Error", error.getMessage().toString());
                new DialogManager().AlertOK(activity, "Koneksi mengalami gangguan, coba lagi.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                stopLoading();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("Apikey", activity.getResources().getString(R.string.API_KEY_APPS));
                return hashMap;
            }
        };
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 30000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 1;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                Log.d("RTO Result=>", error.getMessage().toString());
            }
        });
        Singletone.getmSingletone(activity).addToRequestqueue(jsonObjectRequest);
    }

}
