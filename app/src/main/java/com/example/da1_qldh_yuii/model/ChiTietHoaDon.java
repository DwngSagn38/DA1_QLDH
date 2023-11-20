package com.example.da1_qldh_yuii.model;

public class ChiTietHoaDon {
    private String  maChiTietHoaDon,maHoaDon;
    private double giamGia;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(String maChiTietHoaDon, String maHoaDon, double giamGia) {
        this.maChiTietHoaDon = maChiTietHoaDon;
        this.maHoaDon = maHoaDon;
        this.giamGia = giamGia;
    }

    public String getMaChiTietHoaDon() {
        return maChiTietHoaDon;
    }

    public void setMaChiTietHoaDon(String maChiTietHoaDon) {
        this.maChiTietHoaDon = maChiTietHoaDon;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }
}
