package com.example.da1_qldh_yuii.dao;

import android.content.Context;

import com.example.da1_qldh_yuii.database.DbHelper;

public class BangGiaTheoSizeDAO {

    DbHelper dbHelper;
    public BangGiaTheoSizeDAO(Context context){
        dbHelper = new DbHelper(context);


    }
}
