package com.example.da1_qldh_yuii.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.da1_qldh_yuii.model.Photo;
import com.example.da1_qldh_yuii.adapter.PhotoAdapter;
import com.example.da1_qldh_yuii.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
public class fragment_trangchu extends Fragment {
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    private Timer timer;
    Button btnKhachHang, btnSanPham;

    private List<Photo> mListphoto = new ArrayList<>();
    public fragment_trangchu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trangchu, container, false);

        viewPager = view.findViewById(R.id.viewpager);
        circleIndicator = view.findViewById(R.id.circle_indicator);

        //btnKhachHang
        btnKhachHang = view.findViewById(R.id.btnKhachHang);
        btnSanPham = view.findViewById(R.id.btnSanPham);

        btnKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment khachHangFrg = new fragment_khachhang();
                FragmentTransaction frg = getActivity().getSupportFragmentManager().beginTransaction();
                frg.replace(R.id.flContent, khachHangFrg).commit();
            }
        });


        btnSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment SanPhamFrg = new fragment_sanpham_khohang();
                FragmentTransaction frg = getActivity().getSupportFragmentManager().beginTransaction();
                frg.replace(R.id.flContent, SanPhamFrg).commit();
            }
        });



        //slide show
        getListphoto(mListphoto);
        photoAdapter = new PhotoAdapter(getContext(),mListphoto);
        viewPager.setAdapter(photoAdapter);

        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        autoSlideshow(mListphoto);

        return view;
    }
    private List<Photo> getListphoto(List<Photo> list){
        list.clear();
        list.add(new Photo(R.drawable.sp_new6));
        list.add(new Photo(R.drawable.sp_new2));
        list.add(new Photo(R.drawable.sp_new3));
        list.add(new Photo(R.drawable.sp_new4));
        list.add(new Photo(R.drawable.sp_new5));
        return list;
    }

    private void autoSlideshow(List<Photo> mListphoto){
        if (mListphoto == null || mListphoto.isEmpty() || viewPager == null){
            return;
        }

        // Init timer
        if (timer != null){
            timer.cancel();
        }
        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = mListphoto.size() - 1;

                        if (currentItem < totalItem){
                            currentItem ++;
                            viewPager.setCurrentItem(currentItem);
                        }else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        },3000,3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null){
            timer.cancel();

        }
    }
}