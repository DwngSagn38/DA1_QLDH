package com.example.da1_qldh_yuii.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_qldh_yuii.database.DbHelper;
import com.example.da1_qldh_yuii.model.HoaDon;
import com.example.da1_qldh_yuii.model.KhachHang;

import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    private SQLiteDatabase db;


    public KhachHangDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(KhachHang obj) {
        ContentValues values = new ContentValues();
        values.put("tenKhachHang", obj.getTenKhachHang());
        values.put("soDienThoai", obj.getSoDienThoai());
        values.put("diaChi", obj.getDiaChi());
        return db.insert("KHACHHANG", null, values);
    }

    public long update(KhachHang obj) {
        ContentValues values = new ContentValues();
        values.put("tenKhachHang", obj.getTenKhachHang());
        values.put("soDienThoai", obj.getSoDienThoai());
        values.put("diaChi", obj.getDiaChi());
        return db.update("KHACHHANG", values, "maKhachHang = ?", new String[]{String.valueOf(obj.getMaKhachHang())});
    }

    public long delete(String id) {
        return db.delete("KHACHHANG", "maKhachHang = ?", new String[]{String.valueOf(id)});
    }

    public List<KhachHang> getAll() {
        String sql = "SELECT * FROM KHACHHANG";
        return getData(sql);
    }

    public KhachHang getID(String id) {
        String sql = "SELECT * FROM KHACHHANG WHERE maKhachHang=?";
        List<KhachHang> list = getData(sql, id);
        return list.get(0);
    }


    @SuppressLint("Range")
    private List<KhachHang> getData(String sql, String... selectionArgs) {
        List<KhachHang> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            KhachHang kh = new KhachHang();
            kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex("maKhachHang")));
            kh.setTenKhachHang(cursor.getString(cursor.getColumnIndex("tenKhachHang")));
            kh.setSoDienThoai(cursor.getString(cursor.getColumnIndex("soDienThoai")));
            kh.setDiaChi(cursor.getString(cursor.getColumnIndex("diaChi")));

            list.add(kh);
        }
        return list;
    }
}
