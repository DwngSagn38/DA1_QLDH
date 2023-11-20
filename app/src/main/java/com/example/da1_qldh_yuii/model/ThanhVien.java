package com.example.da1_qldh_yuii.model;

public class ThanhVien {
    private String maThanhVien, tenThanhVien, soDienThoai, matKhau;
    private int phanQuyen;

    public ThanhVien() {
    }

    public ThanhVien(String maThanhVien, String tenThanhVien, String soDienThoai, String matKhau, int phanQuyen) {
        this.maThanhVien = maThanhVien;
        this.tenThanhVien = tenThanhVien;
        this.soDienThoai = soDienThoai;
        this.matKhau = matKhau;
        this.phanQuyen = phanQuyen;
    }

    public String getMaThanhVien() {
        return maThanhVien;
    }

    public void setMaThanhVien(String maThanhVien) {
        this.maThanhVien = maThanhVien;
    }

    public String getTenThanhVien() {
        return tenThanhVien;
    }

    public void setTenThanhVien(String tenThanhVien) {
        this.tenThanhVien = tenThanhVien;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getPhanQuyen() {
        return phanQuyen;
    }

    public void setPhanQuyen(int phanQuyen) {
        this.phanQuyen = phanQuyen;
    }
}
