package com.example.da1_qldh_yuii.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.da1_qldh_yuii.fragment.frgSP_KH.frgKhoHang;
import com.example.da1_qldh_yuii.fragment.frgSP_KH.frgSanPham;

import java.util.ArrayList;
import java.util.List;

public class SanPham_KhoHangAdapter extends FragmentStateAdapter {


    public SanPham_KhoHangAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            return new frgSanPham();
        }else if (position == 1){
            return new frgKhoHang();
        }

        return new frgSanPham();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
