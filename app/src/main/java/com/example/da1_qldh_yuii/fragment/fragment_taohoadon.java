package com.example.da1_qldh_yuii.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.adapter.SanPhamAdapter;
import com.example.da1_qldh_yuii.dao.SanPhamDAO;
import com.example.da1_qldh_yuii.model.SanPham;

import java.util.ArrayList;


public class fragment_taohoadon extends Fragment {


    public fragment_taohoadon() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_taohoadon, container, false);

        return view;

    }
}