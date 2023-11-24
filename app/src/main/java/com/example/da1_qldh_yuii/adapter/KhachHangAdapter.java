package com.example.da1_qldh_yuii.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.dao.KhachHangDAO;
import com.example.da1_qldh_yuii.fragment.fragment_khachhang;
import com.example.da1_qldh_yuii.model.KhachHang;

import java.util.ArrayList;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.viewholder>{

    private Context context;
    private ArrayList<KhachHang> list;
    private KhachHangDAO khDAO;

    fragment_khachhang frg = new fragment_khachhang();

    public KhachHangAdapter(Context context, ArrayList<KhachHang> list) {
        this.context = context;
        this.list = list;
        khDAO = new KhachHangDAO(context);
    }

    ThanhVienAdapter.OnItemClickListener OnItemClickListener;
    @NonNull
    @Override
    public KhachHangAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_rcv_khachhang, parent, false);

        viewholder viewholder = new viewholder(view);
        viewholder.setOnItemClickListener(viewholder.listener);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhachHangAdapter.viewholder holder, int position) {

        KhachHang kh = list.get(position);
        holder.txtTenKH.setText("Tên: " + list.get(position).getTenKhachHang());
        holder.txtMaKH.setText("Mã KH: " + list.get(position).getMaKhachHang());
//        holder.txtSoDienThoaiKH.setText("SDT: " + list.get(position).getSoDienThoai());
//        holder.txtDiaChiKH.setText("Địa chỉ: " + list.get(position).getDiaChi());

        holder.setOnItemClickListener(new ThanhVienAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                opendialog(kh);
                notifyDataSetChanged();
//                frg.showDialog(context,kh,1);
//                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        private ThanhVienAdapter.OnItemClickListener listener;
        TextView txtMaKH, txtTenKH, txtSoDienThoaiKH, txtDiaChiKH;
        public viewholder(@NonNull View itemView) {
            super(itemView); 
            txtTenKH = itemView.findViewById(R.id.txtTenKH);
            txtMaKH = itemView.findViewById(R.id.txtMaKH);
//            txtSoDienThoaiKH = itemView.findViewById(R.id.txtSoDienThoaiKH);
//            txtDiaChiKH = itemView.findViewById(R.id.txtDiaChiKH);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
        public void setOnItemClickListener(ThanhVienAdapter.OnItemClickListener mlistener){
            listener = mlistener;
        }
    }

    public void opendialog(KhachHang kh) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dl_xem_thongtinkhachhang,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextView tvMaKH = view.findViewById(R.id.tvMaKH);
        TextView tvTenKH = view.findViewById(R.id.tvTenKH);
        TextView tvSDT = view.findViewById(R.id.tvSDT);
        TextView tvDiaChi = view.findViewById(R.id.tvDiaChi);
        Button btnUpdateKh = view.findViewById(R.id.btnUpdateKh);
        Button btnDeleteKh = view.findViewById(R.id.btnDeleteKh);

        tvMaKH.setText("Mã KH: "+kh.getMaKhachHang());
        tvTenKH.setText("Tên KH: "+kh.getTenKhachHang());
        tvSDT.setText("SDT: "+kh.getSoDienThoai());
        tvDiaChi.setText("Địa chỉ: "+kh.getDiaChi());


        btnDeleteKh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Cảnh báo!");
                builder1.setMessage("Bạn có chắc chắn muốn xóa không?");
                builder1.setNegativeButton("No",null);
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (khDAO.xoaKhachHang(kh.getMaKhachHang()) == 1){
                            list.clear();
                            list.addAll(khDAO.getDSKhachHang());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder1.show();
            }
        });

        btnUpdateKh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialogUpdate(kh);
                dialog.dismiss();
            }
        });
    }

    private void opendialogUpdate(KhachHang kh) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dl_sua_khachhang,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edMaKHsua = view.findViewById(R.id.edMaKHsua);
        EditText edTenKHsua = view.findViewById(R.id.edTenKHsua);
        EditText edSDTsua = view.findViewById(R.id.edSDTsua);
        EditText edDiaChisua = view.findViewById(R.id.edDiaChisua);
        Button btnLuuKH = view.findViewById(R.id.btnUpdateKH);


        edMaKHsua.setText(kh.getMaKhachHang());
        edMaKHsua.setClickable(false);
        edMaKHsua.setFocusable(false);
        edTenKHsua.setText(kh.getTenKhachHang());
        edSDTsua.setText(kh.getSoDienThoai());
        edDiaChisua.setText(kh.getDiaChi());

        btnLuuKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kh.setMaKhachHang(edMaKHsua.getText().toString().trim());

                String ten = edTenKHsua.getText().toString().trim();
                String sdt = edSDTsua.getText().toString().trim();
                String dc = edDiaChisua.getText().toString().trim();

                if (ten.isEmpty() || sdt.isEmpty() || dc.isEmpty()){
                    Toast.makeText(context, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                }else {

                    if (khDAO.capNhatKhachHang(kh.getMaKhachHang(),ten,sdt,dc)){
                        list.clear();
                        list.addAll(khDAO.getDSKhachHang());
                        notifyDataSetChanged();
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }


}


