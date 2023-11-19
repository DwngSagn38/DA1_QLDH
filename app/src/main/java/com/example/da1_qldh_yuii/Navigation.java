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
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Navigation extends AppCompatActivity {

    DrawerLayout drawer;
    Toolbar toolbar;

    NavigationView nv;
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



        // su kien fragment
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_giaSize){
                    setTitle("Quản lý bảng giá theo size");

                } else if (id == R.id.nav_DVVC){
                    setTitle("Quản lý đơn vị vận chuyển");

                } else if (id == R.id.nav_thongBao){
                    setTitle("Quản lý thông báo");

                } else if (id == R.id.nav_thanhVien){
                    setTitle("Quản lý bảng giá theo size");

                } else if (id == R.id.nav_giaSize){
                    setTitle("Quản lý thành viên");

                } else if(id == R.id.nav_quenMatKhau){
                    setTitle("Quên mật khẩu");

                } else if(id == R.id.nav_doiMatKhau){
                    setTitle("Đổi mật khẩu");

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