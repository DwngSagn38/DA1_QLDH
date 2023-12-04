package com.example.da1_qldh_yuii.model;

public class TonKho {
    private String maSanPham,maHoaDon;
    private int soLuong,maTonKho;

    public TonKho() {
    }

    public TonKho(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public TonKho(String maSanPham, String maHoaDon, int soLuong) {
        this.maSanPham = maSanPham;
        this.maHoaDon = maHoaDon;
        this.soLuong = soLuong;
    }

    public int getMaTonKho() {
        return maTonKho;
    }

    public void setMaTonKho(int maTonKho) {
        this.maTonKho = maTonKho;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
