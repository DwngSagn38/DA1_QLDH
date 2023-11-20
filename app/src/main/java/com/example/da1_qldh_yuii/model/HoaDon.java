package com.example.da1_qldh_yuii.model;

public class HoaDon {
    private String maHoaDon,ngayTao,ngayNhanHang,ghiChu;
    private int maSanPham,maThanhVien,maKhachHang,maVanChuyen,soLuong,trangThai;
    private double tienCoc;

    public HoaDon() {
    }

    public HoaDon(String maHoaDon, String ngayTao, String ngayNhanHang, String ghiChu, int maSanPham, int maThanhVien, int maKhachHang, int maVanChuyen, int soLuong, int trangThai, double tienCoc) {
        this.maHoaDon = maHoaDon;
        this.ngayTao = ngayTao;
        this.ngayNhanHang = ngayNhanHang;
        this.ghiChu = ghiChu;
        this.maSanPham = maSanPham;
        this.maThanhVien = maThanhVien;
        this.maKhachHang = maKhachHang;
        this.maVanChuyen = maVanChuyen;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
        this.tienCoc = tienCoc;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getNgayNhanHang() {
        return ngayNhanHang;
    }

    public void setNgayNhanHang(String ngayNhanHang) {
        this.ngayNhanHang = ngayNhanHang;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public int getMaThanhVien() {
        return maThanhVien;
    }

    public void setMaThanhVien(int maThanhVien) {
        this.maThanhVien = maThanhVien;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public int getMaVanChuyen() {
        return maVanChuyen;
    }

    public void setMaVanChuyen(int maVanChuyen) {
        this.maVanChuyen = maVanChuyen;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public double getTienCoc() {
        return tienCoc;
    }

    public void setTienCoc(double tienCoc) {
        this.tienCoc = tienCoc;
    }
}
