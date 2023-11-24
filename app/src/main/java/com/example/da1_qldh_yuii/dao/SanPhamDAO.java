package com.example.da1_qldh_yuii.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.da1_qldh_yuii.database.DbHelper;
import com.example.da1_qldh_yuii.model.SanPham;
import com.example.da1_qldh_yuii.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {
    private SQLiteDatabase db;

    public SanPhamDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(SanPham sp) {
        ContentValues values = new ContentValues();
        values.put("maSanPham", sp.getMaSanPham());
        values.put("anhSanPham", sp.getAnhSanPham().toString());
        values.put("tenSanPham", sp.getTenSanPham());
        values.put("trangThai", sp.getTrangThai());
        values.put("maBangGia", sp.getMaBangGia());
        return db.insert("SANPHAM", null, values);
    }

    public long update(SanPham sp) {
        ContentValues values = new ContentValues();
        values.put("tenSanPham", sp.getTenSanPham());
        values.put("trangThai", sp.getTrangThai());
        values.put("maBangGia", sp.getMaBangGia());
        return db.update("SANPHAM", values, "maSanPham = ?", new String[]{String.valueOf(sp.getMaSanPham())});
    }

    public long delete(String id) {
        return db.delete("SANPHAM", "maSanPham = ?", new String[]{String.valueOf(id)});
    }

    public List<SanPham> getAll() {
        String sql = "SELECT * FROM SANPHAM";
        return getData(sql);
    }

    public SanPham getID(String id) {
        String sql = "SELECT * FROM SANPHAM WHERE maSanPham=?";
        List<SanPham> list = getData(sql, id);
        return list.get(0);
    }


    @SuppressLint("Range")
    private List<SanPham> getData(String sql, String... selectionArgs) {
        List<SanPham> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            SanPham sp = new SanPham();
            sp.setMaSanPham(cursor.getString(cursor.getColumnIndex("maSanPham")));
//            sp.setAnhSanPham(cursor.getInt(cursor.getColumnIndex("anhSanPham")));
            // Lấy giá trị của trường uri từ Cursor
            String uriString = cursor.getString(cursor.getColumnIndex("anhSanPham"));
            if (uriString != null) {
                Uri uri = Uri.parse(uriString);
                sp.setAnhSanPham(uri);
            }
            sp.setTenSanPham(cursor.getString(cursor.getColumnIndex("tenSanPham")));
            sp.setTrangThai(cursor.getInt(cursor.getColumnIndex("trangThai")));
            sp.setMaBangGia(cursor.getInt(cursor.getColumnIndex("maBangGia")));
            list.add(sp);
        }
        return list;
    }
}
