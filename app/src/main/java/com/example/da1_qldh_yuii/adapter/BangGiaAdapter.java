package com.example.da1_qldh_yuii.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

        // Lấy thông tin người dùng từ SharedPreferences
        SharedPreferences pref = context.getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        int level = pref.getInt("LEVEL", 1);

        if (level == 1){
            holder.imgDeleteBangGia.setVisibility(View.GONE);
            holder.imgEditBangGia.setVisibility(View.GONE);
        }

        holder.txtMaBangGia.setText("Mã bảng giá: " + list.get(position).getMaBangGia());
        holder.txtSize.setText("Size: " + list.get(position).getSize());
        holder.txtGiaBan.setText("Giá bán: " + list.get(position).getGiaBan());


        //edit banggia
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
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo !");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
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
                builder.setNegativeButton("No",null);
                builder.show();
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

        TextView edtMaBangGiaSua = view.findViewById(R.id.edtMaBangGiaSua);
        EditText edtSizeSua = view.findViewById(R.id.edtSizeSua);
        EditText edtGiaBanSua = view.findViewById(R.id.edtGiaBanSua);


        edtMaBangGiaSua.setText(String.valueOf(bangGia.getMaBangGia()));
        edtSizeSua.setText(String.valueOf(bangGia.getSize()));
        edtGiaBanSua.setText(String.valueOf(bangGia.getGiaBan()));

        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String checkSize = edtSizeSua.getText().toString().trim();
                String checkGiaban = edtGiaBanSua.getText().toString().trim();

                if (isInt(checkSize) && isDouble(checkGiaban)) {
                    //lấy giá trị từ edittext

                    int sizeBG = Integer.parseInt(edtSizeSua.getText().toString());
                    double giaBanBG = Double.parseDouble(edtGiaBanSua.getText().toString());
                    bangGia.setSize(sizeBG);
                    bangGia.setGiaBan(giaBanBG);

                    boolean check = bangGiaTheoSizeDAO.capNhatBangGia(bangGia);
                    if (check){
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        //loadData
                        loadData();

                    }else{
                        Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context, "Size hoặc giá bán chưa hợp lệ", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setNegativeButton("Hủy",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void loadData(){
        list.clear();
        list = bangGiaTheoSizeDAO.getDSBangGia();
        notifyDataSetChanged();

    }

    public boolean isInt(String so){
        return so.matches("\\d++");
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
