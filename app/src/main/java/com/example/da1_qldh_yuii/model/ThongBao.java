package com.example.da1_qldh_yuii.model;

import java.util.Date;

public class ThongBao {
    private int maThongBao;
    private String maThanhVien,tieuDe,noiDung,ngayDang;


    public ThongBao() {
    }

    public ThongBao(int maThongBao, String maThanhVien, String tieuDe, String noiDung, String ngayDang) {
        this.maThongBao = maThongBao;
        this.maThanhVien = maThanhVien;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.ngayDang = ngayDang;
    }

    public int getMaThongBao() {
        return maThongBao;
    }

    public void setMaThongBao(int maThongBao) {
        this.maThongBao = maThongBao;
    }

    public String getMaThanhVien() {
        return maThanhVien;
    }

    public void setMaThanhVien(String maThanhVien) {
        this.maThanhVien = maThanhVien;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(String ngayDang) {
        this.ngayDang = ngayDang;
    }
}
