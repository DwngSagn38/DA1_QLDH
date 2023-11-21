package com.example.da1_qldh_yuii.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {


    public DbHelper(@Nullable Context context) {
        super(context, "QLHD", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String dbKhachHang = "CREATE TABLE KHACHHANG(\n" +
                "maKhachHang TEXT PRIMARY KEY ,\n" +
                "tenKhachHang TEXT, \n" +
                "soDienThoai TEXT, \n" +
                "diaChi TEXT);";
        db.execSQL(dbKhachHang);


        String dbBangGia = "CREATE TABLE BANGGIA (\n" +
                "maBangGia INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "size INTEGER, \n" +
                "giaBan DOUBLE);";
        db.execSQL(dbBangGia);


        String dbSanPham = "CREATE TABLE SANPHAM (\n" +
                " maSanPham TEXT PRIMARY KEY , \n" +
                "anhSanPham INTEGER, \n" +
                "tenSanPham TEXT, \n" +
                "trangThai INTEGER, \n" +
                "maBangGia INTEGER, \n" +
                "FOREIGN KEY (maBangGia) REFERENCES BANGGIA(maBangGia) );";
        db.execSQL(dbSanPham);



        String dbHoaDon = "CREATE TABLE HOADON (\n" +
                "    maHoaDon TEXT PRIMARY KEY,\n" +
                "    maSanPham INTEGER,\n" +
                "    maThanhVien INTEGER,\n" +
                "    maKhachHang INTEGER,\n" +
                "    maVanChuyen INTEGER,\n" +
                "    ngayTao TEXT,\n" +
                "    ngayNhanHang TEXT,\n" +
                "    tienCoc DOUBLE,\n" +
                "    soLuong INTEGER,\n" +
                "    ghiChu TEXT,\n" +
                "    trangThai INTEGER,\n" +
                "    FOREIGN KEY (maSanPham) REFERENCES SANPHAM(maSanPham),\n" +
                "    FOREIGN KEY (maThanhVien) REFERENCES THANHVIEN(maThanhVien),\n" +
                "    FOREIGN KEY (maKhachHang) REFERENCES KHACHHANG(maKhachHang),\n" +
                "FOREIGN KEY (maVanChuyen) REFERENCES VANCHUYEN(maVanChuyen) \n" +
                ");";
        db.execSQL(dbHoaDon);


        String dbChiTietHoaDon = "CREATE TABLE CHITIETHOADON(\n" +
                "    maChiTietHoaDon TEXT PRIMARY KEY ,\n" +
                "    maHoaDon TEXT,\n" +
                "    giamGia DOUBLE,\n" +
                "    FOREIGN KEY (maHoaDon) REFERENCES HOADON(maHoaDon)\n" +
                ");";
        db.execSQL(dbChiTietHoaDon);



        String dbVanChuyen = "CREATE TABLE VANCHUYEN (\n" +
                "    maVanChuyen TEXT PRIMARY KEY,\n" +
                "    tenVanChuyen TEXT,\n" +
                "    giaVanChuyen DOUBLE,\n" +
                "    moTa TEXT,\n" +
                "    trangThai INTEGER\n" +
                ");";
        db.execSQL(dbVanChuyen);


        String dbThanhVien = "CREATE TABLE THANHVIEN (\n" +
                "    maThanhVien TEXT PRIMARY KEY,\n" +
                "    tenThanhVien TEXT,\n" +
                "    soDienThoai TEXT,\n" +
                "    matKhau TEXT,\n" +
                "    phanQuyen INTEGER\n" +
                ");";
        db.execSQL(dbThanhVien);


        String dbTonKho = "CREATE TABLE TONKHO (\n" +
                "    maTonKho TEXT PRIMARY KEY,\n" +
                "    maSanPham TEXT,\n" +
                "    maHoaDon TEXT,\n" +
                "    soLuong INTEGER,\n" +
                "    FOREIGN KEY (maSanPham) REFERENCES SANPHAM(maSanPham),\n" +
                "    FOREIGN KEY (maHoaDon) REFERENCES HOADON(maHoaDon)\n" +
                ");";
        db.execSQL(dbTonKho);

        db.execSQL("CREATE TABLE THONGBAO (maThongBao INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maThanhVien TEXT," +
                "tieuDe TEXT," +
                "noiDung TEXT," +
                "ngayDang TEXT," +
                "FOREIGN KEY (maThanhVien) REFERENCES THANHVIEN (maThanhVien))");


        //add dl


        db.execSQL("INSERT INTO KHACHHANG VALUES ('KH01', 'Nhung', '0366666666', 'Hải Dương')," +
                                                " ('KH02', 'Trinh', '0367777777', 'Hà Nội'), " +
                                                "('KH03', 'Sang', '0368888888', 'Hà Nội')");

        //mã, size, giá
        db.execSQL("INSERT INTO BANGGIA VALUES(1, 5, 200)," +
                                            " (2, 6, 230)," +
                                            " (3, 4, 150)");

        //mã, tên, trạng thái, mã bảng giá
        db.execSQL("INSERT INTO SANPHAM VALUES('SP01',1, 'Gấu bông', 1, 2)," +
                                            " ('SP02',2, 'Hoa', 2, 1)," +
                                            " ('SP03',2, 'Khăn', 1, 1)");


        //maThanhVien, tenThanhVien, soDienThoai, matKhau, phanQuyen
        db.execSQL("INSERT INTO THANHVIEN VALUES('TV01', 'Nguyễn Thị Lý', '0922222222', '123456', 0),\n" +
                "('TV02', 'Phạm Thị B', '0933333333', '123456', 1),\n" +
                "('TV03', 'Lê Văn C', '0944444444', '123456', 1)");

        //maVanChuyen, tenVanChuyen, GiaVanChuyen, moTa, trangThai
        db.execSQL("INSERT INTO VANCHUYEN VALUES('VC01', 'Giao hàng nhanh', 20, 'Dịch vụ giao hàng nhanh trong thành phố', 0), \n" +
                "('VC02', 'Chuyển phát nhanh', 15, 'Dịch vụ chuyển phát nhanh toàn quốc', 0), \n" +
                "('VC03', 'Giao hàng tiết kiệm', 10, 'Dịch vụ giao hàng tiết kiệm', 1)");



        //maHoaDon, maSanPham, maThanhVien, maKhachHang, maVanChuyen, ngayTao, ngayNhanHang, tienCoc, soLuong, ghiChu, trangThai
        db.execSQL("INSERT INTO HOADON VALUES" +
                "('HD01', 'SP02', 'TV03', 'KH02', 'VC02', '2023-11-08', '2023-11-15', 0, 1, 'Giao hàng nhanh', 0)," +
                "('HD02', 'SP01', 'TV01', 'KH01', 'VC01', '2023-11-08', '2023-11-15', 500000, 3, 'Giao hàng nhanh', 1)");

        //maChiTietHoaDon, maHoaDon, giamGia
        db.execSQL("INSERT INTO CHITIETHOADON VALUES('CT01', 'HD01', 10)," +
                                                    " ('CT02','HD02',20)");

        //maTonKho, maSanPham, maHoaDon, soLuong
        db.execSQL("INSERT INTO TONKHO VALUES('TK01', 'SP01', 'HD01', 1)");

        //maThongBao, maThanhVien, tieuDe, noiDung, ngayDang
        db.execSQL("INSERT INTO THONGBAO VALUES(1, 'TV01','MÃ SP02','GIẢM GIÁ 10%','10-11-2023')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i != i1){
            db.execSQL("DROP TABLE IF EXISTS KHACHHANG");
            db.execSQL("DROP TABLE IF EXISTS BANGGIA");
            db.execSQL("DROP TABLE IF EXISTS SANPHAM");
            db.execSQL("DROP TABLE IF EXISTS HOADON");
            db.execSQL("DROP TABLE IF EXISTS CHITIETHOADON");
            db.execSQL("DROP TABLE IF EXISTS VANCHUYEN");
            db.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            db.execSQL("DROP TABLE IF EXISTS TONKHO");
            db.execSQL("DROP TABLE IF EXISTS THONGBAO");
            onCreate(db);

        }
    }
}
