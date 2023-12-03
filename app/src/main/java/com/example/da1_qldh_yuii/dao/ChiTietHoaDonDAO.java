package com.example.da1_qldh_yuii.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.da1_qldh_yuii.database.DbHelper;
import com.example.da1_qldh_yuii.model.ChiTietHoaDon;
import com.example.da1_qldh_yuii.model.SanPham;
import com.example.da1_qldh_yuii.model.TaoHoaDon;

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
        values.put("maSanPham", obj.getMaSanPham());
        values.put("soLuong", obj.getSoLuong());
        return db.insert("CHITIETHOADON", null, values);
    }

    public void insertChiTietHoaDon(String maHoaDon, List<String> danhSachMaSanPham) {

        ContentValues values = new ContentValues();
        values.put("maHoaDon", maHoaDon);
        values.put("soLuong", danhSachMaSanPham.size());

        for (String maSanPham : danhSachMaSanPham) {
            values.put("maSanPham", maSanPham);
            long rowId = db.insert("CHITIETHOADON", null, values);
            if (rowId == -1) {
                // Xử lý lỗi khi chèn dữ liệu
            }
        }
    }

    public boolean insertChiTiet(String maHoaDon, List<TaoHoaDon> danhSachChiTiet) {

        for (TaoHoaDon chiTiet : danhSachChiTiet) {
            ContentValues values = new ContentValues();
            values.put("maHoaDon", maHoaDon);
            values.put("maSanPham", chiTiet.getMaSanPham());
            values.put("soLuong", chiTiet.getSoLuongMua());

            long rowId = db.insert("CHITIETHOADON", null, values);
            if (rowId == -1) {
                return false;
            }
        }
        return true;
    }


    public long update(ChiTietHoaDon obj) {
        ContentValues values = new ContentValues();
        values.put("maHoaDon", obj.getMaHoaDon());
        values.put("maSanPham", obj.getMaSanPham());
        values.put("soLuong", obj.getSoLuong());
//        values.put("giamGia", obj.getGiamGia());
        return db.update("CHITIETHOADON", values, "maHoaDon = ?", new String[]{String.valueOf(obj.getMaChiTietHoaDon())});
    }

    public long delete(String id) {
        return db.delete("CHITIETHOADON", "maHoaDon = ?", new String[]{String.valueOf(id)});
    }

    public List<ChiTietHoaDon> getAll() {
        String sql = "SELECT * FROM CHITIETHOADON";
        return getData(sql);
    }

    public List<ChiTietHoaDon> getID(String id) {
        String sql = "SELECT * FROM CHITIETHOADON WHERE maHoaDon=?";
        List<ChiTietHoaDon> list = getData(sql, id);
        return list;
    }


    @SuppressLint("Range")
    private List<ChiTietHoaDon> getData(String sql, String... selectionArgs) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            ChiTietHoaDon ct = new ChiTietHoaDon();
            ct.setMaHoaDon(cursor.getString(cursor.getColumnIndex("maHoaDon")));
            ct.setMaSanPham(cursor.getString(cursor.getColumnIndex("maSanPham")));
            ct.setSoLuong(cursor.getInt(cursor.getColumnIndex("soLuong")));
            list.add(ct);
        }
        return list;
    }
}
