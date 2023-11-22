package com.example.da1_qldh_yuii.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.dao.ThanhVienDAO;
import com.example.da1_qldh_yuii.model.ThanhVien;
import com.google.android.material.textfield.TextInputEditText;

public class fragment_doimatkhau extends Fragment {

    TextInputEditText edPassOld,edPassChange,edRePassChange;

    Button btnDoiMatKhau;

    ThanhVienDAO tvDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_doimatkhau, container, false);

        // anh xa
        tvDao = new ThanhVienDAO(getActivity());
        edPassOld = view.findViewById(R.id.edPassOld);
        edPassChange = view.findViewById(R.id.edPassChange);
        edRePassChange = view.findViewById(R.id.edRePassChange);
        btnDoiMatKhau = view.findViewById(R.id.btnDoiMatKhau);


        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user = pref.getString("USERNAME","");
                if (validate() > 0) {
                    ThanhVien thanhVien = tvDao.getID(user);
                    thanhVien.setMatKhau(edPassChange.getText().toString());

                    if (tvDao.update(thanhVien) > 0) {
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        edPassOld.setText("");
                        edPassChange.setText("");
                        edRePassChange.setText("");
                    } else {
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;

    }

    public int validate(){
        int check =1;
        if (edPassOld.getText().length() == 0 || edPassChange.getText().length() == 0 || edRePassChange.getText().length() == 0){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passOld = pref.getString("PASSWORD","");
            String pass = edPassChange.getText().toString();
            String rePass = edRePassChange.getText().toString();
            if (!passOld.equals(edPassOld.getText().toString())){
                Toast.makeText(getContext(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!pass.equals(rePass)){
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }

        return check;
    }
}