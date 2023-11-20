package com.example.da1_qldh_yuii.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_qldh_yuii.database.DbHelper;
import com.example.da1_qldh_yuii.model.BangGia;
import com.example.da1_qldh_yuii.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class BangGiaTheoSizeDAO {

    private SQLiteDatabase db;

    public BangGiaTheoSizeDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(BangGia bg){
        ContentValues values = new ContentValues();
        values.put("size",bg.getSize());
        values.put("giaBan",bg.getGia());
        return db.insert("BANGGIA",null,values);
    }

    public long update(BangGia bg){
        ContentValues values = new ContentValues();
        values.put("size",bg.getSize());
        values.put("giaBan",bg.getGia());
        return db.update("BANGGIA", values, "maBangGia = ?", new String[]{String.valueOf(bg.getMaBangGia())});
    }

    public long delete(String id){
        return db.delete("BANGGIA", "maBangGia = ?", new String[]{String.valueOf(id)});
    }

    public List<BangGia> getAll() {
        String sql = "SELECT * FROM BANGGIA ";
        return getData(sql);
    }

    public BangGia getID(String id) {
        String sql = "SELECT * FROM BANGGIA WHERE maBangGia=?";
        List<BangGia> list = getData(sql, id);
        return list.get(0);
    }

    @SuppressLint("Range")
    private List<BangGia> getData(String sql, String... selectionArgs) {
        List<BangGia> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            BangGia bg = new BangGia();
            bg.setMaBangGia(cursor.getInt(cursor.getColumnIndex("maBangGia")));
            bg.setSize(cursor.getInt(cursor.getColumnIndex("size")));
            bg.setGia(cursor.getDouble(cursor.getColumnIndex("giaBan")));
            list.add(bg);
        }
        return list;
    }


}
