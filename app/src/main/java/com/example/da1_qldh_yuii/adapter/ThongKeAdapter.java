package com.example.da1_qldh_yuii.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.dao.SanPhamDAO;
import com.example.da1_qldh_yuii.dao.ThongKeDAO;
import com.example.da1_qldh_yuii.model.SanPham;

import java.util.ArrayList;

public class ThongKeAdapter extends RecyclerView.Adapter<ThongKeAdapter.viewholder> {

    SanPhamDAO spDao;
    ThongKeDAO tkDao;
    private final Context context;
    private final ArrayList<SanPham> list;

    public ThongKeAdapter(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
        tkDao = new ThongKeDAO(context);
        spDao = new SanPhamDAO(context);
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_thongke,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        if (position >= 0 && position < list.size()){
            SanPham sp= list.get(position);
            SanPham spgoc = spDao.getID(sp.getMaSanPham());

            holder.imgAnhSPTK.setImageURI(spgoc.getAnhSanPham());
            holder.txtMaSP.setText("Mã : "+spgoc.getMaSanPham());
            holder.txtTenSP.setText("Tên : "+spgoc.getTenSanPham());
            holder.txtSLDon.setText("Số lượng đơn : "+sp.getSoLuongDon());
            holder.txtSLMua.setText("Số lượng bán: "+sp.getSoLuongMua());
        }

    }

    @Override
    public int getItemCount() {
        if (list.size() != 0){
            return  list.size();
        }
        return 0;
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        ImageView imgAnhSPTK;
        TextView txtMaSP,txtTenSP,txtSLDon,txtSLMua;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtSLMua = itemView.findViewById(R.id.txtSLMua);
            txtSLDon = itemView.findViewById(R.id.txtSLDon);
            txtTenSP = itemView.findViewById(R.id.txtTenSP);
            txtMaSP = itemView.findViewById(R.id.txtMaSP);
            imgAnhSPTK = itemView.findViewById(R.id.imgAnhSPTK);
        }
    }
}
