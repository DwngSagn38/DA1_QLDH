package com.example.da1_qldh_yuii.model;

public class TaoHoaDon {
    private int maTHD, soLuongMua;
    private String maSanPham;

    public TaoHoaDon(int soLuongMua, String maSanPham) {
        this.soLuongMua = soLuongMua;
        this.maSanPham = maSanPham;
    }

    public TaoHoaDon(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public TaoHoaDon(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public int getMaTHD() {
        return maTHD;
    }

    public void setMaTHD(int maTHD) {
        this.maTHD = maTHD;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public TaoHoaDon() {
    }
}
