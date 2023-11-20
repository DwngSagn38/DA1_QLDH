package com.example.da1_qldh_yuii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_qldh_yuii.dao.ThanhVienDAO;
import com.example.da1_qldh_yuii.fragment.fragment_banggiatheosize;
import com.example.da1_qldh_yuii.fragment.fragment_doimatkhau;
import com.example.da1_qldh_yuii.fragment.fragment_donvivanchuyen;
import com.example.da1_qldh_yuii.fragment.fragment_quenmatkhau;
import com.example.da1_qldh_yuii.fragment.fragment_thanhvien;
import com.example.da1_qldh_yuii.fragment.fragment_thongbao;
import com.example.da1_qldh_yuii.fragment.fragment_trangchu;
import com.example.da1_qldh_yuii.model.ThanhVien;
import com.google.android.material.navigation.NavigationView;

public class Navigation extends AppCompatActivity {

    //
    DrawerLayout drawer;
    Toolbar toolbar;

    View mHeaderView;
    TextView tvUser;
    NavigationView nv;

    ThanhVienDAO tvDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // ánh xạ
        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar1);
        nv = findViewById(R.id.nvView);
        // set toolbar thay actionbar
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.logo_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        // set mau icon ve ban goc
        nv.setItemIconTintList(null);

        fragment_trangchu fragment_trangchu = new fragment_trangchu();
        replaceFrg(fragment_trangchu);

        // show user trên header
        mHeaderView = nv.getHeaderView(0);
        tvUser = mHeaderView.findViewById(R.id.tvUser);
        Intent i = getIntent();
        String user = i.getStringExtra("user");
        tvDAO = new ThanhVienDAO(this);
        ThanhVien thanhVien = tvDAO.getID(user);
        String username = thanhVien.getTenThanhVien();
        tvUser.setText(username);

        // admin co quyen ql bang gia, dvvc
        if (user.equalsIgnoreCase("Admin")) {
            nv.getMenu().findItem(R.id.nav_thanhVien).setVisible(true);
        }


        // su kien fragment
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_giaSize){
                    setTitle("Quản lý bảng giá theo size");
                    fragment_banggiatheosize fragmentBanggiatheosize = new fragment_banggiatheosize();
                    replaceFrg(fragmentBanggiatheosize);

                } else if (id == R.id.nav_DVVC){
                    setTitle("Quản lý đơn vị vận chuyển");
                    fragment_donvivanchuyen fragmentDonvivanchuyen = new fragment_donvivanchuyen();
                    replaceFrg(fragmentDonvivanchuyen);

                } else if (id == R.id.nav_thongBao){
                    setTitle("Quản lý thông báo");
                    fragment_thongbao fragmentThongbao = new fragment_thongbao();
                    replaceFrg(fragmentThongbao);

                } else if (id == R.id.nav_thanhVien){
                    setTitle("Quản lý thành viên");
                    fragment_thanhvien fragment_thanhvien = new fragment_thanhvien();
                    replaceFrg(fragment_thanhvien);

                } else if(id == R.id.nav_quenMatKhau){
                    setTitle("Quên mật khẩu");
                    fragment_quenmatkhau fragment_quenmatkhau = new fragment_quenmatkhau();
                    replaceFrg(fragment_quenmatkhau);

                } else if(id == R.id.nav_doiMatKhau){
                    setTitle("Đổi mật khẩu");
                    fragment_doimatkhau fragment_doimatkhau = new fragment_doimatkhau();
                    replaceFrg(fragment_doimatkhau);

                }else if (id == R.id.nav_dangXuat){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Navigation.this);
                    builder.setTitle("Xác nhận");
                    builder.setMessage("Bạn có chắc chắn muốn đăng xuất không?");
                    builder.setCancelable(true);

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            Intent intent = new Intent(Navigation.this, DangNhap.class);
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

    public void replaceFrg(Fragment frg){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.flContent,frg).commit();
    }
}