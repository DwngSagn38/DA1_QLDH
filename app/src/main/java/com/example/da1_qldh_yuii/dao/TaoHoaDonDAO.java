package com.example.da1_qldh_yuii.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_qldh_yuii.database.DbHelper;
import com.example.da1_qldh_yuii.model.TaoHoaDon;
import com.example.da1_qldh_yuii.model.ThongBao;

import java.util.ArrayList;
import java.util.List;

public class TaoHoaDonDAO {

    private SQLiteDatabase db;

    public TaoHoaDonDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public List<TaoHoaDon> getAll() {
        String sql = "SELECT * FROM TaoHoaDon";
        return getData(sql);
    }

    public long insert(TaoHoaDon thd){
        ContentValues values = new ContentValues();
        values.put("maSanPham",thd.getMaSanPham());
        values.put("soLuongMua",thd.getSoLuongMua());
        return db.insert("TaoHoaDon",null,values);
    }

    public long update(TaoHoaDon thd){
        ContentValues values = new ContentValues();
        values.put("maSanPham",thd.getMaSanPham());
        values.put("soLuongMua",thd.getSoLuongMua());
        return db.update("TaoHoaDon",values, "maTHD = ? ", new String[]{String.valueOf(thd.getMaTHD())});
    }

    public long delete(String masp){
        return db.delete("TaoHoaDon", "maSanPham = ?", new String[]{String.valueOf(masp)});
    }
    public void deleteAllData() {
        db.delete("TaoHoaDon", null, null);
    }
    @SuppressLint("Range")
    private List<TaoHoaDon> getData(String sql, String... selectionArgs) {
        List<TaoHoaDon> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            TaoHoaDon thd = new TaoHoaDon();
            thd.setMaTHD(cursor.getInt(cursor.getColumnIndex("maTHD")));
            thd.setMaSanPham(cursor.getString(cursor.getColumnIndex("maSanPham")));
            thd.setSoLuongMua(cursor.getInt(cursor.getColumnIndex("soLuongMua")));
            list.add(thd);
        }
        return list;
    }
}
