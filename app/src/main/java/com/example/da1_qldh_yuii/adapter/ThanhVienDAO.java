package com.example.da1_qldh_yuii.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_qldh_yuii.database.DbHelper;

public class ThanhVienDAO {
    DbHelper dbHelper;
    public ThanhVienDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    //đăng nhập
    public boolean checkDangNhap(String tenThanhVien, String matKhau){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THANHVIEN WHERE tenThanhVien = ? AND matKhau = ?", new String[]{tenThanhVien, matKhau});
        if (cursor.getCount() != 0){

            return true;
        }else {
            return false;
        }
    }


}
