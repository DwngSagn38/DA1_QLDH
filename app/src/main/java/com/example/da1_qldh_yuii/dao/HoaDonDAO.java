package com.example.da1_qldh_yuii.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_qldh_yuii.database.DbHelper;
import com.example.da1_qldh_yuii.model.ChiTietHoaDon;
import com.example.da1_qldh_yuii.model.HoaDon;
import com.example.da1_qldh_yuii.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {
    private SQLiteDatabase db;
    private Context context;

    public HoaDonDAO(Context context) {
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(HoaDon obj) {
        ContentValues values = new ContentValues();
        values.put("maHoaDon", obj.getMaHoaDon());
        values.put("maThanhVien", obj.getMaThanhVien());
        values.put("maKhachHang", obj.getMaKhachHang());
        values.put("maVanChuyen", obj.getMaVanChuyen());
        values.put("gio", obj.getGio());
        values.put("ngayTao", obj.getNgayTao());
        values.put("ngayNhanHang", obj.getNgayNhanHang());
        values.put("tienCoc", obj.getTienCoc());
        values.put("soLuong", obj.getSoLuong());
        values.put("ghiChu", obj.getGhiChu());
        values.put("trangThai", obj.getTrangThai());
        return db.insert("HOADON", null, values);
    }

    public long update(HoaDon obj) {
        ContentValues values = new ContentValues();
        values.put("maThanhVien", obj.getMaThanhVien());
        values.put("maKhachHang", obj.getMaKhachHang());
        values.put("maVanChuyen", obj.getMaVanChuyen());
        values.put("ngayTao", obj.getNgayTao());
        values.put("ngayNhanHang", obj.getNgayNhanHang());
        values.put("tienCoc", obj.getTienCoc());
        values.put("soLuong", obj.getSoLuong());
        values.put("ghiChu", obj.getGhiChu());
        values.put("trangThai", obj.getTrangThai());
        return db.update("HOADON", values, "maHoaDon = ?", new String[]{String.valueOf(obj.getMaHoaDon())});
    }

    public List<HoaDon> getListStatus(int status){
        String sql = "SELECT * FROM HOADON where trangThai = ?";
        List<HoaDon> list = getData(sql,String.valueOf(status));
        return list;
    }

    public long delete(String id) {
        return db.delete("HOADON", "maHoaDon = ?", new String[]{String.valueOf(id)});
    }

    public List<HoaDon> getAll() {
        String sql = "SELECT * FROM HOADON";
        return getData(sql);
    }

    public HoaDon getID(String id) {
        String sql = "SELECT * FROM HOADON WHERE maHoaDon=?";
        List<HoaDon> list = getData(sql, id);
        return list.get(0);
    }


    @SuppressLint("Range")
    private List<HoaDon> getData(String sql, String... selectionArgs) {
        List<HoaDon> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            HoaDon hd = new HoaDon();
            hd.setMaHoaDon(cursor.getString(cursor.getColumnIndex("maHoaDon")));
            hd.setMaThanhVien(cursor.getString(cursor.getColumnIndex("maThanhVien")));
            hd.setMaKhachHang(cursor.getString(cursor.getColumnIndex("maKhachHang")));
            hd.setMaVanChuyen(cursor.getString(cursor.getColumnIndex("maVanChuyen")));
            hd.setNgayTao(cursor.getString(cursor.getColumnIndex("ngayTao")));
            hd.setGio(cursor.getInt(cursor.getColumnIndex("gio")));
            hd.setNgayNhanHang(cursor.getString(cursor.getColumnIndex("ngayNhanHang")));
            hd.setTienCoc(cursor.getDouble(cursor.getColumnIndex("tienCoc")));
            hd.setSoLuong(cursor.getInt(cursor.getColumnIndex("soLuong")));
            hd.setGhiChu(cursor.getString(cursor.getColumnIndex("ghiChu")));
            hd.setTrangThai(cursor.getInt(cursor.getColumnIndex("trangThai")));

            list.add(hd);
        }
        return list;
    }
}
