package com.example.da1_qldh_yuii.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.dao.KhachHangDAO;
import com.example.da1_qldh_yuii.model.KhachHang;
import com.google.android.material.textfield.TextInputEditText;

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

        final KhachHang khachHang = list.get(position);

        holder.txtMaKH.setText("Mã khách hàng: " + list.get(position).getMaKhachHang());
        holder.txtTenKH.setText("Tên khách hàng: " + list.get(position).getTenKhachHang());
        holder.txtSoDienThoaiKH.setText("Số điện thoại: " + list.get(position).getSoDienThoai());
        holder.txtDiaChiKH.setText("Địa chỉ: " + list.get(position).getDiaChi());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(khachHang);
            }
        });



    }

    private void showDialog(KhachHang khachHang){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dl_xem_thongtinkhachhang);

        TextView tvMaKH = dialog.findViewById(R.id.tvMaKH);
        TextView tvTenKH = dialog.findViewById(R.id.tvTenKH);
        TextView tvSDT = dialog.findViewById(R.id.tvSDT);
        TextView tvDiaChi = dialog.findViewById(R.id.tvDiaChi);

        Button btnUpdateKH = dialog.findViewById(R.id.btnUpdateKH);
        Button btnDeleteKH = dialog.findViewById(R.id.btnDelteteKH);

        tvMaKH.setText("Mã: " + khachHang.getMaKhachHang());
        tvTenKH.setText("Tên: " + khachHang.getTenKhachHang());
        tvSDT.setText("SĐT: " + khachHang.getSoDienThoai());
        tvDiaChi.setText("Địa chỉ: " + khachHang.getDiaChi());


        btnUpdateKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUpdate(khachHang);
                dialog.dismiss();
            }
        });

        btnDeleteKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Cảnh báo!");
                builder1.setMessage("Bạn có chắc chắn muốn xóa không?");
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(khachHangDAO.xoaKhachHang(khachHang.getMaKhachHang()) == 1){
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            list.remove(khachHang);
                            notifyDataSetChanged();
                        }else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder1.setNegativeButton("No",null);
                builder1.show();
            }
        });



        dialog.show();


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




    //update
    private void showDialogUpdate(final KhachHang khachHang){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dl_sua_khachhang);

        TextInputEditText edMaKHsua = dialog.findViewById(R.id.edMaKHsua);
        TextInputEditText edTenKHsua = dialog.findViewById(R.id.edTenKHsua);
        TextInputEditText edSDTsua = dialog.findViewById(R.id.edSDTsua);
        TextInputEditText edDiaChisua = dialog.findViewById(R.id.edDiaChisua);

        Button btnUpdateKH = dialog.findViewById(R.id.btnUpdateKH);


        edMaKHsua.setText(khachHang.getMaKhachHang());
        edTenKHsua.setText(khachHang.getTenKhachHang());
        edSDTsua.setText(khachHang.getSoDienThoai());
        edDiaChisua.setText(khachHang.getDiaChi());


        btnUpdateKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tenKH = edTenKHsua.getText().toString().trim();
                String sdtKH = edSDTsua.getText().toString().trim();
                String diaChiKH = edDiaChisua.getText().toString().trim();

                boolean isUpdated = khachHangDAO.capNhatKhachHang(khachHang.getMaKhachHang(), tenKH, sdtKH, diaChiKH);

                if (isUpdated){
                    khachHang.setTenKhachHang(tenKH);
                    khachHang.setSoDienThoai(sdtKH);
                    notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });



        dialog.show();
    }



}


