package com.example.da1_qldh_yuii.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.adapter.BangGiaAdapter;
import com.example.da1_qldh_yuii.dao.BangGiaTheoSizeDAO;
import com.example.da1_qldh_yuii.dao.ThanhVienDAO;
import com.example.da1_qldh_yuii.model.BangGia;
import com.example.da1_qldh_yuii.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class fragment_banggiatheosize extends Fragment {

    BangGiaTheoSizeDAO bangGiaTheoSizeDAO;
    RecyclerView recyclerViewBangGia;
    ThanhVienDAO tvDAO;
    int level;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_banggiatheosize, container, false);

        TextView tvtest =view.findViewById(R.id.tvtest);
        recyclerViewBangGia = view.findViewById(R.id.rcvBangGiaTheoSize);
        FloatingActionButton floatAddTV = view.findViewById(R.id.floatAddBangGia);

        bangGiaTheoSizeDAO = new BangGiaTheoSizeDAO(getContext());
        loadData();


        // Nhân viên không có quyền thêm bảng giá
        // Nhận Bundle từ Fragment
        SharedPreferences pref = getContext().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        int level = pref.getInt("LEVEL", 1);
        if (level == 1){
            floatAddTV.setVisibility(View.GONE);
        }

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

        EditText edtSizeAdd = view.findViewById(R.id.edtSizeAdd);
        EditText edtGiaBanAdd = view.findViewById(R.id.edtGiaBanAdd);

        //thêm
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String checkSize = edtSizeAdd.getText().toString().trim();
                String checkGiaban = edtGiaBanAdd.getText().toString().trim();

                if (isInt(checkSize) && isDouble(checkGiaban)){
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
                }else {
                    Toast.makeText(getContext(), "Size hoặc giá bán chưa hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //hủy
        builder.setNegativeButton("Hủy",null);


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean isInt(String so){
        return so.matches("\\d++");
    }
    public boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}