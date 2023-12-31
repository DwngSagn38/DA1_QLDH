package com.example.da1_qldh_yuii.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.da1_qldh_yuii.Navigation;
import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.adapter.SanPham_KhoHangAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class fragment_sanpham_khohang extends Fragment {

    private TabLayoutMediator tabLayoutMediator;
    private SanPham_KhoHangAdapter adapter;

    public fragment_sanpham_khohang(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sanpham_khohang, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.vp2);

        adapter = new SanPham_KhoHangAdapter(requireActivity());
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0){
                    tab.setText("Sản phẩm");
                } else if (position == 1) {
                    tab.setText("Kho hàng");
                }
            }
        }).attach();

        return view;



    }
}