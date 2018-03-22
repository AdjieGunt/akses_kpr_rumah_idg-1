package com.rumahkpr.akses.aksesrumahkpr.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.rumahkpr.akses.aksesrumahkpr.Listener.SimulasiKPR;
import com.rumahkpr.akses.aksesrumahkpr.R;
import com.rumahkpr.akses.aksesrumahkpr.model.Rumah;
import com.rumahkpr.akses.aksesrumahkpr.util.formatNominal;

/**
 * A simple {@link Fragment} subclass.
 */
public class SimulasikprFragment extends Fragment{
    private EditText uangMuka, lamaPinjam, bunga, nama, harga;
    private Button hitung;
    private SimulasiKPR simulasiKPR;
    private String mnilaiPinjaman="0", mbunga="0", mlamapinjam="0", muangMuka="0", hargaHouse;
    private Bundle bundle;
    private Rumah rumah;
    private formatNominal formatNominal;

    public SimulasikprFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simulaikpr, container, false);
        // Inflate the layout for this fragment
        initXml(view);
        formatNominal = new formatNominal();
        getDataBundle();

        uangMuka.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                muangMuka = uangMuka.getText().toString();
                mnilaiPinjaman = String.valueOf((Integer.valueOf(rumah.getHarga()) - Integer.valueOf(muangMuka)));
                simulasiKPR.hitungSimulasi(mnilaiPinjaman, mlamapinjam, mbunga);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        lamaPinjam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mlamapinjam = lamaPinjam.getText().toString();
                simulasiKPR.hitungSimulasi(mnilaiPinjaman, mlamapinjam, mbunga);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        bunga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mbunga = bunga.getText().toString();
                simulasiKPR.hitungSimulasi(mnilaiPinjaman, mlamapinjam, mbunga);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void getDataBundle() {
        bundle = getArguments();
        rumah = (Rumah)bundle.getSerializable("rumah");

        nama.setText(rumah.getKlaster().toUpperCase());
        harga.setText("Rp. "+formatNominal.nominal(Integer.valueOf(rumah.getHarga())));
    }

    private void initXml(View view) {
        uangMuka = (EditText) view.findViewById(R.id.uangMuka);
        lamaPinjam = (EditText) view.findViewById(R.id.lamaPinjaman);
        bunga = (EditText) view.findViewById(R.id.sukuBungaPtahun);
        nama = (EditText) view.findViewById(R.id.namaSimulasi);
        harga = (EditText) view.findViewById(R.id.hargaSimulai);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            simulasiKPR = (SimulasiKPR)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.simulasiKPR = null;
    }

    public void setDataHouse(Rumah rumah){
        Log.d(rumah.getAlamat(), "<<====");
    }
}
