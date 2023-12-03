package com.example.da1_qldh_yuii.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.da1_qldh_yuii.fragment.frgTHD_CSP.fragment_chonsanpham;
import com.example.da1_qldh_yuii.fragment.frgTHD_CSP.fragment_taohoadon;

public class THD_CSPAdapter extends FragmentStateAdapter {
    public THD_CSPAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            return new fragment_chonsanpham();
        } else if (position == 1) {
            return new fragment_taohoadon();
        }
        return new fragment_chonsanpham();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
