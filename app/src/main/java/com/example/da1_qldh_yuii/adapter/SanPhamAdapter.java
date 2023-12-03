package com.example.da1_qldh_yuii.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.dao.BangGiaTheoSizeDAO;
import com.example.da1_qldh_yuii.dao.SanPhamDAO;
import com.example.da1_qldh_yuii.dao.TaoHoaDonDAO;
import com.example.da1_qldh_yuii.fragment.frgTHD_CSP.fragment_chonsanpham;
import com.example.da1_qldh_yuii.fragment.frgSP_KH.frgSanPham;
import com.example.da1_qldh_yuii.model.SanPham;
import com.example.da1_qldh_yuii.model.TaoHoaDon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.viewholder> {
    frgSanPham frg = new frgSanPham();
    private Context currentFragment;
    public void setCurrentFragment(Context fragment) {
        currentFragment = fragment;
    }

    fragment_chonsanpham frg_taoHD = new fragment_chonsanpham();
    SanPhamDAO spDao;
    BangGiaTheoSizeDAO bgDao;

    TaoHoaDonDAO thdDAO;
    private final Context context;
    private final ArrayList<SanPham> list;

    public SanPhamAdapter(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
        spDao = new SanPhamDAO(context);
        bgDao = new BangGiaTheoSizeDAO(context);
        thdDAO = new TaoHoaDonDAO(context);
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_rcv_sanpham,null);

        viewholder viewholder = new viewholder(view);
        viewholder.SetOnItemClickListener(viewholder.listener);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        if (position >= 0 && position < list.size() ){
            SanPham sp = list.get(position);
            holder.txtMaSP.setText("Mã sản phẩm: "+ sp.getMaSanPham());
            holder.txtTenSP.setText("Tên : "+ sp.getTenSanPham());
            holder.txtGiaSP.setText("Giá: "+ bgDao.getID(sp.getMaBangGia()).getGiaBan() +" VNĐ");

            if (sp.getTrangThai() == 0){
                holder.txtTrangThaiSP.setText("Trạng thái : Còn hàng");
                holder.txtTrangThaiSP.setTextColor(Color.GREEN);
            }else if (sp.getTrangThai() == 1){
                holder.txtTrangThaiSP.setText("Trạng thái : Đặt hàng");
                holder.txtTrangThaiSP.setTextColor(Color.BLUE);
            }else {
                holder.txtTrangThaiSP.setText("Trạng thái : Ngừng bán");
                holder.txtTrangThaiSP.setTextColor(Color.RED);
            }

            if (context.equals(currentFragment)){
                holder.txtMaSP.setVisibility(View.GONE);
                holder.txtGiaSP.setVisibility(View.GONE);
                holder.txtTenSP.setTextSize(20);
            }

//            holder.imgAnhSP.setImageResource(sp.getAnhSanPham());
             holder.imgAnhSP.setImageURI(sp.getAnhSanPham());

            holder.SetOnItemClickListener(new ThanhVienAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int i) {
                    opendialog(sp);
                    notifyDataSetChanged();
                }
            });



        }
    }

    private void opendialog(SanPham sp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dl_xem_thongtinchitietsanpham,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        ImageView imgAnhSanPhamCT = view.findViewById(R.id.imgAnhSanPhamCT);
        TextView txtMaSanPhamCT = view.findViewById(R.id.txtMaSanPhamCT);
        TextView txtTenSanPhamCT = view.findViewById(R.id.txtTenSanPhamCT);
        TextView txtSizeSanPhamCT = view.findViewById(R.id.txtSizeSanPhamCT);
        TextView txtGiaBanCT = view.findViewById(R.id.txtGiaBanCT);
        TextView txtTrangThaiCT = view.findViewById(R.id.txtTrangThaiCT);
        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        Button btnDelete = view.findViewById(R.id.btnDelete);
        LinearLayout llChucnang = view.findViewById(R.id.llChucnang);
        TextView btnTaoHDon = view.findViewById(R.id.btnTaoHDon);
        TextView btnTaoHDonOK = view.findViewById(R.id.btnTaoHDonOK);

        if (context.equals(currentFragment)){

            llChucnang.setVisibility(View.GONE);
            btnTaoHDon.setVisibility(View.VISIBLE);
            if (sp.getTrangThai() == 2){
                btnTaoHDon.setVisibility(View.GONE);
                btnTaoHDonOK.setVisibility(View.GONE);
            }else {
                ArrayList<TaoHoaDon> list1 = new ArrayList<>();
                list1.addAll(thdDAO.getAll());
                for (TaoHoaDon select : list1){
                    if (select.getMaSanPham().equals(sp.getMaSanPham())){
                        btnTaoHDon.setVisibility(View.GONE);
                        btnTaoHDonOK.setVisibility(View.VISIBLE);
                    }
                }
            }
            btnTaoHDon.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View view) {
                    TaoHoaDon thd = new TaoHoaDon();
                    thd.setMaSanPham(sp.getMaSanPham());
                    thd.setSoLuongMua(1);
                    thdDAO.insert(thd);
                    btnTaoHDon.setVisibility(View.GONE);
                    btnTaoHDonOK.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Đã thêm vào hóa đơn", Toast.LENGTH_SHORT).show();
                }
            });

            btnTaoHDonOK.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View view) {
                    thdDAO.delete(sp.getMaSanPham());
                    Toast.makeText(context, "Hủy thêm", Toast.LENGTH_SHORT).show();
                    btnTaoHDon.setVisibility(View.VISIBLE);
                    btnTaoHDonOK.setVisibility(View.GONE);
                    notifyDataSetChanged();
                }
            });
        }

        imgAnhSanPhamCT.setImageURI(sp.getAnhSanPham());

        txtMaSanPhamCT.setText("Mã sản phẩm: "+sp.getMaSanPham());
        txtTenSanPhamCT.setText("Tên: "+sp.getTenSanPham());
        txtSizeSanPhamCT.setText("Size: "+bgDao.getID(sp.getMaBangGia()).getSize()+" cm");
        txtGiaBanCT.setText("Giá: "+bgDao.getID(sp.getMaBangGia()).getGiaBan()+" VNĐ");

        if (sp.getTrangThai() == 0){
            txtTrangThaiCT.setText("Trạng thái: Còn hàng");
            txtTrangThaiCT.setTextColor(Color.GREEN);
        }else if (sp.getTrangThai() == 1){
            txtTrangThaiCT.setText("Trạng thái: Đặt hàng");
            txtTrangThaiCT.setTextColor(Color.BLUE);
        }else {
            txtTrangThaiCT.setText("Trạng thái: Ngừng bán");
            txtTrangThaiCT.setTextColor(Color.RED);
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Cảnh báo!");
                builder1.setMessage("Bạn có chắc chắn muốn xóa không?");
                builder1.setNegativeButton("No",null);
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (spDao.delete(sp.getMaSanPham()) != -1){
                            list.clear();
                            list.addAll(spDao.getAll());
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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frg.opendialog(sp,context,1,list);
                dialog.dismiss();
            }
        });


        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (list.size() != 0){
            return list.size();
        }
        return 0;
    }

    public static class viewholder extends RecyclerView.ViewHolder {

        private ThanhVienAdapter.OnItemClickListener listener;

        TextView txtMaSP,txtTenSP,txtGiaSP,txtTrangThaiSP;
        ImageView imgAnhSP;


        public viewholder(@NonNull View itemView) {
            super(itemView);

            imgAnhSP = itemView.findViewById(R.id.imgAnhSP);
            txtMaSP = itemView.findViewById(R.id.txtMaSP);
            txtTenSP = itemView.findViewById(R.id.txtTenSP);
            txtGiaSP = itemView.findViewById(R.id.txtGiaSP);
            txtTrangThaiSP = itemView.findViewById(R.id.txtTrangThaiSP);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int positon = getAdapterPosition();
                        if (positon != RecyclerView.NO_POSITION){
                            listener.onItemClick(positon);
                        }
                    }
                }
            });

        }
        public void SetOnItemClickListener(ThanhVienAdapter.OnItemClickListener mlistener){
            listener = mlistener;
        }
    }
}
