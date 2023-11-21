package com.example.da1_qldh_yuii.model;

public class BangGia {
    private int maBangGia;
    private int size;
    private double giaBan;

    public BangGia() {
    }

    public BangGia(int maBangGia, int size, double giaBan) {
        this.maBangGia = maBangGia;
        this.size = size;
        this.giaBan = giaBan;
    }

    public BangGia(int size, double giaBan) {
        this.size = size;
        this.giaBan = giaBan;
    }


    public int getMaBangGia() {
        return maBangGia;
    }

    public void setMaBangGia(int maBangGia) {
        this.maBangGia = maBangGia;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }
}
