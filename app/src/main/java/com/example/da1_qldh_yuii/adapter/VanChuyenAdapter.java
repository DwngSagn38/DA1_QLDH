package com.example.da1_qldh_yuii.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.dao.VanChuyenDAO;
import com.example.da1_qldh_yuii.model.VanChuyen;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class VanChuyenAdapter extends RecyclerView.Adapter<VanChuyenAdapter.viewholder> {

    VanChuyenDAO vcDAO;
    private final  Context context;
    private final  ArrayList<VanChuyen> list;
    String statusColor = "";

    public VanChuyenAdapter(Context context, ArrayList<VanChuyen> list) {
        this.context = context;
        this.list = list;
        vcDAO = new VanChuyenDAO(context);
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_rvc_donvivanchuyen,null);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        if (position >= 0  && position <list.size()){
            SharedPreferences pref = context.getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            int level = pref.getInt("LEVEL", 1);

            if (level == 1){
                holder.trangthai.setClickable(false);
                holder.trangthai.setFocusable(false);
            }

            VanChuyen vc = list.get(position);
            holder.tenDVVCht.setText(vc.getTenVanChuyen());
            holder.maDVVCht.setText(vc.getMaVanChuyen());
            holder.giaDVVCht.setText(vc.getGiaVanChuyen()+" VNĐ");

            if (vc.getTrangThai() == 1)
                statusColor = "#F44336";
            else
                statusColor = "#1A3CF8";

            holder.trangthai.setButtonTintList(ColorStateList.valueOf(Color.parseColor(statusColor)));

            if (level != 1){
                holder.trangthai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogchitiet(vc);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() > 0){
            return list.size();
        }
        return 0;
    }


    public static class viewholder extends RecyclerView.ViewHolder {

        TextView maDVVCht,tenDVVCht,giaDVVCht;
        RadioButton trangthai;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            giaDVVCht = itemView.findViewById(R.id.giaDVVCht);
            tenDVVCht = itemView.findViewById(R.id.tenDVVCht);
            maDVVCht = itemView.findViewById(R.id.maDVVCht);
            trangthai = itemView.findViewById(R.id.trangthai);
        }
    }

    public void dialogchitiet(VanChuyen vc){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dl_xem_thongtindonvivanchuyen,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextView txtMaDVVCtt = view.findViewById(R.id.txtMaDVVCtt);
        TextView txtTenDVVCtt = view.findViewById(R.id.txtTenDVVCtt);
        TextView txtChiPhiDVVCtt = view.findViewById(R.id.txtChiPhiDVVCtt);
        TextView txtTrangThaitt = view.findViewById(R.id.txtTrangThaitt);
        Button btnDeleteDVVCtt = view.findViewById(R.id.btnDeleteDVVCtt);
        Button btnLuuDVVCtt = view.findViewById(R.id.btnLuuDVVCtt);
        TextView txtMoTaDVVCtt =view.findViewById(R.id.txtMoTaDVVCtt);

        txtMaDVVCtt.setText("Mã DVVC: "+vc.getMaVanChuyen());
        txtTenDVVCtt.setText("Tên DVVC: "+vc.getTenVanChuyen());
        txtChiPhiDVVCtt.setText("Giá VC: "+vc.getGiaVanChuyen()+"");
        txtMoTaDVVCtt.setText(vc.getMoTa());
        if (vc.getTrangThai() == 1) {
            txtTrangThaitt.setText("Trạng thái: Tạm ngừng");
            txtTrangThaitt.setTextColor(Color.RED);
        }else{
            txtTrangThaitt.setText("Trạng thái: Còn hợp tác");
            txtTrangThaitt.setTextColor(Color.BLUE);
        }

        btnDeleteDVVCtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Cảnh báo!");
                builder1.setMessage("Bạn có chắc chắn muốn xóa không?");
                builder1.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (vcDAO.delete(vc.getMaVanChuyen()) != -1){
                            list.clear();
                            list.addAll(vcDAO.getAll());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder1.setNegativeButton("Không",null);
                builder1.show();
            }
        });

        btnLuuDVVCtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog(vc,context,1,list);
                dialog.dismiss();
            }
        });


    }

    public void opendialog(VanChuyen vc,Context context,int type,ArrayList<VanChuyen> list){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dl_sua_donvivanchuyen,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputEditText txtMaDonViVCsua = view.findViewById(R.id.txtMaDonViVCsua);
        TextInputEditText txtTenDonViVCsua = view.findViewById(R.id.txtTenDonViVCsua);
        TextInputEditText txtChiPhiVCsua = view.findViewById(R.id.txtChiPhiVCsua);
        EditText edtMoTaDVVCsua = view.findViewById(R.id.edtMoTaDVVCsua);
        Button btnLuuDVVCsua = view.findViewById(R.id.btnLuuDVVCsua);
        Button btnHuyDVVCsua = view.findViewById(R.id.btnHuyDVVCsua);
        LinearLayout llStatus =view.findViewById(R.id.llStatus);
        RadioButton trangthai = view.findViewById(R.id.trangthai);
        TextView tvStatus =view.findViewById(R.id.tvStatus);

        if (type == 1){
            txtMaDonViVCsua.setText(vc.getMaVanChuyen());
            txtTenDonViVCsua.setText(vc.getTenVanChuyen());
            txtChiPhiVCsua.setText(vc.getGiaVanChuyen()+"");
            edtMoTaDVVCsua.setText(vc.getMoTa());


            txtMaDonViVCsua.setClickable(false);
            txtMaDonViVCsua.setFocusable(false);
            if (vc.getTrangThai() == 1) {
                statusColor = "#F44336";
                tvStatus.setText("Tạm ngừng");
            }else{
                statusColor = "#1A3CF8";
                tvStatus.setText("Còn hợp tác");
            }
            trangthai.setButtonTintList(ColorStateList.valueOf(Color.parseColor(statusColor)));
            tvStatus.setTextColor(ColorStateList.valueOf(Color.parseColor(statusColor)));
            llStatus.setVisibility(View.VISIBLE);

            trangthai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (statusColor.equals("#F44336")) {
                        statusColor = "#1A3CF8";
                        tvStatus.setText("Còn hợp tác");
                    }else{
                        statusColor = "#F44336";
                        tvStatus.setText("Tạm ngừng");
                    }
                    trangthai.setButtonTintList(ColorStateList.valueOf(Color.parseColor(statusColor)));
                    tvStatus.setTextColor(ColorStateList.valueOf(Color.parseColor(statusColor)));
                }
            });
        }

        btnHuyDVVCsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnLuuDVVCsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma = txtMaDonViVCsua.getText().toString();
                String ten = txtTenDonViVCsua.getText().toString();
                String gia = txtChiPhiVCsua.getText().toString();
                String mota = edtMoTaDVVCsua.getText().toString();
                if (ma.isEmpty() || ten.isEmpty() || gia.isEmpty() || mota.isEmpty()){
                    Toast.makeText(context, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                } else if (!isDouble(gia)) {
                    Toast.makeText(context, "Giá không hợp lệ", Toast.LENGTH_SHORT).show();
                } else {
                    VanChuyenDAO vcDao = new VanChuyenDAO(context);
                    vc.setMaVanChuyen(ma);
                    vc.setGiaVanChuyen(Double.parseDouble(gia));
                    vc.setTenVanChuyen(ten);
                    vc.setMoTa(mota);
                    if (type == 0){
                        vc.setTrangThai(0);
                        if (vcDao.insert(vc) != -1){
                            list.clear();
                            list.addAll(vcDao.getAll());
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            notifyDataSetChanged();
                        }else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        if (tvStatus.getText().toString().equals("Tạm ngừng")){
                            vc.setTrangThai(1);
                        }else
                            vc.setTrangThai(0);
                        if (vcDao.update(vc) != -1){
                            list.clear();
                            list.addAll(vcDao.getAll());
                            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            notifyDataSetChanged();
                        }else {
                            Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
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
