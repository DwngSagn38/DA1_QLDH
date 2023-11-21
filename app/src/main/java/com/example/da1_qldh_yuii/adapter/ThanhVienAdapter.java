package com.example.da1_qldh_yuii.adapter;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_qldh_yuii.DangNhap;
import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.dao.ThanhVienDAO;
import com.example.da1_qldh_yuii.model.ThanhVien;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.viewholder> {

    private ThanhVienDAO tvDAO;
    private final Context context;
    private final ArrayList<ThanhVien> list;

    // OnClickItem : B1 :  tạo một interface để xử lý sự kiện khi người dùng chọn
    public interface OnItemClickListener {
        void onItemClick(int i);
    }




    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list) {
        this.context = context;
        this.list = list;
        tvDAO = new ThanhVienDAO(context);
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_rcv_thanhvien,null);

        // 5.  Khai báo và truyền đối tượng OnItemClickListener vào ViewHolder
        viewholder vholder = new viewholder(view);
        vholder.setOnItemClickListener(vholder.listener);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        if (position >= 0 && position < list.size()){
            ThanhVien tv = list.get(position);

            if (tv.getPhanQuyen() == 0){
                holder.imgAnh.setImageResource(R.drawable.logo_yuii);
            }else {
                holder.imgAnh.setImageResource(R.drawable.ic_person);
            }

            if (tv.getPhanQuyen() == 0){
                holder.txtPhanQuyen.setText("Quản lý");
            }else {
                holder.txtPhanQuyen.setText("Nhân viên");
            }
            holder.txtTenThanhVien.setText(tv.getTenThanhVien());
            holder.txtSoDienThoai.setText(tv.getSoDienThoai());

            // 6. longClickListener là một đối tượng triển khai interface OnItemLongClickListener, và nó được truyền vào Adapter. Khi sự kiện long click xảy ra trong Adapter, nó sẽ thông báo về cho longClickListener để xử lý tương ứng.
            holder.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int i) {
                    opendialog(tv);
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
        // B2 : khai báo một biến thành viên để lưu trữ đối tượng OnItemClickListener
        private OnItemClickListener listener;

        private TextView txtPhanQuyen,txtTenThanhVien,txtSoDienThoai;
        private ImageView imgAnh;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtSoDienThoai = itemView.findViewById(R.id.txtSoDienThoai);
            txtTenThanhVien = itemView.findViewById(R.id.txtTenThanhVien);
            txtPhanQuyen = itemView.findViewById(R.id.txtPhanQuyen);
            imgAnh = itemView.findViewById(R.id.imgAnh);

            // 3 : thiết lập setOnLongClickListener cho itemView.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

        //  4. Thêm phương thức setOnClickListener để truyền đối tượng OnItemClickListener từ Adapter vào ViewHolder
        public void setOnItemClickListener(OnItemClickListener mlistener) {
            listener = mlistener;
        }
    }

    public void opendialog(ThanhVien tv) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dl_xem_thongtinchitietthanhvien, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        ImageView imgAnhThanhVien = view.findViewById(R.id.imgAnhThanhVien);
        TextView txtMaThanhVien = view.findViewById(R.id.txtMaThanhVien);
        TextView txtHoVaTen = view.findViewById(R.id.txtHoVaTen);
        TextView txtSoDienThoai = view.findViewById(R.id.txtSoDienThoai);
        TextView txtMatKhau = view.findViewById(R.id.txtMatKhau);
        TextView txtChucVu = view.findViewById(R.id.txtChucVu);
        Button btnUpdateTV = view.findViewById(R.id.btnUpdateTV);
        Button btnDeleteTV = view.findViewById(R.id.btnDeleteTV);

        if (tv.getPhanQuyen() == 0){
            imgAnhThanhVien.setImageResource(R.drawable.logo_yuii);
        }else {
            imgAnhThanhVien.setImageResource(R.drawable.ic_person);
        }
        txtMaThanhVien.setText("Mã thành viên: "+tv.getMaThanhVien());
        txtHoVaTen.setText("Họ và tên: "+tv.getTenThanhVien());
        txtSoDienThoai.setText("Số điện thoại: "+tv.getSoDienThoai());
        txtMatKhau.setText("Mật khẩu: "+tv.getMatKhau());
        if (tv.getPhanQuyen() == 0){
            txtChucVu.setText("Chức vụ: Quản lý");
        }else {
            txtChucVu.setText("Chức vụ: Nhân viên");
        }

        btnDeleteTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Cảnh báo!");
                builder1.setMessage("Bạn có chắc chắn muốn xóa không?");
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(tvDAO.delete(tv.getMaThanhVien()) == 1){
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder1.setNegativeButton("No",null);
                builder1.show();
            }
        });
        btnUpdateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUpdate(tv);
                dialog.dismiss();
            }
        });
    }

    public void dialogUpdate(ThanhVien tv){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dl_sua_thanhvien, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputEditText txtMaThanhVienAdd = view.findViewById(R.id.txtMaThanhVienAdd);
        TextInputEditText txtHoTenAdd = view.findViewById(R.id.txtHoTenAdd);
        TextInputEditText txtSoDienThoaiAdd = view.findViewById(R.id.txtSoDienThoaiAdd);
        TextInputEditText txtMatKhauAdd = view.findViewById(R.id.txtMatKhauAdd);
        Spinner spinnerOptions = view.findViewById(R.id.spnChucVu);
        Button btnLuu = view.findViewById(R.id.btnLuu);

        txtMaThanhVienAdd.setText(tv.getMaThanhVien());
        txtHoTenAdd.setText(tv.getTenThanhVien());
        txtSoDienThoaiAdd.setText(tv.getSoDienThoai());
        txtMatKhauAdd.setText(tv.getMatKhau());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (tv.getPhanQuyen() == 1){
            adapter.add("Nhân Viên");
            adapter.add("Quản Lý");
        }else {
            adapter.add("Quản Lý");
            adapter.add("Nhân Viên");
        }
        //nnn

        spinnerOptions.setAdapter(adapter);

        spinnerOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = (String) parent.getItemAtPosition(position);
                // Thực hiện các thao tác tùy thuộc vào tùy chọn được chọn
                if (selectedOption.equals("Nhân Viên")) {
                    tv.setPhanQuyen(1);
                } else if (selectedOption.equals("Quản Lý")) {
                    tv.setPhanQuyen(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có tùy chọn nào được chọn

            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = txtMaThanhVienAdd.getText().toString();
                String hoten = txtHoTenAdd.getText().toString();
                String sdt = txtSoDienThoaiAdd.getText().toString();
                String pass = txtMatKhauAdd.getText().toString();


                if (user.trim().isEmpty() || hoten.trim().isEmpty() || sdt.trim().isEmpty() || pass.trim().isEmpty()) {
                    Toast.makeText(context, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                }else if (!validateSDT(sdt) || sdt.length() < 10){
                    Toast.makeText(context, "Số điện thoại chưa đúng", Toast.LENGTH_SHORT).show();
                } else {
                    tv.setMaThanhVien(user);
                    tv.setTenThanhVien(hoten);
                    tv.setSoDienThoai(sdt);
                    tv.setMatKhau(pass);
                    if (tvDAO.update(tv) == 1){
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public boolean validateSDT(String sdt){
        return sdt.matches("\\d++");
    }
}
