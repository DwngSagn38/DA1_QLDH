package com.example.da1_qldh_yuii.model;

public class BangGia {
    private int maBangGia,size;
    private double gia;

    public BangGia() {
    }

    public BangGia(int maBangGia, int size, double gia) {
        this.maBangGia = maBangGia;
        this.size = size;
        this.gia = gia;
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

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }
}
