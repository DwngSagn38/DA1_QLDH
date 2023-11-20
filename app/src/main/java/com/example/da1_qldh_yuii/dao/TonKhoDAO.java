package com.example.da1_qldh_yuii.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_qldh_yuii.database.DbHelper;
import com.example.da1_qldh_yuii.model.ThanhVien;
import com.example.da1_qldh_yuii.model.TonKho;
import com.example.da1_qldh_yuii.model.VanChuyen;

import java.util.ArrayList;
import java.util.List;

public class TonKhoDAO {
    private SQLiteDatabase db;

    //
    public TonKhoDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(TonKho tk) {
        ContentValues values = new ContentValues();
        values.put("maSanPham", tk.getMaSanPham());
        values.put("maHoaDon", tk.getMaHoaDon());
        values.put("soLuong", tk.getSoLuong());
        return db.insert("TONKHO", null, values);
    }

    public long update(TonKho tk) {
        ContentValues values = new ContentValues();
        values.put("maSanPham", tk.getMaSanPham());
        values.put("maHoaDon", tk.getMaHoaDon());
        values.put("soLuong", tk.getSoLuong());
        return db.update("TONKHO", values, "maTonKho = ?", new String[]{String.valueOf(tk.getMaTonKho())});
    }

    public long delete(String id) {
        return db.delete("TONKHO", "maTonKho = ?", new String[]{String.valueOf(id)});
    }

    public List<TonKho> getAll() {
        String sql = "SELECT * FROM TONKHO";
        return getData(sql);
    }

    public TonKho getID(String id) {
        String sql = "SELECT * FROM TONKHO WHERE maTonKho=?";
        List<TonKho> list = getData(sql, id);
        return list.get(0);
    }


    @SuppressLint("Range")
    private List<TonKho> getData(String sql, String... selectionArgs) {
        List<TonKho> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            TonKho tk = new TonKho();
            tk.setMaTonKho(cursor.getString(cursor.getColumnIndex("maTonKho")));
            tk.setMaSanPham(cursor.getString(cursor.getColumnIndex("maSanPham")));
            tk.setMaHoaDon(cursor.getString(cursor.getColumnIndex("maHoaDon")));
            tk.setSoLuong(cursor.getInt(cursor.getColumnIndex("soLuong")));
            list.add(tk);
        }
        return list;
    }
}
