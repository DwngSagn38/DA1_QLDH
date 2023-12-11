package com.example.da1_qldh_yuii.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.dao.BangGiaTheoSizeDAO;
import com.example.da1_qldh_yuii.dao.HoaDonDAO;
import com.example.da1_qldh_yuii.dao.SanPhamDAO;
import com.example.da1_qldh_yuii.dao.TaoHoaDonDAO;
import com.example.da1_qldh_yuii.model.BangGia;
import com.example.da1_qldh_yuii.model.SanPham;
import com.example.da1_qldh_yuii.model.TaoHoaDon;

import java.util.ArrayList;

public class TaoHoaDonAdapter extends RecyclerView.Adapter<TaoHoaDonAdapter.viewholder> {
    HoaDonDAO hdDAO;
    SanPhamDAO spDAO;
    TaoHoaDonDAO thdDAO;
    BangGiaTheoSizeDAO bgDAO;
    private final Context context;
    private final ArrayList<TaoHoaDon> list;

    public TaoHoaDonAdapter(Context context, ArrayList<TaoHoaDon> list) {
        this.context = context;
        this.list = list;
        hdDAO = new HoaDonDAO(context);
        spDAO = new SanPhamDAO(context);
        bgDAO = new BangGiaTheoSizeDAO(context);
        thdDAO = new TaoHoaDonDAO(context);
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_rcv_taohoadon,null);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        if (position >= 0 && position < list.size()){

            TaoHoaDon thd = list.get(position);
            SanPham sp = spDAO.getID(thd.getMaSanPham());
            BangGia bg = bgDAO.getID(sp.getMaBangGia());

            holder.imgAnhSP.setImageURI(sp.getAnhSanPham());

            holder.tvTenSP.setText("Tên: "+sp.getTenSanPham());
            holder.tvGiaSP.setText("Giá: "+bg.getGiaBan()+" VNĐ");

            holder.edtSoLuongMua.setText(thd.getSoLuongMua()+"");

            holder.imgDown.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View view) {
                    if (thd.getSoLuongMua() >= 1){
                        thd.setSoLuongMua(thd.getSoLuongMua() - 1);
                        holder.edtSoLuongMua.setText(thd.getSoLuongMua()+"");
                        thdDAO.update(thd);
                        notifyDataSetChanged();
                    }else {
                        holder.edtSoLuongMua.setText(0+"");
                    }
                }
            });
            holder.imgUp.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View view) {
                    thd.setSoLuongMua(thd.getSoLuongMua()+1);
                    holder.edtSoLuongMua.setText(thd.getSoLuongMua()+"");
                    thdDAO.update(thd);
                    notifyDataSetChanged();
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if (list.size() != 0){
            return list.size();
        }
        return 0;
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        ImageView imgAnhSP,imgDown,imgUp;
        TextView tvTenSP,tvGiaSP;
        TextView edtSoLuongMua;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            imgAnhSP = itemView.findViewById(R.id.imgAnhSP);
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvGiaSP = itemView.findViewById(R.id.tvGiaSP);
            edtSoLuongMua = itemView.findViewById(R.id.edtSoLuongMua);
            imgDown = itemView.findViewById(R.id.imgDown);
            imgUp = itemView.findViewById(R.id.imgUp);
        }
    }
}
