package com.example.da1_qldh_yuii.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.da1_qldh_yuii.DangNhap;
import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.adapter.ThanhVienAdapter;
import com.example.da1_qldh_yuii.dao.ThanhVienDAO;
import com.example.da1_qldh_yuii.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class fragment_thanhvien extends Fragment {

    private RecyclerView rcvThanhVien;
    private FloatingActionButton floatAddThanhVien;

    private ThanhVienAdapter adapter;

    private ThanhVienDAO tvDAO;
    DangNhap dangNhap = new DangNhap();

    ArrayList<ThanhVien> list = new ArrayList<>();

    public fragment_thanhvien() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thanhvien, container, false);
        rcvThanhVien = view.findViewById(R.id.rcvThanhVien);
        floatAddThanhVien = view.findViewById(R.id.floatAddThanhVien);
        tvDAO = new ThanhVienDAO(getContext());

        loadData();

        floatAddThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangNhap.openDialog(getContext(),1,list);
                loadData();
            }
        });

        return view;
    }

    public void loadData() {
        list.clear();
        list.addAll(tvDAO.getAll());
        rcvThanhVien.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ThanhVienAdapter(getContext(), list);
        rcvThanhVien.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}