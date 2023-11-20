package com.example.da1_qldh_yuii.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_qldh_yuii.database.DbHelper;
import com.example.da1_qldh_yuii.model.SanPham;
import com.example.da1_qldh_yuii.model.VanChuyen;

import java.util.ArrayList;
import java.util.List;

public class VanChuyenDAO {
    private SQLiteDatabase db;

    public VanChuyenDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(VanChuyen vc) {
        ContentValues values = new ContentValues();
        values.put("tenVanChuyen", vc.getTenVanChuyen());
        values.put("giaVanChuyen", vc.getGiaVanChuyen());
        values.put("moTa", vc.getMoTa());
        values.put("trangThai", vc.getTrangThai());
        return db.insert("VANCHUYEN", null, values);
    }

    public long update(VanChuyen vc) {
        ContentValues values = new ContentValues();
        values.put("tenVanChuyen", vc.getTenVanChuyen());
        values.put("giaVanChuyen", vc.getGiaVanChuyen());
        values.put("moTa", vc.getMoTa());
        values.put("trangThai", vc.getTrangThai());
        return db.update("VANCHUYEN", values, "maVanChuyen = ?", new String[]{String.valueOf(vc.getMaVanChuyen())});
    }

    public long delete(String id) {
        return db.delete("VANCHUYEN", "maVanChuyen = ?", new String[]{String.valueOf(id)});
    }

    public List<VanChuyen> getAll() {
        String sql = "SELECT * FROM VANCHUYEN";
        return getData(sql);
    }

    public VanChuyen getID(String id) {
        String sql = "SELECT * FROM VANCHUYEN WHERE maVanChuyen=?";
        List<VanChuyen> list = getData(sql, id);
        return list.get(0);
    }


    @SuppressLint("Range")
    private List<VanChuyen> getData(String sql, String... selectionArgs) {
        List<VanChuyen> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            VanChuyen vc = new VanChuyen();
            vc.setMaVanChuyen(cursor.getString(cursor.getColumnIndex("maVanChuyen")));
            vc.setTenVanChuyen(cursor.getString(cursor.getColumnIndex("tenVanChuyen")));
            vc.setGiaVanChuyen(cursor.getDouble(cursor.getColumnIndex("giaVanChuyen")));
            vc.setMoTa(cursor.getString(cursor.getColumnIndex("moTa")));
            vc.setTrangThai(cursor.getInt(cursor.getColumnIndex("trangThai")));
            list.add(vc);
        }
        return list;
    }
}
