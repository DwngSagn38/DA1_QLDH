package com.example.da1_qldh_yuii.fragment.frgSP_KH;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.adapter.SanPhamAdapter;
import com.example.da1_qldh_yuii.dao.BangGiaTheoSizeDAO;
import com.example.da1_qldh_yuii.dao.SanPhamDAO;
import com.example.da1_qldh_yuii.model.BangGia;
import com.example.da1_qldh_yuii.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class frgSanPham extends Fragment {

    private FloatingActionButton floatAddSanPham;
    private RecyclerView rcvSanPham;
    private Button btnTatCa;
    private EditText btnTheosize, btnTT;
    ArrayList<SanPham> list = new ArrayList<>();


    SanPhamAdapter adapter;
    SanPhamDAO spDao;

    public frgSanPham() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_san_pham, container, false);

        getAnhXa(view);

        loadData(list);
        floatAddSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SanPham sanPham = new SanPham();
                opendialog(sanPham, getContext(), 0, list);
            }
        });
        btnTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData(list);
                Toast.makeText(getContext(), "Dữ liệu đã được làm mới", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void getAnhXa(View view) {
        floatAddSanPham = view.findViewById(R.id.floatAddSanPham);
        rcvSanPham = view.findViewById(R.id.rcvSanPham);
        btnTheosize = view.findViewById(R.id.btnTheosize);
        btnTT = view.findViewById(R.id.btnTT);
        btnTatCa = view.findViewById(R.id.btnTatCa);
    }

    public void loadData(ArrayList<SanPham> list) {
        spDao = new SanPhamDAO(getContext());
        list = (ArrayList<SanPham>) spDao.getAll();
        rcvSanPham.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SanPhamAdapter(getContext(), list);
        rcvSanPham.setAdapter(adapter);
    }

    public void opendialog(SanPham sp, Context context, int type, ArrayList<SanPham> list1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dl_them_sanpham, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edtMaSPadd = view.findViewById(R.id.edtMaSPadd);
        EditText edtTenSPadd = view.findViewById(R.id.edtTenSPadd);
        ImageView imgAnhSanPhamAdd = view.findViewById(R.id.imgAnhSanPhamAdd);
        Spinner spnMaBangGiaadd = view.findViewById(R.id.spnMaBangGiaadd);
        RadioGroup radio_groupAdd = view.findViewById(R.id.radio_groupAdd);
        RadioButton rdoConHangAdd = view.findViewById(R.id.rdoConHangAdd);
        RadioButton rdoDatHangAdd = view.findViewById(R.id.rdoDatHangAdd);
        RadioButton rdoNgungBanAdd = view.findViewById(R.id.rdoNgungBanAdd);
        Button btnLuuAdd = view.findViewById(R.id.btnLuuAdd);
        Button btnHuyAdd = view.findViewById(R.id.btnHuyAdd);


        ArrayAdapter<Integer> ArrMabg = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        ArrMabg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayList<BangGia> listBG = new ArrayList<>();
        BangGiaTheoSizeDAO bgDao = new BangGiaTheoSizeDAO(context);
        listBG.addAll(bgDao.getDSBangGia());

        for (BangGia bg : listBG) {
            if (bg.getMaBangGia() != sp.getMaBangGia()) {
                ArrMabg.add(bg.getMaBangGia());
            }
        }

        spnMaBangGiaadd.setAdapter(ArrMabg);

        spnMaBangGiaadd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Integer selectedOption = (Integer) adapterView.getItemAtPosition(i);
                // Thực hiện các thao tác tùy thuộc vào tùy chọn được chọn
                for (BangGia bg : listBG) {
                    if (bg.getMaBangGia() == selectedOption) {
                        sp.setMaBangGia(bg.getMaBangGia());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (type == 1) {
            // cập nhật không cho nhập mã
            edtMaSPadd.setFocusable(false);
            edtMaSPadd.setClickable(false);

            imgAnhSanPhamAdd.setImageResource(R.drawable.sp_new2);
            edtMaSPadd.setText(sp.getMaSanPham());
            edtTenSPadd.setText(sp.getTenSanPham());

            if (sp.getTrangThai() == 0) {
                rdoConHangAdd.setChecked(true);
            } else if (sp.getTrangThai() == 1) {
                rdoDatHangAdd.setChecked(true);
            } else {
                rdoNgungBanAdd.setChecked(true);
            }

            // set mã bảng giá của sản phẩm hiện tại về vị trí đầu tiên
            ArrMabg.insert(sp.getMaBangGia(), 0);

        }

        btnHuyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnLuuAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int anhsp = 1;
                String masp = edtMaSPadd.getText().toString().trim();

                String tensp = edtTenSPadd.getText().toString().trim();

                int selectedId = radio_groupAdd.getCheckedRadioButtonId(); // check radiogroup xem có cái nào đc tích không

                if (masp.isEmpty() || tensp.isEmpty() || selectedId == -1) {
                    Toast.makeText(context, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                } else {
                    sp.setAnhSanPham(anhsp);
                    sp.setMaSanPham(masp);
                    sp.setTenSanPham(tensp);
                    if (rdoConHangAdd.isChecked()) {
                        sp.setTrangThai(0);
                    } else if (rdoDatHangAdd.isChecked()) {
                        sp.setTrangThai(1);
                    } else {
                        sp.setTrangThai(2);
                    }

                    if (type == 1) {
                        spDao = new SanPhamDAO(context);
                        if (spDao.update(sp) != -1) {
                            list1.clear();
                            list1.addAll(spDao.getAll());
                            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (spDao.insert(sp) != -1) {
                            loadData(list1);
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            }
        });


    }


}
