package com.example.da1_qldh_yuii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_qldh_yuii.dao.HoaDonDAO;
import com.example.da1_qldh_yuii.dao.SanPhamDAO;
import com.example.da1_qldh_yuii.dao.ThanhVienDAO;
import com.example.da1_qldh_yuii.fragment.fragment_banggiatheosize;
import com.example.da1_qldh_yuii.fragment.fragment_doimatkhau;
import com.example.da1_qldh_yuii.fragment.fragment_donvivanchuyen;
import com.example.da1_qldh_yuii.fragment.fragment_hoadon;
import com.example.da1_qldh_yuii.fragment.fragment_khohang;
import com.example.da1_qldh_yuii.fragment.fragment_quenmatkhau;
import com.example.da1_qldh_yuii.fragment.fragment_sanpham_khohang;
import com.example.da1_qldh_yuii.fragment.fragment_thanhvien;
import com.example.da1_qldh_yuii.fragment.fragment_thongbao;
import com.example.da1_qldh_yuii.fragment.fragment_trangchu;
import com.example.da1_qldh_yuii.fragment.fragment_trangchu_thongke;
import com.example.da1_qldh_yuii.model.HoaDon;
import com.example.da1_qldh_yuii.model.SanPham;
import com.example.da1_qldh_yuii.model.ThanhVien;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Navigation extends AppCompatActivity {

    //
    DrawerLayout drawer;
    Toolbar toolbar;

    View mHeaderView;
    TextView tvUser, tvLevel;
    ImageView img_logo;
    NavigationView nv;

    ThanhVienDAO tvDAO;

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // ánh xạ
        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        nv = findViewById(R.id.nvView);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        // set toolbar thay actionbar
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        // set mau icon ve ban goc
        nv.setItemIconTintList(null);

        fragment_trangchu fragment_trangchu = new fragment_trangchu();
        replaceFrg(fragment_trangchu);


        // show user trên header
        mHeaderView = nv.getHeaderView(0);
        tvUser = mHeaderView.findViewById(R.id.tvUser);
        tvLevel = mHeaderView.findViewById(R.id.tvLevel);
        img_logo = mHeaderView.findViewById(R.id.img_logo);
        Intent i = getIntent();
        String user = i.getStringExtra("user");
        tvDAO = new ThanhVienDAO(this);
        ThanhVien thanhVien = tvDAO.getID(user);
        String username = thanhVien.getTenThanhVien();
        int level = thanhVien.getPhanQuyen();
        tvUser.setText(username);
        if (level == 0) {
            actionBar.setHomeAsUpIndicator(R.drawable.logo_menu);
            tvLevel.setText("(Quản lý)");
            img_logo.setImageResource(R.drawable.logo_yuii);
        } else {
            actionBar.setHomeAsUpIndicator(R.drawable.logo_menu2);
            tvLevel.setText("(Nhân Viên)");
            img_logo.setImageResource(R.drawable.ic_person);
            nv.setItemIconTintList(null);
        }

        checkStatus(Navigation.this);

        // quanly co quyen ql thong bao, thanh vien
        if (level == 0) {
            nv.getMenu().findItem(R.id.nav_thongBao).setVisible(true);
            nv.getMenu().findItem(R.id.nav_thanhVien).setVisible(true);
        }

        // su kien bottomnavigation

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.bottom_trangChu) {
                setTitle("Yuii shop");
                replaceFrg(fragment_trangchu);
            } else if (id == R.id.bottom_thongKe) {
                setTitle("Thống kê");
                fragment_trangchu_thongke fragment_trangchu_thongke = new fragment_trangchu_thongke();
                replaceFrg(fragment_trangchu_thongke);
            } else if (id == R.id.bottom_khoHang) {
                setTitle("Kho hàng");
                fragment_sanpham_khohang fragment_sanpham_khohang = new fragment_sanpham_khohang();
                replaceFrg(fragment_sanpham_khohang);
            } else if (id == R.id.bottom_hoaDon) {
                setTitle("Hóa đơn");
                fragment_hoadon fragment_hoadon = new fragment_hoadon();
                replaceFrg(fragment_hoadon);
            }
            return true;
        });

        // su kien navigation
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_giaSize) {
                    setTitle("Quản lý bảng giá theo size");
                    fragment_banggiatheosize fragmentBanggiatheosize = new fragment_banggiatheosize();
                    replaceFrg(fragmentBanggiatheosize);

                } else if (id == R.id.nav_DVVC) {
                    setTitle("Quản lý đơn vị vận chuyển");
                    fragment_donvivanchuyen fragmentDonvivanchuyen = new fragment_donvivanchuyen();
                    replaceFrg(fragmentDonvivanchuyen);

                } else if (id == R.id.nav_thongBao) {
                    setTitle("Quản lý thông báo");
                    fragment_thongbao fragmentThongbao = new fragment_thongbao();
                    replaceFrg(fragmentThongbao);

                } else if (id == R.id.nav_thanhVien) {
                    setTitle("Quản lý thành viên");
                    fragment_thanhvien fragment_thanhvien = new fragment_thanhvien();
                    replaceFrg(fragment_thanhvien);

                } else if (id == R.id.nav_quenMatKhau) {
                    setTitle("Quên mật khẩu");
                    fragment_quenmatkhau fragment_quenmatkhau = new fragment_quenmatkhau();
                    replaceFrg(fragment_quenmatkhau);
                    Toast.makeText(Navigation.this, "Chức năng đang bảo trì", Toast.LENGTH_SHORT).show();

                } else if (id == R.id.nav_doiMatKhau) {
                    setTitle("Đổi mật khẩu");
                    fragment_doimatkhau fragment_doimatkhau = new fragment_doimatkhau();
                    replaceFrg(fragment_doimatkhau);

                } else if (id == R.id.nav_dangXuat) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Navigation.this);
                    builder.setTitle("Xác nhận");
                    builder.setMessage("Bạn có chắc chắn muốn đăng xuất không?");
                    builder.setCancelable(true);

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            Intent intent = new Intent(Navigation.this, DangNhap.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            Toast.makeText(Navigation.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.cancel();
                            Toast.makeText(Navigation.this, "Không đăng xuất", Toast.LENGTH_SHORT).show();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
                drawer.closeDrawers();
                return true;
            }
        });
    }

    public void replaceFrg(Fragment frg) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.flContent, frg).addToBackStack(null).commit();
        checkStatus(Navigation.this);
    }

    public void checkStatus(Context context){
        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        int Min = calendar.get(Calendar.MINUTE);
        int Hours = calendar.get(Calendar.HOUR_OF_DAY);
        int gioHienTai = Min;
        HoaDonDAO hdDAO = new HoaDonDAO(context);

        ArrayList<HoaDon> list = new ArrayList<>();
        list.addAll(hdDAO.getAll());

        if (list.size() == 0){
            return;
        }

        for (HoaDon hd : list){
            if (hd.getTrangThai() == 2 && hd.getGio() + 2 < gioHienTai){
                    hd.setTrangThai(1);
                    hd.setNgayGiaoOk( new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()) + " -- "+Hours+":"+Min);
                    hdDAO.update(hd);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MyApplication.CHANNEL_ID);

                    builder.setSmallIcon(R.drawable.ic_launcher_background);
                    builder.setContentTitle("Cập nhật trạng thái!!!");
                    builder.setContentText("Mã hóa đơn "+hd.getMaHoaDon()+ " đã được giao");
                    builder.setSmallIcon(R.drawable.logo);

                    Bitmap logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp_new2);

                    builder.setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(logo).bigLargeIcon(null)
                    );
                    NotificationManagerCompat com = NotificationManagerCompat.from(context);

                    if(ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS)
                            == PackageManager.PERMISSION_GRANTED
                    ){
                        com.notify((int) new Date().getTime(), builder.build());
                    }else{
                        ActivityCompat.requestPermissions(((Activity) context),
                                new String[]{Manifest.permission.POST_NOTIFICATIONS},
                                999);
                    }
            }
        }
    }

}