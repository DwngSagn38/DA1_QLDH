package com.example.da1_qldh_yuii;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.da1_qldh_yuii.fragment.fragment_doanhthu;
import com.example.da1_qldh_yuii.fragment.fragment_thongke;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public ViewPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager,lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new fragment_thongke();
            case 1:
                return new fragment_doanhthu();
            default:
                return new fragment_thongke();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
