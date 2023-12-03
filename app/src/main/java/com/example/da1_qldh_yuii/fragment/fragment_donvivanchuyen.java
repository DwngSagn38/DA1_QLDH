package com.example.da1_qldh_yuii.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.adapter.VanChuyenAdapter;
import com.example.da1_qldh_yuii.dao.VanChuyenDAO;
import com.example.da1_qldh_yuii.model.VanChuyen;

import java.util.ArrayList;


public class fragment_donvivanchuyen extends Fragment {


    VanChuyen vc;
    VanChuyenDAO vcDao;
    VanChuyenAdapter adapter;
    ArrayList<VanChuyen> list = new ArrayList<>();
    private RecyclerView rcvDonViVanChuyen;
    LinearLayout llDVVCNgung,llDVVCds,llDVVCadd;
    ImageView imgback;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donvivanchuyen, container, false);
        rcvDonViVanChuyen = view.findViewById(R.id.rcvDonViVanChuyen);
        llDVVCNgung = view.findViewById(R.id.llDVVCNgung);
        llDVVCds = view.findViewById(R.id.llDVVCds);
        llDVVCadd = view.findViewById(R.id.llDVVCadd);
        imgback = view.findViewById(R.id.imgback);

        SharedPreferences pref = getContext().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        int level = pref.getInt("LEVEL", 1);

        if (level == 1){
            llDVVCadd.setVisibility(View.GONE);
        }

        vcDao = new VanChuyenDAO(getContext());
        list.addAll(vcDao.getAll());
        llDVVCds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadData(list);
                loadds(llDVVCds,llDVVCadd,llDVVCNgung);
            }
        });

        llDVVCNgung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<VanChuyen> list1 = new ArrayList<>();
                for (VanChuyen vc : list){
                    if (vc.getTrangThai() == 1){
                        list1.add(vc);
                    }
                }
                loadData(list1);
                loadds(llDVVCNgung,llDVVCds,llDVVCadd);
            }
        });

        llDVVCadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vc = new VanChuyen();
                adapter = new VanChuyenAdapter(getContext(),list);
                adapter.opendialog(vc,requireActivity(),0,list);
            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back(level);
            }
        });

        return view;
    }

    public void loadData(ArrayList<VanChuyen> list){
//        ArrayList<VanChuyen> list1 = new ArrayList<>();
//        list1.addAll(list);
        rcvDonViVanChuyen.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new VanChuyenAdapter(getContext(),list);
        rcvDonViVanChuyen.setAdapter(adapter);
    }
    public void back(int level){
        llDVVCadd.setBackgroundResource(R.drawable.bg_bottom);
        llDVVCds.setBackgroundResource(R.drawable.bg_bottom);
        llDVVCNgung.setBackgroundResource(R.drawable.bg_bottom);
        llDVVCds.setVisibility(View.VISIBLE);
        llDVVCadd.setVisibility(View.VISIBLE);
        llDVVCNgung.setVisibility(View.VISIBLE);
        imgback.setVisibility(View.GONE);
        rcvDonViVanChuyen.setVisibility(View.GONE);
        if (level == 1){
            llDVVCadd.setVisibility(View.GONE);
        }
    }

    public void loadds(LinearLayout  lo1, LinearLayout lo2, LinearLayout lo3){
        lo1.setBackgroundResource(R.drawable.khungdn);
        lo2.setVisibility(View.GONE);
        lo3.setVisibility(View.GONE);
        imgback.setVisibility(View.VISIBLE);
        rcvDonViVanChuyen.setVisibility(View.VISIBLE);
    }
}