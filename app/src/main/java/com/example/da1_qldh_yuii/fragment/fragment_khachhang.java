package com.example.da1_qldh_yuii.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.da1_qldh_yuii.DangNhap;
import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.adapter.KhachHangAdapter;

import com.example.da1_qldh_yuii.adapter.ThanhVienAdapter;
import com.example.da1_qldh_yuii.dao.KhachHangDAO;

import com.example.da1_qldh_yuii.model.KhachHang;

import com.example.da1_qldh_yuii.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;


public class fragment_khachhang extends Fragment {

    ArrayList<KhachHang> list = new ArrayList<>();
    KhachHangDAO khachHangDAO;
    RecyclerView recyclerViewKhachHang;
    DangNhap dn = new DangNhap();


    KhachHangAdapter adapter;
    private SearchView searchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               handleSearch(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_khachhang, container, false);

        recyclerViewKhachHang = view.findViewById(R.id.rcvKhachHang);
        FloatingActionButton floatAddKH = view.findViewById(R.id.floatAddKhachHang);


        loadData();

        // Lấy reference đến NavController



        floatAddKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KhachHang kh = new KhachHang();
                showDialog(kh);
            }
        });

        return view;
    }


    //load data
    private void loadData() {

        khachHangDAO = new KhachHangDAO(getContext());
        list = khachHangDAO.getDSKhachHang();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewKhachHang.setLayoutManager(linearLayoutManager);
        KhachHangAdapter adapter = new KhachHangAdapter(getContext(), list);
        recyclerViewKhachHang.setAdapter(adapter);

    }


    public void showDialog(KhachHang kh) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dl_them_khachhang, null);
        builder.setView(view);


        EditText edMaKHadd = view.findViewById(R.id.edMaKHadd);
        EditText edTenKHadd = view.findViewById(R.id.edTenKHadd);
        EditText edSDTadd = view.findViewById(R.id.edSDTadd);
        EditText edDiaChiadd = view.findViewById(R.id.edDiaChiadd);

        //thêm

        builder.setNegativeButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String maKH = edMaKHadd.getText().toString();
                String tenKH = edTenKHadd.getText().toString();
                String sdtKH = edSDTadd.getText().toString();
                String diaChiKH = edDiaChiadd.getText().toString();


                if (maKH.isEmpty() || tenKH.isEmpty() || sdtKH.isEmpty() || diaChiKH.isEmpty()){
                    Toast.makeText(getContext(), "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                } else if (khachHangDAO.checkMaKH(maKH)) {
                    Toast.makeText(getContext(), "Mã khách hàng đã được sử dụng", Toast.LENGTH_SHORT).show();
                } else if (!isso(sdtKH)) {
                    Toast.makeText(getContext(), "Số điện thoại chưa đúng", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = khachHangDAO.themKhachHang(maKH,tenKH,sdtKH,diaChiKH);
                    if (check){
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        //load data
                        loadData();

                    }else{
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        //hủy
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public boolean isso(String so){
        return so.matches("\\d+");
    }


    private String removeDau(String str) {
        String strKhongDau = Normalizer.normalize(str, Normalizer.Form.NFD);
        return strKhongDau.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
    private void handleSearch(String query) {
        List<KhachHang> listSearch = new ArrayList<>();
        String tenTimKhongDau = removeDau(query.toLowerCase());
        for (KhachHang kh : list) {
            String tenKhongDau = removeDau(kh.getTenKhachHang().toLowerCase());
            if (tenKhongDau.contains(tenTimKhongDau)) {
                listSearch.add(kh);
            }
        }
        adapter = new KhachHangAdapter(getActivity(), (ArrayList<KhachHang>) listSearch);
        recyclerViewKhachHang.setAdapter(adapter);

    }



}



