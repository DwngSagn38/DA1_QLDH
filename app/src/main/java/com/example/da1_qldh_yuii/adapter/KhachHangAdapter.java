package com.example.da1_qldh_yuii.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.dao.KhachHangDAO;
import com.example.da1_qldh_yuii.model.KhachHang;

import java.util.ArrayList;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.viewholder>{

    private Context context;
    private ArrayList<KhachHang> list;
    private KhachHangDAO khachHangDAO;

    public KhachHangAdapter(Context context, ArrayList<KhachHang> list) {
        this.context = context;
        this.list = list;
        khachHangDAO = new KhachHangDAO(context);
    }

    @NonNull
    @Override
    public KhachHangAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_rcv_khachhang, parent, false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhachHangAdapter.viewholder holder, int position) {

        holder.txtMaKH.setText("Mã khách hàng: " + list.get(position).getMaKhachHang());
        holder.txtTenKH.setText("Tên khách hàng: " + list.get(position).getTenKhachHang());
        holder.txtSoDienThoaiKH.setText("Số điện thoại: " + list.get(position).getSoDienThoai());
        holder.txtDiaChiKH.setText("Địa chỉ: " + list.get(position).getDiaChi());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView txtMaKH, txtTenKH, txtSoDienThoaiKH, txtDiaChiKH;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtMaKH = itemView.findViewById(R.id.txtMaKH);
            txtTenKH = itemView.findViewById(R.id.txtTenKH);
            txtSoDienThoaiKH = itemView.findViewById(R.id.txtSoDienThoaiKH);
            txtDiaChiKH = itemView.findViewById(R.id.txtDiaChiKH);


        }
    }


}


