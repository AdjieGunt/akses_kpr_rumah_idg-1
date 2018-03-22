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

/**
 * A simple {@link Fragment} subclass.
 */
public class SimulasikprFragment extends Fragment implements SimulasiKPR {
    private EditText uangMuka, lamaPinjam, bunga;
    private Button hitung;
    private SimulasiKPR simulasiKPR;
    private String mnilaiPinjaman, mbunga, mlamapinjam, muangMuka, hargaHouse;

    public SimulasikprFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simulaikpr, container, false);
        // Inflate the layout for this fragment
        uangMuka = (EditText) view.findViewById(R.id.uangMuka);
        lamaPinjam = (EditText) view.findViewById(R.id.lamaPinjaman);
        bunga = (EditText) view.findViewById(R.id.sukuBungaPtahun);

        uangMuka.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                muangMuka = uangMuka.getText().toString();
                mnilaiPinjaman = String.valueOf((1000000000 - Integer.valueOf(muangMuka)));
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

    @Override
    public void hitungSimulasi(String nilaiPinjaman, String lamaPinjaman, String sukuBunga) {

    }
}
