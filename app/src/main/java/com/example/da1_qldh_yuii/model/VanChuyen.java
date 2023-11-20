package com.example.da1_qldh_yuii.model;

public class VanChuyen {
    private String maVanChuyen,tenVanChuyen,moTa;
    private int trangThai;
    private double giaVanChuyen;

    public VanChuyen() {
    }

    public VanChuyen(String maVanChuyen, String tenVanChuyen, String moTa, int trangThai, double giaVanChuyen) {
        this.maVanChuyen = maVanChuyen;
        this.tenVanChuyen = tenVanChuyen;
        this.moTa = moTa;
        this.trangThai = trangThai;
        this.giaVanChuyen = giaVanChuyen;
    }

    public String getMaVanChuyen() {
        return maVanChuyen;
    }

    public void setMaVanChuyen(String maVanChuyen) {
        this.maVanChuyen = maVanChuyen;
    }

    public String getTenVanChuyen() {
        return tenVanChuyen;
    }

    public void setTenVanChuyen(String tenVanChuyen) {
        this.tenVanChuyen = tenVanChuyen;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public double getGiaVanChuyen() {
        return giaVanChuyen;
    }

    public void setGiaVanChuyen(double giaVanChuyen) {
        this.giaVanChuyen = giaVanChuyen;
    }
}
