package com.example.da1_qldh_yuii.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.dao.TKeDAO;
import com.example.da1_qldh_yuii.model.SanPham;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class fragment_doanhthu extends Fragment {

    private int initialYear;
    private int initialMonth;
    private int initialDay;
    TKeDAO tkDAO;
    PieChart piechart;

    Button btnThangNay,btnTuanNay,btnHomNay;
    TextView tvDoanhThu,tvTime;
    ImageView imgAll;
    fragment_thongke frgtk = new fragment_thongke();
    ArrayList<SanPham> list = new ArrayList<>();
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
        piechart = view.findViewById(R.id.piechart);
        imgAll = view.findViewById(R.id.imgAll);
        tkDAO = new TKeDAO(getContext());
        list = tkDAO.getDoanhThuTheoMaSP();

        getBieuDo(list);

        long doanhThu = tkDAO.getDoanhThu();
        numberFormat(doanhThu);
        tvTime.setText("Tổng doanh thu");
        imgAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long doanhThu = tkDAO.getDoanhThu();
                numberFormat(doanhThu);
                tvTime.setText("Tổng doanh thu");
                getBieuDo(list);
            }
        });

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        btnHomNay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frgtk.setClickButton(btnHomNay,btnThangNay,btnTuanNay);
                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime(); // Lấy ngày hôm nay
                String currentDateStr = dateFormat.format(currentDate);
                calendar.add(Calendar.DAY_OF_MONTH, 1); // Thêm 1 ngày vào ngày hiện tại
                Date tomorrowDate = calendar.getTime(); // Lấy ngày mới
                String tomorrowDateStr = dateFormat.format(tomorrowDate); // Định dạng ngày mới thành chuỗi
                long doanhThuHomNay = tkDAO.getDoanhThuTheoNgay(currentDateStr,tomorrowDateStr);
                numberFormat(doanhThuHomNay);
                tvTime.setText("Đơn hàng có ngày hoàn thành : \n"+currentDateStr);

                // set biểu đồ
                ArrayList<SanPham> list1 = new ArrayList<>();
                list1 = tkDAO.getDoanhThuTheoMaSPTheoNgay(currentDateStr,tomorrowDateStr);
                getBieuDo(list1);
            }
        });
        btnTuanNay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frgtk.setClickButton(btnTuanNay, btnHomNay, btnThangNay);

                Calendar calendar = Calendar.getInstance();

                // Thiết lập ngày trong tuần là Thứ Hai (Calendar.MONDAY)
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                Date monday = calendar.getTime();
                String thuHai = dateFormat.format(monday);

                // Thiết lập ngày trong tuần là Chủ Nhật (Calendar.SUNDAY)
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                Date sunday = calendar.getTime();
                String chuNhat = dateFormat.format(sunday);

                long doanhThuTuan = tkDAO.getDoanhThuTheoNgay(thuHai, chuNhat);
                numberFormat(doanhThuTuan);
                tvTime.setText("Đơn hàng có ngày hoàn thành : \n"+thuHai+" - "+chuNhat);

                // set biểu đồ
                ArrayList<SanPham> list1 = new ArrayList<>();
                list1 = tkDAO.getDoanhThuTheoMaSPTheoNgay(thuHai,chuNhat);
                getBieuDo(list1);
            }
        });
        btnThangNay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frgtk.setClickButton(btnThangNay, btnHomNay, btnTuanNay);
                Calendar calendar = Calendar.getInstance();

                // Lấy ngày đầu tháng
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                Date dauThang = calendar.getTime();
                String ngayDau = dateFormat.format(dauThang);

                // Lấy ngày cuối tháng
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                Date cuoiThang = calendar.getTime();
                String ngayCuoi = dateFormat.format(cuoiThang);

                long doanhThuThang = tkDAO.getDoanhThuTheoNgay(ngayDau, ngayCuoi);
                numberFormat(doanhThuThang);
                tvTime.setText("Đơn hàng có ngày hoàn thành : \n"+ngayDau+" - "+ngayCuoi);

                // set biểu đồ
                ArrayList<SanPham> list1 = new ArrayList<>();
                list1 = tkDAO.getDoanhThuTheoMaSPTheoNgay(ngayDau,ngayCuoi);
                getBieuDo(list1);
            }
        });
    }
    public void numberFormat(long doanhThu){
        NumberFormat numberFormat = NumberFormat.getInstance();
        String formattedDoanhThu = numberFormat.format(doanhThu);
        tvDoanhThu.setText(formattedDoanhThu+" VNĐ");
    }

    public void getBieuDo(ArrayList<SanPham> list1){

        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<SanPham> list = new ArrayList<>(list1);

        for (SanPham sp : list){
            entries.add(new PieEntry((float) sp.getDoanhThu(),sp.getTenSanPham()));
        }

        PieDataSet pieDataSet = new PieDataSet(entries,"Doanh thu");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(pieDataSet);
        piechart.setData(data);
        piechart.getDescription().setEnabled(false);
        piechart.animateY(1000);
        piechart.invalidate();

    }
}