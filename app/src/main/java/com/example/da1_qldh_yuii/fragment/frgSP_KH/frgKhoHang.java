package com.example.da1_qldh_yuii.fragment.frgSP_KH;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.da1_qldh_yuii.R;

public class frgKhoHang extends Fragment {


    public frgKhoHang() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_frg_kho_hang, container, false);
        Toast.makeText(getContext(), "Chức năng đang được cải thiện", Toast.LENGTH_SHORT).show();
        return view;
    }
}