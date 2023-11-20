package com.example.da1_qldh_yuii.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_qldh_yuii.database.DbHelper;
import com.example.da1_qldh_yuii.model.BangGia;

import java.util.ArrayList;

public class BangGiaTheoSizeDAO {

    DbHelper dbHelper;
    public BangGiaTheoSizeDAO(Context context){
        dbHelper = new DbHelper(context);

    }

    public ArrayList<BangGia> getDSBangGia(){
        ArrayList<BangGia> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM BANGGIA", null);

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                list.add(new BangGia(cursor.getInt(0), cursor.getInt(1), cursor.getDouble(2)));
            }while (cursor.moveToNext());

        }

        return list;
    }


    public boolean themBangGia(int size, double giaBan){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("size", size);
        contentValues.put("giaBan", giaBan);

        long check = sqLiteDatabase.insert("BANGGIA", null, contentValues);
        if (check == -1)
            return false;

        return true;

    }

    //update
    public boolean capNhatBangGia( BangGia bg ){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("size", bg.getSize());
        contentValues.put("giaBan", bg.getGiaBan());

        long check = sqLiteDatabase.update("BANGGIA", contentValues, "maBangGia = ?", new String[]{String.valueOf(bg.getMaBangGia())});

        if (check == -1)
            return false;
        return true;

    }


    public int xoaBangGia(int maBangGia) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        // Kiểm tra xem bảng giá với mã bảng giá đã tồn tại hay chưa
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM BANGGIA WHERE maBangGia = ?", new String[]{String.valueOf(maBangGia)});
        if (cursor.getCount() == 0) {
            cursor.close();
            return 0; // Trạng thái 0: Bảng giá không tồn tại
        }
        cursor.close();

        // Thực hiện xóa bảng giá
        int rowsDeleted = sqLiteDatabase.delete("BANGGIA", "maBangGia = ?", new String[]{String.valueOf(maBangGia)});
        if (rowsDeleted == 1) {
            return 1; // Trạng thái 1: Xóa bảng giá thành công
        } else {
            return -1; // Trạng thái -1: Xóa bảng giá không thành công
        }
    }



}
