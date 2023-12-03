package com.example.da1_qldh_yuii.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;

public class SanPham {
    private String maSanPham,tenSanPham;
    private Uri anhSanPham;

    private int trangThai,maBangGia;

    public SanPham() {
    }

    public SanPham(String maSanPham, String tenSanPham, Uri anhSanPham, int trangThai, int maBangGia) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.anhSanPham = anhSanPham;
        this.trangThai = trangThai;
        this.maBangGia = maBangGia;
    }

//    public SanPham(String maSanPham, String tenSanPham, int anhSanPham, int trangThai, int maBangGia) {
//        this.maSanPham = maSanPham;
//        this.tenSanPham = tenSanPham;
//        this.anhSanPham = anhSanPham;
//        this.trangThai = trangThai;
//        this.maBangGia = maBangGia;
//    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public Uri getAnhSanPham() {
        return anhSanPham;
    }

    public void setAnhSanPham(Uri anhSanPham) {
        this.anhSanPham = anhSanPham;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getMaBangGia() {
        return maBangGia;
    }

    public void setMaBangGia(int maBangGia) {
        this.maBangGia = maBangGia;
    }
}
