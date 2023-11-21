package com.example.da1_qldh_yuii.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.da1_qldh_yuii.fragment.frgSP_KH.frgKhoHang;
import com.example.da1_qldh_yuii.fragment.frgSP_KH.frgSanPham;

import java.util.ArrayList;
import java.util.List;

public class SanPham_KhoHangAdapter extends FragmentStateAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> headerList = new ArrayList<>();

    public SanPham_KhoHangAdapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        initData();
    }

    private void initData(){
        addData(new frgSanPham(), "Sản phẩm");
        addData(new frgKhoHang(), "Kho hàng");

    }

    private void addData(Fragment fragment, String header){
        fragmentList.add(fragment);
        headerList.add(header);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    public  String getHeader(int position){
        return headerList.get(position);
    }
}
