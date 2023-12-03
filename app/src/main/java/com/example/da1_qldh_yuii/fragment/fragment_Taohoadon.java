package com.example.da1_qldh_yuii.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.adapter.SanPham_KhoHangAdapter;
import com.example.da1_qldh_yuii.adapter.THD_CSPAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class fragment_Taohoadon extends Fragment {

    public fragment_Taohoadon() {
        // Required empty public constructor
    }
    private TabLayoutMediator tabLayoutMediator;
    private THD_CSPAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment__taohoadon, container, false);


        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.vp2);

        adapter = new THD_CSPAdapter(requireActivity());
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0){
                    tab.setText("Chọn sản phẩm");
                } else if (position == 1) {
                    tab.setText("Tạo hóa đơn");
                }
            }
        }).attach();


        return view;
    }
}