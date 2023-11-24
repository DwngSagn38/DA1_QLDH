package com.example.da1_qldh_yuii.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.adapter.ThanhVienAdapter;
import com.example.da1_qldh_yuii.adapter.ThongBaoAdapter;
import com.example.da1_qldh_yuii.dao.ThanhVienDAO;
import com.example.da1_qldh_yuii.dao.ThongBaoDAO;
import com.example.da1_qldh_yuii.model.ThanhVien;
import com.example.da1_qldh_yuii.model.ThongBao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class fragment_thongbao extends Fragment {

    ListView lvThongBao;

    ArrayList<ThongBao> list;

    ArrayList<ThanhVien> listThanhVien;

    ThanhVienDAO thanhVienDAO;

    static ThongBaoDAO tbDAO;

    ThongBaoAdapter adapter;


    ImageView imgAddThongBao;

    ThongBao item;


    EditText edTieuDeAdd;
    EditText edNoiDungAdd;

    
    public fragment_thongbao() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thongbao, container, false);

        lvThongBao = view.findViewById(R.id.lvThongBao);
        imgAddThongBao = view.findViewById(R.id.imgAddThongBao);
        tbDAO = new ThongBaoDAO(getActivity());
        loadData();
        imgAddThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(getActivity(),0);
            }
        });

        lvThongBao.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                item = list.get(position);
                openDialog(getActivity(),1);
                return false;
            }
        });
        return view;
    }

    public void loadData() {
       list = (ArrayList<ThongBao>) tbDAO.getAll();
       adapter = new ThongBaoAdapter(getActivity(),this,list);
       lvThongBao.setAdapter(adapter);
    }

    public void xoa(final String Id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có chắc chắn muốn xóa không?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tbDAO.delete(Id);
                loadData();
                dialog.cancel();
                Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(getContext(), "Không xóa", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void openDialog(final Context context,final int type){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dl_them_thongbao, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        edTieuDeAdd = view.findViewById(R.id.edTieuDeAdd);
        edNoiDungAdd = view.findViewById(R.id.edNoiDungAdd);
        Button btnLuuThongBaoAdd = view.findViewById(R.id.btnLuuThongBaoAdd);
        Button btnHuyThongBaoAdd = view.findViewById(R.id.btnHuyThongBaoAdd);

        if(type != 0){
            edTieuDeAdd.setText(item.getTieuDe());
            edNoiDungAdd.setText(item.getNoiDung());

        }



        btnHuyThongBaoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnLuuThongBaoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item = new ThongBao();
                item.setNoiDung(edNoiDungAdd.getText().toString());
                item.setTieuDe(edTieuDeAdd.getText().toString());
                if (validate() > 0){
                    if (type == 0){
                        if (tbDAO.insert(item) > 0){
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        if (tbDAO.update(item) > 0){
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }
                    loadData();
                    dialog.dismiss();
                }

            }
        });
        dialog.show();
    }
    public int validate(){
        int check = 1;
        if(edTieuDeAdd.getText().length() == 0 || edNoiDungAdd.getText().length() == 0){
            Toast.makeText(getContext(), "Không được bỏ trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
    }
