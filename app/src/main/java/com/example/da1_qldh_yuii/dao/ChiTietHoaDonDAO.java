package com.example.da1_qldh_yuii.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_qldh_yuii.database.DbHelper;
import com.example.da1_qldh_yuii.model.ChiTietHoaDon;
import com.example.da1_qldh_yuii.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonDAO {
    private SQLiteDatabase db;

    public ChiTietHoaDonDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ChiTietHoaDon obj) {
        ContentValues values = new ContentValues();
        values.put("maHoaDon", obj.getMaHoaDon());
        values.put("giamGia", obj.getGiamGia());
        return db.insert("CHITIETHOADON", null, values);
    }

    public long update(ChiTietHoaDon obj) {
        ContentValues values = new ContentValues();
        values.put("maHoaDon", obj.getMaHoaDon());
        values.put("giamGia", obj.getGiamGia());
        return db.update("CHITIETHOADON", values, "maChiTietHoaDon = ?", new String[]{String.valueOf(obj.getMaChiTietHoaDon())});
    }

    public long delete(String id) {
        return db.delete("CHITIETHOADON", "maChiTietHoaDon = ?", new String[]{String.valueOf(id)});
    }

    public List<ChiTietHoaDon> getAll() {
        String sql = "SELECT * FROM CHITIETHOADON";
        return getData(sql);
    }

    public ChiTietHoaDon getID(String id) {
        String sql = "SELECT * FROM CHITIETHOADON WHERE maChiTietHoaDon=?";
        List<ChiTietHoaDon> list = getData(sql, id);
        return list.get(0);
    }


    @SuppressLint("Range")
    private List<ChiTietHoaDon> getData(String sql, String... selectionArgs) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            ChiTietHoaDon ct = new ChiTietHoaDon();
            ct.setMaHoaDon(cursor.getString(cursor.getColumnIndex("maHoaDon")));
            ct.setMaChiTietHoaDon(cursor.getString(cursor.getColumnIndex("maChiTietHoaDon")));
            ct.setGiamGia(cursor.getDouble(cursor.getColumnIndex("giamGia")));
            list.add(ct);
        }
        return list;
    }
}
