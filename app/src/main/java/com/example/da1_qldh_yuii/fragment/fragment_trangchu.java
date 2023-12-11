package com.example.da1_qldh_yuii.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.da1_qldh_yuii.Navigation;
import com.example.da1_qldh_yuii.adapter.ThongBaoAdapter;
import com.example.da1_qldh_yuii.dao.TKeDAO;
import com.example.da1_qldh_yuii.dao.ThongBaoDAO;
import com.example.da1_qldh_yuii.fragment.frgSP_KH.frgSanPham;
import com.example.da1_qldh_yuii.fragment.frgTHD_CSP.fragment_chonsanpham;
import com.example.da1_qldh_yuii.model.Photo;
import com.example.da1_qldh_yuii.adapter.PhotoAdapter;
import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.model.ThongBao;

import java.text.NumberFormat;
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

    ListView lvThongBao;

    ArrayList<ThongBao> list;

    ThongBaoAdapter adapter;

    ThongBaoDAO tbDAO;
    Button btnKhachHang, btnSanPham,btnTaoHoaDon,btnDsachChuaGiao;

    LinearLayout lladmin;
    EditText edSearch;
    TextView tvTongSL,tvTongDH,tvTongDT;

    private List<Photo> mListphoto = new ArrayList<>();
    public fragment_trangchu() {
        // Required empty public constructor
    }
    Navigation check = new Navigation();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trangchu, container, false);

        viewPager = view.findViewById(R.id.viewpager);
        circleIndicator = view.findViewById(R.id.circle_indicator);

        lvThongBao = view.findViewById(R.id.lvThongBao);
        lladmin = view.findViewById(R.id.lladmin);
        edSearch = view.findViewById(R.id.edSearch);
        tvTongSL = view.findViewById(R.id.tvTongSL);
        tvTongDH = view.findViewById(R.id.tvTongDH);
        tvTongDT = view.findViewById(R.id.tvTongDT);
        btnDsachChuaGiao = view.findViewById(R.id.btnDsachChuaGiao);

        SharedPreferences pref = getContext().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        int level = pref.getInt("LEVEL", 1);
        if (level == 0){
            lladmin.setVisibility(View.VISIBLE);
            btnDsachChuaGiao.setVisibility(View.VISIBLE);
            getThongKe();
        }else {
            edSearch.setVisibility(View.VISIBLE);
        }
        // check statust hóa đơn
        check.checkStatus(getContext());

        btnDsachChuaGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment chonspfrg = new fragment_hoadon();
                FragmentTransaction frg = getActivity().getSupportFragmentManager().beginTransaction();
                frg.replace(R.id.flContent, chonspfrg).commit();
            }
        });

        //btnKhachHang
        btnKhachHang = view.findViewById(R.id.btnKhachHang);
        btnSanPham = view.findViewById(R.id.btnSanPham);
        btnTaoHoaDon = view.findViewById(R.id.btnTaoHoaDon);

        tbDAO = new ThongBaoDAO(getActivity());
        list = (ArrayList<ThongBao>) tbDAO.getAll();
        adapter = new ThongBaoAdapter(getActivity(),list,true);
        lvThongBao.setAdapter(adapter);

        btnKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment khachHangFrg = new fragment_khachhang();
                FragmentTransaction frg = getActivity().getSupportFragmentManager().beginTransaction();
                frg.replace(R.id.flContent, khachHangFrg).commit();
            }
        });

        btnTaoHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment chonspfrg = new fragment_chonsanpham();
                FragmentTransaction frg = getActivity().getSupportFragmentManager().beginTransaction();
                frg.replace(R.id.flContent, chonspfrg).commit();
            }
        });


        btnSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment SanPhamFrg = new frgSanPham();
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
            onDestroy();
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
            timer = null;
        }
    }

    public void getThongKe(){
        TKeDAO tkDAO = new TKeDAO(getContext());
        tvTongDH.setText(tkDAO.getTongDonHang()+" đơn");
        tvTongSL.setText(tkDAO.getTongSoLuong()+"");
        NumberFormat numberFormat = NumberFormat.getInstance();
        String formattedDoanhThu = numberFormat.format(tkDAO.getDoanhThu());
        tvTongDT.setText(formattedDoanhThu+" VNĐ");
        btnDsachChuaGiao.setText("Bạn có "+tkDAO.getDHDaGiao(0)+" đơn hàng đang chờ xử lý");
    }
}