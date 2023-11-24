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


    public boolean themKhachHang(KhachHang kh){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maKhachHang", kh.getMaKhachHang());
        contentValues.put("tenKhachHang", kh.getTenKhachHang());
        contentValues.put("soDienThoai", kh.getSoDienThoai());
        contentValues.put("diaChi", kh.getDiaChi());

        long check = sqLiteDatabase.insert("KHACHHANG", null, contentValues);
        if (check == -1)
            return false;

        return true;

    }

    public boolean suaKhachHang(KhachHang kh){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maKhachHang", kh.getMaKhachHang());
        contentValues.put("tenKhachHang", kh.getTenKhachHang());
        contentValues.put("soDienThoai", kh.getSoDienThoai());
        contentValues.put("diaChi", kh.getDiaChi());

        long check = sqLiteDatabase.update("KHACHHANG", contentValues,"maKhachHang=?",
                new String[]{String.valueOf(kh.getMaKhachHang())});
        return check != -1;
    }
    public boolean checkID(String username) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        boolean IDExists = false;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM KHACHHANG WHERE maKhachHang = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            IDExists = true;
        }
        return IDExists;
    }

    public boolean xoaKhachHang(String maKhachHang){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("KHACHHANG","maKhachHang=?",
                new String[]{maKhachHang});
        if (check == -1)
            return false;
        return true;
    }
    public boolean checkMaKH(String id) {
        boolean usernameExists = false;
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM KHACHHANG WHERE maKhachHang = ?", new String[]{id});
        if (cursor.moveToFirst()) {
            usernameExists = true;
        }
        return usernameExists;
    }

}
