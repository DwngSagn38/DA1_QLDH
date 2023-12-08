package com.example.da1_qldh_yuii.fragment.frgTHD_CSP;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.adapter.TaoHoaDonAdapter;
import com.example.da1_qldh_yuii.dao.BangGiaTheoSizeDAO;
import com.example.da1_qldh_yuii.dao.ChiTietHoaDonDAO;
import com.example.da1_qldh_yuii.dao.HoaDonDAO;
import com.example.da1_qldh_yuii.dao.KhachHangDAO;
import com.example.da1_qldh_yuii.dao.SanPhamDAO;
import com.example.da1_qldh_yuii.dao.TaoHoaDonDAO;
import com.example.da1_qldh_yuii.dao.VanChuyenDAO;
import com.example.da1_qldh_yuii.model.BangGia;
import com.example.da1_qldh_yuii.model.ChiTietHoaDon;
import com.example.da1_qldh_yuii.model.HoaDon;
import com.example.da1_qldh_yuii.model.KhachHang;
import com.example.da1_qldh_yuii.model.SanPham;
import com.example.da1_qldh_yuii.model.TaoHoaDon;
import com.example.da1_qldh_yuii.model.VanChuyen;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class fragment_taohoadon extends Fragment {
    public fragment_taohoadon() {
        // Required empty public constructor
    }
    private int initialYear;
    private int initialMonth;
    private int initialDay;
    private int initialMin;
    private int initialhours;

    ImageView btnTaoHDon;
    RecyclerView rcvGioHang;
    TextView txtTongtien,tvMahd;

    Button btnThemsp;
    TaoHoaDonAdapter adapter;
    HoaDonDAO hdDAO;
    ArrayList<TaoHoaDon> list = new ArrayList<>();

    TaoHoaDonDAO thdDAO;
    SanPhamDAO spDAO;
    BangGiaTheoSizeDAO bgDAO;
    VanChuyenDAO vcDAO;
    KhachHangDAO khDAO;

    ChiTietHoaDonDAO ctDAO;




    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_taohoadon2, container, false);
        getViewID(view);
        dao();

        String mahd = getRandomString();
        tvMahd.setText("Mã hóa đơn : "+mahd);
        list.addAll(thdDAO.getAll());
        adapter = new TaoHoaDonAdapter(getContext(),list);
        rcvGioHang.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvGioHang.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        txtTongtien.setText("Tổng tiền: " +updateTongTien(getContext(),list) + " VNĐ");
        btnTaoHDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HoaDon hoaDon = new HoaDon();
                ChiTietHoaDon ctHD = new ChiTietHoaDon();
                opendialog(hoaDon,getContext(),list,mahd);
            }
        });

        btnThemsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment chonspfrg = new fragment_chonsanpham();
                FragmentTransaction frg = getActivity().getSupportFragmentManager().beginTransaction();
                frg.replace(R.id.flContent, chonspfrg).commit();
            }
        });



        return view;
    }
    public void getViewID(View view){
        txtTongtien = view.findViewById(R.id.txtTongtien);
        tvMahd = view.findViewById(R.id.tvMahd);
        rcvGioHang = view.findViewById(R.id.rcvGioHang);
        btnTaoHDon = view.findViewById(R.id.btnTaoHDon);
        btnThemsp = view.findViewById(R.id.btnThemsp);
    }
    public void dao(){
        hdDAO = new HoaDonDAO(getContext());
        ctDAO = new ChiTietHoaDonDAO(getContext());
        thdDAO = new TaoHoaDonDAO(getContext());
        vcDAO = new VanChuyenDAO(getContext());
        khDAO = new KhachHangDAO(getContext());
    }

    public Double updateTongTien(Context context,ArrayList<TaoHoaDon> list) {
        SanPhamDAO spDAO = new SanPhamDAO(context);
        BangGiaTheoSizeDAO bgDAO = new BangGiaTheoSizeDAO(context);
        double tongtien = 0.0;
        for (TaoHoaDon hd : list) {
            SanPham sanPham = spDAO.getID(hd.getMaSanPham());
            BangGia bangGia = bgDAO.getID(sanPham.getMaBangGia());

            double tien = hd.getSoLuongMua() * bangGia.getGiaBan();
            tongtien += tien;
        }
        return tongtien;
    }

    public String getRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int length = 6;
        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public void opendialog (HoaDon hd, Context context, ArrayList<TaoHoaDon> list,String ma){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_themhoadon,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        Spinner spmakh = view.findViewById(R.id.spmakh);
        TextView tvNgayNhan = view.findViewById(R.id.tvNgayNhan);
        Spinner spmadvvc = view.findViewById(R.id.spmadvvc);
        Spinner spTienCoc = view.findViewById(R.id.spTienCoc);
        TextView tvTongTienHD = view.findViewById(R.id.tvTongTienHD);
        EditText edMoTaHD =view.findViewById(R.id.edMoTaHD);
        Button btnLuuAddHD = view.findViewById(R.id.btnLuuAddHD);
        ImageView datePickerImageView = view.findViewById(R.id.datePickerImageView);

        tvTongTienHD.setTextColor(Color.RED);
        tvTongTienHD.setText("Tổng tiền: " +updateTongTien(getContext(),list) + " VNĐ");

        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        initialYear = calendar.get(Calendar.YEAR);
        initialMonth = calendar.get(Calendar.MONTH);
        initialDay = calendar.get(Calendar.DAY_OF_MONTH);
        initialMin = calendar.get(Calendar.MINUTE);
        initialhours = calendar.get(Calendar.HOUR_OF_DAY);
        datePickerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tvNgayNhan);
            }
        });

        ArrayAdapter<String> ArrTenKH = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        ArrTenKH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayList<KhachHang> listKH = new ArrayList<>();
        listKH.addAll(khDAO.getDSKhachHang());

        for (KhachHang kh : listKH) {
            ArrTenKH.add(kh.getTenKhachHang());
        }

        spmakh.setAdapter(ArrTenKH);

        spmakh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedOption = (String) adapterView.getItemAtPosition(i);
                // Thực hiện các thao tác tùy thuộc vào tùy chọn được chọn
                for (KhachHang kh : listKH) {
                    if (kh.getTenKhachHang().equals(selectedOption)) {
                        hd.setMaKhachHang(kh.getMaKhachHang());
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ArrayAdapter<Double> ArrTienCoc = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        ArrTienCoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrTienCoc.add(0.0);
        ArrTienCoc.add(10.0);
        ArrTienCoc.add(20.0);
        ArrTienCoc.add(30.0);
        ArrTienCoc.add(40.0);
        ArrTienCoc.add(50.0);
        ArrTienCoc.add(60.0);
        ArrTienCoc.add(70.0);
        ArrTienCoc.add(80.0);
        ArrTienCoc.add(90.0);
        ArrTienCoc.add(100.0);

        spTienCoc.setAdapter(ArrTienCoc);
        double tongtien = updateTongTien(getContext(), list);
        spTienCoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Double selectedOption = (Double) adapterView.getItemAtPosition(i);
                        hd.setTienCoc(selectedOption);
                        VanChuyen vc = vcDAO.getID(hd.getMaVanChuyen());
                        double tongtien = updateTongTien(getContext(), list) + vc.getGiaVanChuyen() - (updateTongTien(getContext(), list) * (selectedOption * 0.01));
                        tvTongTienHD.setText("Tổng tiền: " +tongtien + " VNĐ");
                }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        ArrayAdapter<Double> ArrVC = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        ArrVC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayList<VanChuyen> listVC = new ArrayList<>();
        listVC.addAll(vcDAO.getAll());

        for (VanChuyen vc : listVC) {
            if (vc.getTrangThai() != 1){
                ArrVC.add(vc.getGiaVanChuyen());
            }
        }

        spmadvvc.setAdapter(ArrVC);

        spmadvvc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            final double[] tien = {};
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Double selectedOption = (Double) adapterView.getItemAtPosition(i);
                // Thực hiện các thao tác tùy thuộc vào tùy chọn được chọn
                for (VanChuyen vc : listVC) {
                    if (vc.getGiaVanChuyen() == selectedOption) {
                        hd.setMaVanChuyen(vc.getMaVanChuyen());
                        tvTongTienHD.setText("Tổng tiền: " +(updateTongTien(getContext(), list) + vc.getGiaVanChuyen() - (updateTongTien(getContext(), list)) * hd.getTienCoc() * 0.01  + " VNĐ"));
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btnLuuAddHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ghichu = edMoTaHD.getText().toString();
                if (tvNgayNhan.getText().toString().isEmpty() ){
                    Toast.makeText(context, "Bạn chưa thêm ngày nhận hàng", Toast.LENGTH_SHORT).show();
                }else {
                    SharedPreferences pref = getContext().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                    String matv = pref.getString("MATV","");
                    hd.setMaHoaDon(ma);
                    hd.setMaThanhVien(matv);
                    hd.setNgayTao( new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date())+" -- "+initialhours+":"+initialMin);
//                    hd.setGio(initialMin);
                    hd.setNgayNhanHang(String.valueOf(tvNgayNhan.getText()));
                    hd.setGhiChu(ghichu);
                    hd.setTrangThai(0);
                    hd.setSoLuong(list.size());
                    hd.setNgayGiaoHang("");
                    hd.setNgayGiaoOk("");

                    if (hdDAO.insert(hd) != -1){

                        Toast.makeText(context, "Thêm thành công hóa đơn : "+ma, Toast.LENGTH_SHORT).show();
                        thdDAO.deleteAllData();
                        dialog.dismiss();
                        Fragment chonspfrg = new fragment_chonsanpham();
                        FragmentTransaction frg = getActivity().getSupportFragmentManager().beginTransaction();
                        frg.replace(R.id.flContent, chonspfrg).commit();

                        ctDAO.insertChiTiet(ma,list);
                    }else{
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

    }
    private void showDatePickerDialog(TextView tv) {
        // Hiển thị DatePickerDialog để chọn ngày
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Xử lý ngày đã chọn
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate selectedDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth); // Lưu ý: Tháng trong DatePickerDialog được đếm từ 0, nên cần +1
                String currentDateStr = selectedDate.format(formatter);
                tv.setText(currentDateStr);
            }
        }, initialYear, initialMonth, initialDay);

        datePickerDialog.show();
    }
    public boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}