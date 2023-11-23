package com.example.da1_qldh_yuii.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_qldh_yuii.database.DbHelper;
import com.example.da1_qldh_yuii.model.BangGia;
import com.example.da1_qldh_yuii.model.HoaDon;
import com.example.da1_qldh_yuii.model.KhachHang;

import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    DbHelper dbHelper;
    public KhachHangDAO(Context context){
        dbHelper = new DbHelper(context);

    }

    public ArrayList<KhachHang> getDSKhachHang(){
        ArrayList<KhachHang> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM KHACHHANG", null);

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                list.add(new KhachHang(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }while (cursor.moveToNext());

        }

        return list;
    }


    public boolean themKhachHang(String maKhachHang, String tenKhachHang, String soDienThoai, String diaChi){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maKhachHang", maKhachHang);
        contentValues.put("tenKhachHang", tenKhachHang);
        contentValues.put("soDienThoai", soDienThoai);
        contentValues.put("diaChi", diaChi);

        long check = sqLiteDatabase.insert("KHACHHANG", null, contentValues);
        if (check == -1)
            return false;

        return true;

    }

    //update

    public boolean capNhatKhachHang(String maKhachHang, String tenKhachHang, String soDienThoai, String diaChi){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maKhachHang", maKhachHang);
        contentValues.put("tenKhachHang", tenKhachHang);
        contentValues.put("soDienThoai", soDienThoai);
        contentValues.put("diaChi", diaChi);

        long check = sqLiteDatabase.update("KHACHHANG", contentValues, "maKhachHang = ?", new String[]{String.valueOf(maKhachHang)});
        if (check == -1)
            return false;
        return true;

    }


    //xóa

    public int xoaKhachHang(String maKhachHang) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int check = sqLiteDatabase.delete("KHACHHANG", "maKhachHang = ?", new String[]{maKhachHang});
        sqLiteDatabase.close();

        if (check == 0) {
            return 0; // Xóa không thành công
        } else {
            return 1; // Xóa thành công
        }
    }

}
