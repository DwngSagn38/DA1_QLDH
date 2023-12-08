package com.example.da1_qldh_yuii.model;

public class HoaDon {
    private String maHoaDon;
    private String maSanPham;
    private String maThanhVien;
    private String maKhachHang;
    private String maVanChuyen;
    private String ngayTao;
    private String ngayNhanHang;
    private String ghiChu;
    private String ngayGiaoHang;

    public String getNgayGiaoHang() {
        return ngayGiaoHang;
    }

    public void setNgayGiaoHang(String ngayGiaoHang) {
        this.ngayGiaoHang = ngayGiaoHang;
    }

    public String getNgayGiaoOk() {
        return ngayGiaoOk;
    }

    public void setNgayGiaoOk(String ngayGiaoOk) {
        this.ngayGiaoOk = ngayGiaoOk;
    }

    private String ngayGiaoOk;
    private int soLuong,trangThai;
    private double tienCoc;

    private double tongTien;
    private int gio;

    public int getGio() {
        return gio;
    }

    public void setGio(int gio) {
        this.gio = gio;
    }

    public HoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public HoaDon() {
    }

    public HoaDon(String maHoaDon, String maSanPham, String maThanhVien, String maKhachHang, String maVanChuyen, String ngayTao,int gio, String ngayNhanHang, String ghiChu, int soLuong, int trangThai, double tienCoc) {
        this.maHoaDon = maHoaDon;
        this.maSanPham = maSanPham;
        this.maThanhVien = maThanhVien;
        this.maKhachHang = maKhachHang;
        this.maVanChuyen = maVanChuyen;
        this.ngayTao = ngayTao;
        this.gio = gio;
        this.ngayNhanHang = ngayNhanHang;
        this.ghiChu = ghiChu;
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

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getMaThanhVien() {
        return maThanhVien;
    }

    public void setMaThanhVien(String maThanhVien) {
        this.maThanhVien = maThanhVien;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getMaVanChuyen() {
        return maVanChuyen;
    }

    public void setMaVanChuyen(String maVanChuyen) {
        this.maVanChuyen = maVanChuyen;
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
