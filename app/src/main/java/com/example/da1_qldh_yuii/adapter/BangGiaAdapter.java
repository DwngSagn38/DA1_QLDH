package com.example.da1_qldh_yuii.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.dao.BangGiaTheoSizeDAO;
import com.example.da1_qldh_yuii.model.BangGia;

import java.util.ArrayList;

public class BangGiaAdapter extends RecyclerView.Adapter<BangGiaAdapter.ViewHolder> {

    private Context context;
    private ArrayList<BangGia> list;
    private BangGiaTheoSizeDAO bangGiaTheoSizeDAO;

    public BangGiaAdapter(Context context, ArrayList<BangGia> list, BangGiaTheoSizeDAO bangGiaTheoSizeDAO) {
        this.context = context;
        this.list = list;
        this.bangGiaTheoSizeDAO = bangGiaTheoSizeDAO;
    }

    @NonNull
    @Override
    public BangGiaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_rcv_banggiatheosize, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BangGiaAdapter.ViewHolder holder, int position) {

        holder.txtMaBangGia.setText("Mã bảng giá: " + list.get(position).getMaBangGia());
        holder.txtSize.setText("Size: " + list.get(position).getSize());
        holder.txtGiaBan.setText("Giá bán: " + list.get(position).getGiaBan());


        //edit
        holder.imgEditBangGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCapNhatTT(list.get(holder.getAdapterPosition()));
            }
        });


        //delete
        holder.imgDeleteBangGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = bangGiaTheoSizeDAO.xoaBangGia(list.get(holder.getAdapterPosition()).getMaBangGia());
                if (check == 1) {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                } else if (check == 0) {
                    Toast.makeText(context, "Bảng giá không tồn tại", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMaBangGia, txtSize, txtGiaBan;
        ImageView imgEditBangGia, imgDeleteBangGia;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            txtMaBangGia = itemView.findViewById(R.id.txtMaBangGia);
            txtSize = itemView.findViewById(R.id.txtSize);
            txtGiaBan = itemView.findViewById(R.id.txtGiaBan);

            imgEditBangGia = itemView.findViewById(R.id.imgEditBangGia);
            imgDeleteBangGia = itemView.findViewById(R.id.imgDeleteBangGia);





        }
    }

    private void showDialogCapNhatTT(BangGia bangGia){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dl_sua_banggiatheosize, null);
        builder.setView(view);

        EditText edtMaBangGiaSua = view.findViewById(R.id.edtMaBangGiaSua);
        EditText edtSizeSua = view.findViewById(R.id.edtSizeSua);
        EditText edtGiaBanSua = view.findViewById(R.id.edtGiaBanSua);


        edtMaBangGiaSua.setText(String.valueOf(bangGia.getMaBangGia()));
        edtSizeSua.setText(String.valueOf(bangGia.getSize()));
        edtGiaBanSua.setText(String.valueOf(bangGia.getGiaBan()));

        builder.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lấy giá trị từ edittext

                int maBG = Integer.parseInt(edtMaBangGiaSua.getText().toString());
                int sizeBG = Integer.parseInt(edtSizeSua.getText().toString());
                double giaBanBG = Double.parseDouble(edtGiaBanSua.getText().toString());

                boolean check = bangGiaTheoSizeDAO.capNhatBangGia(maBG, sizeBG, giaBanBG);
                if (check){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    //loadData
                    loadData();

                }else{
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void loadData(){
        list.clear();
        list = bangGiaTheoSizeDAO.getDSBangGia();
        notifyDataSetChanged();

    }



}
