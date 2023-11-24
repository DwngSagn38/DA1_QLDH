package com.example.da1_qldh_yuii;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_qldh_yuii.adapter.ThanhVienAdapter;
import com.example.da1_qldh_yuii.dao.ThanhVienDAO;
import com.example.da1_qldh_yuii.fragment.fragment_trangchu;
import com.example.da1_qldh_yuii.model.ThanhVien;

import java.util.ArrayList;

public class DangNhap extends AppCompatActivity {

    EditText edUsername,edPassword;
    CheckBox chkRemember;
    Button btnDangNhap;

    TextView tvDangKy;

    ThanhVienDAO tvDAO;

    String strUser,strPass;

    EditText edUser,edTen,edsdt,edPass,edRePass;

    Button btnLuu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        //anh xa

        edUsername = findViewById(R.id.edUsername);
        edPassword = findViewById(R.id.edPassword);
        chkRemember = findViewById(R.id.chkRemember);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        tvDangKy = findViewById(R.id.tvDangKy);
        tvDAO = new ThanhVienDAO(this);

        //
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME", "");
        String pass = pref.getString("PASSWORD", "");
        Boolean rem = pref.getBoolean("REMEMBER", false);

        edUsername.setText(user);
        edPassword.setText(pass);
        chkRemember.setChecked(rem);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

        ArrayList<ThanhVien> list = new ArrayList<>();
        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(DangNhap.this,0,list);
            }
        });

    }

    public void openDialog(Context context,int type, ArrayList<ThanhVien> list){

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = inflater.inflate(R.layout.dl_dangky,null);
        builder.setView(view); // gan view vao hop thoai
        Dialog dialog = builder.create();
        dialog.show();

        ThanhVienDAO tvDao = new ThanhVienDAO(context);

        // anh xa
        edUser = dialog.findViewById(R.id.edUser);
        edTen = dialog.findViewById(R.id.edTen);
        edsdt = dialog.findViewById(R.id.edsdt);
        edPass = dialog.findViewById(R.id.edPass);
        edRePass = dialog.findViewById(R.id.edRePass);
        btnLuu = dialog.findViewById(R.id.btnLuu);
        //
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edUser.getText().toString();
                String hoten = edTen.getText().toString();
                String sdt = edsdt.getText().toString();
                String pass = edPass.getText().toString();
                String confirm = edRePass.getText().toString();

                if (user.trim().isEmpty() || hoten.trim().isEmpty() || sdt.trim().isEmpty() || pass.trim().isEmpty() || confirm.trim().isEmpty()) {
                    Toast.makeText(context, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                } else if (tvDao.checkUser(user)) {
                    Toast.makeText(context, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                } else if (!pass.equals(confirm)) {
                    Toast.makeText(context, "Mật khẩu chưa khớp", Toast.LENGTH_SHORT).show();
                } else if (!validateSDT(sdt) || sdt.length() < 10){
                    Toast.makeText(context, "Số điện thoại chưa đúng", Toast.LENGTH_SHORT).show();
                } else {
                    ThanhVien tv = new ThanhVien(user,hoten,sdt,pass,1);
                    tvDao.insert(tv);
                    Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    if (type == 1){
                        list.clear();
                        list.addAll(tvDao.getAll());
                    }
                    dialog.dismiss();
                }
            }
        });

    }

    public void rememberUser(String u, String p, boolean status,int level) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!status) {
            // xoa trang thai luu truoc do
            edit.clear();
        } else {
            edit.putString("USERNAME", u);
            edit.putString("PASSWORD", p);
            edit.putBoolean("REMEMBER", status);
            edit.putInt("LEVEL",level);
        }
        // luu lai toan bo du lieu
        edit.commit();
    }

    public void checkLogin() {
        strUser = edUsername.getText().toString();
        strPass = edPassword.getText().toString();
        if (strUser.trim().isEmpty() || strPass.trim().isEmpty()) {
            Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
        } else {
            if (tvDAO.checkLogin(strUser, strPass) > 0) {
                Toast.makeText(getApplicationContext(), "Login thành công", Toast.LENGTH_SHORT).show();
                rememberUser(strUser, strPass, chkRemember.isChecked(),tvDAO.getID(strUser).getPhanQuyen());
                Intent intent = new Intent(getApplicationContext(), Navigation.class);
                intent.putExtra("user", strUser);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean validateSDT(String sdt){
        return sdt.matches("\\d++");
    }

// comment a
}