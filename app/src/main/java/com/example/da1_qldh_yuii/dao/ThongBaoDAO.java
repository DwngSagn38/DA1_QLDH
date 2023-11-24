package com.example.da1_qldh_yuii.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_qldh_yuii.database.DbHelper;
import com.example.da1_qldh_yuii.model.ThanhVien;
import com.example.da1_qldh_yuii.model.ThongBao;

import java.util.ArrayList;
import java.util.List;

public class ThongBaoDAO {

    private SQLiteDatabase db;

    public ThongBaoDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ThongBao tb){
        ContentValues values = new ContentValues();
        values.put("maThanhVien",tb.getMaThanhVien());
        values.put("tieuDe",tb.getTieuDe());
        values.put("noiDung",tb.getNoiDung());
        values.put("ngayDang",tb.getNgayDang());
        return db.insert("THONGBAO",null,values);
    }



    public long update(ThongBao tb){
        ContentValues values = new ContentValues();
        values.put("maThanhVien",tb.getMaThanhVien());
        values.put("tieuDe",tb.getTieuDe());
        values.put("noiDung",tb.getNoiDung());
        values.put("ngayDang",tb.getNgayDang());
        return db.update("THONGBAO", values, "maThongBao = ?", new String[]{String.valueOf(tb.getMaThongBao())});
    }

    public long delete(String id){
        return db.delete("THONGBAO", "maThongBao = ?", new String[]{String.valueOf(id)});
    }

    public List<ThongBao> getAll() {
        String sql = "SELECT * FROM THONGBAO";
        return getData(sql);
    }

    public ThongBao getID(String id) {
        String sql = "SELECT * FROM THONGBAO WHERE maThongBao=?";
        List<ThongBao> list = getData(sql, id);
        if (!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }


    @SuppressLint("Range")
    private List<ThongBao> getData(String sql, String... selectionArgs) {
        List<ThongBao> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            ThongBao tb = new ThongBao();
            tb.setMaThongBao(cursor.getInt(cursor.getColumnIndex("maThongBao")));
            tb.setMaThanhVien(cursor.getString(cursor.getColumnIndex("maThanhVien")));
            tb.setTieuDe(cursor.getString(cursor.getColumnIndex("tieuDe")));
            tb.setNoiDung(cursor.getString(cursor.getColumnIndex("noiDung")));
            tb.setNgayDang(cursor.getString(cursor.getColumnIndex("ngayDang")));
            list.add(tb);
        }
        return list;
    }
}
