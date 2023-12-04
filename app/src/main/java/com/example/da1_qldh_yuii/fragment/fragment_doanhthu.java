package com.example.da1_qldh_yuii.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.dao.ThongKeDAO;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class fragment_doanhthu extends Fragment {

    private int initialYear;
    private int initialMonth;
    private int initialDay;
    ThongKeDAO tkDAO;

    Button btnThangNay,btnTuanNay,btnHomNay;
    TextView tvDoanhThu,tvTime;
    fragment_thongke frgtk = new fragment_thongke();
    public fragment_doanhthu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_doanhthu, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvDoanhThu = view.findViewById(R.id.tvDoanhThu);
        tvTime = view.findViewById(R.id.tvTime);
        btnHomNay = view.findViewById(R.id.btnHomNay);
        btnTuanNay = view.findViewById(R.id.btnTuanNay);
        btnThangNay = view.findViewById(R.id.btnThangNay);


        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        initialYear = calendar.get(Calendar.YEAR);
        initialMonth = calendar.get(Calendar.MONTH);
        initialDay = calendar.get(Calendar.DAY_OF_MONTH);

        tkDAO = new ThongKeDAO(getContext());

        String homnay = initialDay + "/" + (initialMonth + 1) + "/" + initialYear;
        long doanhThu = tkDAO.getDoanhThu();
        NumberFormat numberFormat = NumberFormat.getInstance();
        String formattedDoanhThu = numberFormat.format(doanhThu);
        tvDoanhThu.setText(formattedDoanhThu+".000 VNĐ");
        tvTime.setText("Tổng");

        btnHomNay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frgtk.setClickButton(btnHomNay,btnThangNay,btnTuanNay);
                Toast.makeText(getContext(), "Chức năng đang được cải thiện", Toast.LENGTH_SHORT).show();
            }
        });
        btnTuanNay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frgtk.setClickButton(btnTuanNay,btnHomNay,btnThangNay);
                Toast.makeText(getContext(), "Chức năng đang được cải thiện", Toast.LENGTH_SHORT).show();
            }
        });
        btnThangNay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frgtk.setClickButton(btnThangNay,btnHomNay,btnTuanNay);
                Toast.makeText(getContext(), "Chức năng đang được cải thiện", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}