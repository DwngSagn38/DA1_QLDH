package com.example.da1_qldh_yuii.fragment.frgTHD_CSP;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.adapter.SanPhamAdapter;
import com.example.da1_qldh_yuii.dao.SanPhamDAO;
import com.example.da1_qldh_yuii.dao.TaoHoaDonDAO;
import com.example.da1_qldh_yuii.model.SanPham;
import com.example.da1_qldh_yuii.model.TaoHoaDon;

import java.util.ArrayList;


public class fragment_chonsanpham extends Fragment {


    public fragment_chonsanpham() {
        // Required empty public constructor
    }

    RecyclerView rcvTaoHoaDon;
    SanPhamAdapter adapter;
    ArrayList<SanPham> list = new ArrayList<>();
    SanPhamDAO spDao ;
    ImageView btnSelect;
    Button btnTatCa,btnSize15,btnSize20,btnSize25,btnTaoHDon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_taohoadon, container, false);
        getView(view);

        spDao = new SanPhamDAO(getContext());
        list = (ArrayList<SanPham>) spDao.getAll();
        rcvTaoHoaDon.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SanPhamAdapter(getContext(), list);
        adapter.setCurrentFragment(getActivity());
        rcvTaoHoaDon.setAdapter(adapter);
        AnSelect();

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnTatCa.getVisibility() == View.VISIBLE){
                    AnSelect();
                }else {
                    HienSelect();
                }
            }
        });
        btnTaoHDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<TaoHoaDon> list1 = new ArrayList<>();
                TaoHoaDonDAO taoHoaDonDAO = new TaoHoaDonDAO(getContext());
                list1 = (ArrayList<TaoHoaDon>) taoHoaDonDAO.getAll();
                if (list1.size() > 0){
                    Fragment taohoadonfrg = new fragment_taohoadon();
                    FragmentTransaction frg = getActivity().getSupportFragmentManager().beginTransaction();
                    frg.replace(R.id.flContent, taohoadonfrg).commit();
                }else {
                    Toast.makeText(getContext(), "Vui lòng chọn sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;

    }

    public void getView(View view){
        rcvTaoHoaDon = view.findViewById(R.id.rcvTaoHoaDon);
        btnSelect = view.findViewById(R.id.btnSelect);
        btnTatCa = view.findViewById(R.id.btnTatCa);
        btnSize15 = view.findViewById(R.id.btnSize15);
        btnSize20 = view.findViewById(R.id.btnSize20);
        btnSize25 = view.findViewById(R.id.btnSize25);
        btnTaoHDon= view.findViewById(R.id.btnTaoHDon);
    }

    public void HienSelect(){
        btnTatCa.setVisibility(View.VISIBLE);
        btnSize15.setVisibility(View.VISIBLE);
        btnSize20.setVisibility(View.VISIBLE);
        btnSize25.setVisibility(View.VISIBLE);
    }
    public void AnSelect(){
        btnTatCa.setVisibility(View.INVISIBLE);
        btnSize15.setVisibility(View.INVISIBLE);
        btnSize20.setVisibility(View.INVISIBLE);
        btnSize25.setVisibility(View.INVISIBLE);
    }
}