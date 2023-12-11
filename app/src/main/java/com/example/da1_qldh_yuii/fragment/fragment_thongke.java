package com.example.da1_qldh_yuii.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.adapter.ThongKeAdapter;
import com.example.da1_qldh_yuii.dao.TKeDAO;
import com.example.da1_qldh_yuii.model.SanPham;

import java.util.ArrayList;


public class fragment_thongke extends Fragment {

    TextView tvthongKe,tvAllDH,tvChuaGiao,tvDaGiao,tvHuy,tvDangGiao;
    LinearLayout llTKDH;
    TKeDAO tkDAO;
    ThongKeAdapter adapter;
    ArrayList<SanPham> list = new ArrayList<>();

    RecyclerView rcvThongKe;

    Button btnTatCa,btnTop5,btnDonHang;
    public fragment_thongke() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_thongke, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getView(view);

        tkDAO = new TKeDAO(getContext());
        list.addAll(tkDAO.getAll());
        loadData(list);
        setClickButton(btnTatCa,btnTop5,btnDonHang);
        rcvThongKe.setVisibility(View.VISIBLE);
        llTKDH.setVisibility(View.GONE);

        tvthongKe.setText("Tổng: "+ tkDAO.getTongSoLuong());
        btnTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData(list);
                setClickButton(btnTatCa,btnTop5,btnDonHang);
                rcvThongKe.setVisibility(View.VISIBLE);
                llTKDH.setVisibility(View.GONE);
                tvthongKe.setText("Tổng: "+ tkDAO.getTongSoLuong());
            }
        });

        btnTop5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<SanPham> listTop5 = new ArrayList<>();
                listTop5.addAll(tkDAO.getTop5());
                loadData(listTop5);
                setClickButton(btnTop5,btnTatCa,btnDonHang);
                rcvThongKe.setVisibility(View.VISIBLE);
                llTKDH.setVisibility(View.GONE);
                tvthongKe.setText("Tổng: "+ tkDAO.getTongSoLuongTop5());
            }
        });

        btnDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClickButton(btnDonHang,btnTop5,btnTatCa);
                rcvThongKe.setVisibility(View.GONE);
                llTKDH.setVisibility(View.VISIBLE);
                tvthongKe.setText("Tổng: "+ tkDAO.getTongDonHang());
                tvDaGiao.setText("Đơn hàng đã giao : "+tkDAO.getDHDaGiao(1));
                tvDangGiao.setText("Đơn hàng đang giao : "+tkDAO.getDHDaGiao(2));
                tvChuaGiao.setText("Đơn hàng chưa giao : "+tkDAO.getDHDaGiao(0));
                tvHuy.setText("Đơn hàng đã hủy : "+tkDAO.getDHDaGiao(3));
            }
        });
    }

    public void loadData(ArrayList<SanPham> list){
        rcvThongKe.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ThongKeAdapter(getContext(),list);
        rcvThongKe.setAdapter(adapter);
    }

    public void setClickButton(Button b1,Button b2, Button b3){
        b1.setBackgroundResource(R.drawable.khungdn);
        b2.setBackgroundResource(R.drawable.khung);
        b3.setBackgroundResource(R.drawable.khung);
        b1.setTextColor(Color.WHITE);
        b2.setTextColor(Color.BLUE);
        b3.setTextColor(Color.BLUE);
    }
    public void getView(View view){
        rcvThongKe = view.findViewById(R.id.rcvThongKe);
        btnDonHang =view.findViewById(R.id.btnDonHang);
        btnTop5 = view.findViewById(R.id.btnTop5);
        btnTatCa =view.findViewById(R.id.btnTatCa);
        tvthongKe = view.findViewById(R.id.tvthongKe);
        tvChuaGiao = view.findViewById(R.id.tvChuaGiao);
        tvHuy =view.findViewById(R.id.tvHuy);
        tvDaGiao = view.findViewById(R.id.tvDaGiao);
        tvDangGiao = view.findViewById(R.id.tvDangGiao);
        llTKDH =view.findViewById(R.id.llTKDH);

    }
}