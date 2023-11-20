package com.example.da1_qldh_yuii.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.da1_qldh_yuii.database.DbHelper;
import com.example.da1_qldh_yuii.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    private SQLiteDatabase db;

    public ThanhVienDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ThanhVien tv){
        ContentValues values = new ContentValues();
        values.put("maThanhVien",tv.getMaThanhVien());
        values.put("tenThanhVien",tv.getTenThanhVien());
        values.put("soDienThoai",tv.getSoDienThoai());
        values.put("matKhau",tv.getMatKhau());
        values.put("phanQuyen",tv.getPhanQuyen());
        return db.insert("THANHVIEN",null,values);
    }

    public long update(ThanhVien tv){
        ContentValues values = new ContentValues();
        values.put("maThanhVien",tv.getMaThanhVien());
        values.put("tenThanhVien",tv.getTenThanhVien());
        values.put("soDienThoai",tv.getSoDienThoai());
        values.put("matKhau",tv.getMatKhau());
        values.put("phanQuyen",tv.getPhanQuyen());
        return db.update("THANHVIEN", values, "maThanhVien = ?", new String[]{String.valueOf(tv.getMaThanhVien())});
    }

    public long delete(String id){
        return db.delete("THANHVIEN", "maThanhVien = ?", new String[]{String.valueOf(id)});
    }

    public List<ThanhVien> getAll() {
        String sql = "SELECT * FROM THANHVIEN";
        return getData(sql);
    }

    public ThanhVien getID(String id) {
        String sql = "SELECT * FROM THANHVIEN WHERE maThanhVien=?";
        List<ThanhVien> list = getData(sql, id);
        return list.get(0);
    }

    public int checkLogin(String id, String password) {
        String sql = "SELECT * FROM THANHVIEN WHERE maThanhVien=? AND matKhau=?";
        List<ThanhVien> list = getData(sql, id, password);
        if (list.size() == 0) {
            return -1;
        }
        return 1;
    }

    public boolean checkUser(String username) {
        boolean usernameExists = false;
        Cursor cursor = db.rawQuery("SELECT * FROM THANHVIEN WHERE maThanhVien = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            usernameExists = true;
        }
        return usernameExists;
    }

    @SuppressLint("Range")
    private List<ThanhVien> getData(String sql, String... selectionArgs) {
        List<ThanhVien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            ThanhVien tv = new ThanhVien();
            tv.setMaThanhVien(cursor.getString(cursor.getColumnIndex("maThanhVien")));
            tv.setTenThanhVien(cursor.getString(cursor.getColumnIndex("tenThanhVien")));
            tv.setSoDienThoai(cursor.getString(cursor.getColumnIndex("soDienThoai")));
            tv.setMatKhau(cursor.getString(cursor.getColumnIndex("matKhau")));
            tv.setPhanQuyen(cursor.getInt(cursor.getColumnIndex("phanQuyen")));
            list.add(tv);
        }
        return list;
    }
}
