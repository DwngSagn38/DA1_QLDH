package com.example.da1_qldh_yuii.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.dao.ThanhVienDAO;
import com.example.da1_qldh_yuii.fragment.fragment_thongbao;
import com.example.da1_qldh_yuii.model.ThanhVien;
import com.example.da1_qldh_yuii.model.ThongBao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ThongBaoAdapter extends ArrayAdapter<ThongBao> {

    private Context context;
    fragment_thongbao fragment;
    private ArrayList<ThongBao> list;

    ThanhVienDAO tvDao;

    TextView txtTieuDe,txtNoiDung,txtHoTenThanhVien,txtNgay;


    ImageView imgDeleteThongBao;

    public ThongBaoAdapter(@NonNull Context context, fragment_thongbao fragment, ArrayList<ThongBao> list) {
        super(context, 0,list);
        this.context = context;
        this.list = list;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_rcv_thongbao,parent,false);
        }

        final ThongBao item = list.get(position);
        if (item != null){
            txtTieuDe = view.findViewById(R.id.txtTieuDe);
            txtTieuDe.setText(item.getTieuDe());
            txtNoiDung = view.findViewById(R.id.txtNoiDung);
            txtNoiDung.setText(item.getNoiDung());

            txtHoTenThanhVien = view.findViewById(R.id.txtHoTenThanhVien);
            txtHoTenThanhVien.setText("by"+ item.getMaThanhVien());
            txtNgay=view.findViewById(R.id.txtNgay);
            txtNgay.setText("Ngày: " + item.getNgayDang());
            imgDeleteThongBao = view.findViewById(R.id.imgDeleteThongBao);
        }

        imgDeleteThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.xoa(String.valueOf(item.getMaThongBao()));
            }
        });
        return view;
    }
}
