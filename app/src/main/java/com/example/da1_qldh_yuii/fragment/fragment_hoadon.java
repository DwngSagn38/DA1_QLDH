package com.example.da1_qldh_yuii.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.adapter.HoaDonAdapter;
import com.example.da1_qldh_yuii.dao.HoaDonDAO;
import com.example.da1_qldh_yuii.fragment.frgTHD_CSP.fragment_chonsanpham;
import com.example.da1_qldh_yuii.model.HoaDon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class fragment_hoadon extends Fragment {

    Button btnChuaGiao,btnTatCa,btnDaGiao,btnHuy,btnDangGiao;
    RecyclerView rcvHoaDon;

    FloatingActionButton flAddHD;

    HoaDonAdapter adapter;
    ArrayList<HoaDon> list = new ArrayList<>();

    HoaDonDAO hdDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_hoadon, container, false);
        flAddHD = view.findViewById(R.id.flAddHD);
        btnChuaGiao = view.findViewById(R.id.btnChuaGiao);
        btnTatCa = view.findViewById(R.id.btnTatCa);
        btnDaGiao = view.findViewById(R.id.btnDaGiao);
        btnHuy = view.findViewById(R.id.btnHuy);
        btnDangGiao = view.findViewById(R.id.btnDangGiao);
        rcvHoaDon = view.findViewById(R.id.rcvHoaDon);
        flAddHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment chonspfrg = new fragment_chonsanpham();
                FragmentTransaction frg = getActivity().getSupportFragmentManager().beginTransaction();
                frg.replace(R.id.flContent, chonspfrg).commit();
            }
        });

        hdDAO = new HoaDonDAO(getContext());
        list = (ArrayList<HoaDon>) hdDAO.getAll();
        loadData(list);
        setClickButton(btnTatCa,btnDaGiao,btnChuaGiao,btnHuy,btnDangGiao);
        btnTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData(list);
                setClickButton(btnTatCa,btnDaGiao,btnChuaGiao,btnHuy,btnDangGiao);
            }
        });

        btnDaGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<HoaDon> list1 = new ArrayList<>();
                list1.addAll(hdDAO.getListStatus(1));
                loadData(list1);
                setClickButton(btnDaGiao,btnTatCa,btnChuaGiao,btnHuy,btnDangGiao);
            }
        });

        btnChuaGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<HoaDon> list1 = new ArrayList<>();
                list1.addAll(hdDAO.getListStatus(0));
                loadData(list1);
                setClickButton(btnChuaGiao,btnTatCa,btnDaGiao,btnHuy,btnDangGiao);
            }
        });

        btnDangGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<HoaDon> list1 = new ArrayList<>();
                list1.addAll(hdDAO.getListStatus(2));
                loadData(list1);
                setClickButton(btnDangGiao,btnTatCa,btnDaGiao,btnHuy,btnChuaGiao);
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<HoaDon> list1 = new ArrayList<>();
                list1.addAll(hdDAO.getListStatus(3));
                loadData(list1);
                setClickButton(btnHuy,btnTatCa,btnDaGiao,btnChuaGiao,btnDangGiao);
            }
        });

        return view;
    }

    public void loadData(ArrayList<HoaDon> list){
        rcvHoaDon.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HoaDonAdapter(getContext(),list);
        rcvHoaDon.setAdapter(adapter);
    }
    public void setClickButton(Button b1,Button b2, Button b3, Button b4,Button b5){
        b1.setBackgroundResource(R.drawable.khungdn);
        b2.setBackgroundResource(R.drawable.khung);
        b3.setBackgroundResource(R.drawable.khung);
        b4.setBackgroundResource(R.drawable.khung);
        b5.setBackgroundResource(R.drawable.khung);
        b1.setTextColor(Color.WHITE);
        b2.setTextColor(Color.BLUE);
        b3.setTextColor(Color.BLUE);
        b4.setTextColor(Color.BLUE);
        b5.setTextColor(Color.BLUE);
    }
}