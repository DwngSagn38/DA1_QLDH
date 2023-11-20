package com.example.da1_qldh_yuii.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.adapter.BangGiaAdapter;
import com.example.da1_qldh_yuii.dao.BangGiaTheoSizeDAO;
import com.example.da1_qldh_yuii.model.BangGia;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class fragment_banggiatheosize extends Fragment {

    BangGiaTheoSizeDAO bangGiaTheoSizeDAO;
    RecyclerView recyclerViewBangGia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_banggiatheosize, container, false);
        recyclerViewBangGia = view.findViewById(R.id.rcvBangGiaTheoSize);
        FloatingActionButton floatAddTV = view.findViewById(R.id.floatAddBangGia);

        bangGiaTheoSizeDAO = new BangGiaTheoSizeDAO(getContext());
        loadData();


        floatAddTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;
    }



    //load data
    private void loadData(){

        ArrayList<BangGia> list = bangGiaTheoSizeDAO.getDSBangGia();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewBangGia.setLayoutManager(linearLayoutManager);
        BangGiaAdapter adapter = new BangGiaAdapter(getContext(), list, bangGiaTheoSizeDAO);
        recyclerViewBangGia.setAdapter(adapter);

    }

    private void showDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dl_them_banggiatheosize, null);
        builder.setView(view);

        EditText edtMaBangGiaAdd = view.findViewById(R.id.edtMaBangGiaAdd);
        EditText edtSizeAdd = view.findViewById(R.id.edtSizeAdd);
        EditText edtGiaBanAdd = view.findViewById(R.id.edtGiaBanAdd);

        //thêm
        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int size = Integer.parseInt(edtSizeAdd.getText().toString());
                double giaBan = Double.parseDouble(edtGiaBanAdd.getText().toString());

                boolean check = bangGiaTheoSizeDAO.themBangGia(size, giaBan);
                if (check){
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    //load data
                    loadData();

                }else{
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //hủy
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



}