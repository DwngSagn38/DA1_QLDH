package com.example.da1_qldh_yuii.fragment.frgSP_KH;

import static android.app.Activity.RESULT_OK;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.adapter.SanPhamAdapter;
import com.example.da1_qldh_yuii.dao.BangGiaTheoSizeDAO;
import com.example.da1_qldh_yuii.dao.SanPhamDAO;
import com.example.da1_qldh_yuii.fragment.fragment_banggiatheosize;
import com.example.da1_qldh_yuii.fragment.fragment_sanpham_khohang;
import com.example.da1_qldh_yuii.model.BangGia;
import com.example.da1_qldh_yuii.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class frgSanPham extends Fragment {

    private FloatingActionButton floatAddSanPham;
    private RecyclerView rcvSanPham;
    private Button btnTatCa,btnTheosize;
    private EditText btnTT;
    private TextView txtDanhSachSP,tvThemSp;
    private Uri selectedImageUri;
    private ImageView imgPicker;
    ArrayList<SanPham> list = new ArrayList<>();

    SanPham sanPham =new SanPham();

    private static final int REQUEST_CODE = 1;


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
        spDao = new SanPhamDAO(getContext());
        list = (ArrayList<SanPham>) spDao.getAll();

        SharedPreferences pref = getContext().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        int level = pref.getInt("LEVEL", 1);
        if (level == 0){
            floatAddSanPham.setVisibility(View.VISIBLE);
        }

        if (list.size() != 0){
            tvThemSp.setVisibility(View.GONE);
        }
        tvThemSp.setVisibility(View.VISIBLE);

        loadData(list);
        floatAddSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SanPham sanPham = new SanPham();
                opendialog(sanPham, getContext(), 0, list);
            }
        });

        btnTatCa.setBackgroundResource(R.drawable.khungdn);
        btnTatCa.setTextColor(Color.WHITE);
        btnTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData(list);
                txtDanhSachSP.setText("Danh sách sản phẩm");
                btnTatCa.setBackgroundResource(R.drawable.khungdn);
                btnTheosize.setBackgroundResource(R.drawable.khung);
                btnTatCa.setTextColor(Color.WHITE);
                btnTheosize.setTextColor(Color.BLUE);
                floatAddSanPham.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SanPham sanPham = new SanPham();
                        opendialog(sanPham, getContext(), 0, list);
                    }
                });
            }
        });

        btnTheosize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment_banggiatheosize frgBG = new fragment_banggiatheosize();
                frgBG.loadDataBG(getContext(),rcvSanPham);
                txtDanhSachSP.setText("Danh sách bảng giá");
                btnTheosize.setBackgroundResource(R.drawable.khungdn);
                btnTatCa.setBackgroundResource(R.drawable.khung);
                tvThemSp.setVisibility(View.GONE);
                btnTheosize.setTextColor(Color.WHITE);
                btnTatCa.setTextColor(Color.BLUE);
                floatAddSanPham.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        frgBG.showDialog(getContext(),rcvSanPham);
                    }
                });
            }
        });
        btnTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Chức năng đang cải thiện", Toast.LENGTH_SHORT).show();
            }
        });

//        openImagePicker();

        return view;
    }

    public void getAnhXa(View view) {
        floatAddSanPham = view.findViewById(R.id.floatAddSanPham);
        rcvSanPham = view.findViewById(R.id.rcvSanPham);
        btnTheosize = view.findViewById(R.id.btnTheosize);
        btnTT = view.findViewById(R.id.btnTT);
        btnTatCa = view.findViewById(R.id.btnTatCa);
        txtDanhSachSP = view.findViewById(R.id.txtDanhSachSP);
        tvThemSp = view.findViewById(R.id.tvThemSp);
    }

    public void loadData(ArrayList<SanPham> list) {
        rcvSanPham.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SanPhamAdapter(getContext(), list,getActivity().getSupportFragmentManager());
        rcvSanPham.setAdapter(adapter);
    }


    public void opendialog(SanPham sp, Context context, int type, ArrayList<SanPham> list1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dl_them_sanpham, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputEditText edtMaSPadd = view.findViewById(R.id.edtMaSPadd);
        EditText edtTenSPadd = view.findViewById(R.id.edtTenSPadd);
        ImageView imgAnhSanPhamAdd = view.findViewById(R.id.imgAnhSanPhamAdd);
        Spinner spnMaBangGiaadd = view.findViewById(R.id.spnMaBangGiaadd);
        RadioGroup radio_groupAdd = view.findViewById(R.id.radio_groupAdd);
        RadioButton rdoConHangAdd = view.findViewById(R.id.rdoConHangAdd);
        RadioButton rdoDatHangAdd = view.findViewById(R.id.rdoDatHangAdd);
        RadioButton rdoNgungBanAdd = view.findViewById(R.id.rdoNgungBanAdd);
        Button btnLuuAdd = view.findViewById(R.id.btnLuuAdd);
        Button btnHuyAdd = view.findViewById(R.id.btnHuyAdd);
//        Button btnChonAnh = view.findViewById(R.id.btnChonAnh);

        imgPicker = view.requireViewById(R.id.imgAnhSanPhamAdd);

        ArrayAdapter<Integer> ArrMabg = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        ArrMabg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayList<BangGia> listBG = new ArrayList<>();
        BangGiaTheoSizeDAO bgDao = new BangGiaTheoSizeDAO(context);
        listBG.addAll(bgDao.getDSBangGia());

        for (BangGia bg : listBG) {
            if (bg.getMaBangGia() != sp.getMaBangGia()) {
                ArrMabg.add(bg.getSize());
            }
        }

        spnMaBangGiaadd.setAdapter(ArrMabg);

        spnMaBangGiaadd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Integer selectedOption = (Integer) adapterView.getItemAtPosition(i);
                // Thực hiện các thao tác tùy thuộc vào tùy chọn được chọn
                for (BangGia bg : listBG) {
                    if (bg.getSize() == selectedOption) {
                        sp.setMaBangGia(bg.getMaBangGia());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (type == 1) {
//            btnChonAnh.setVisibility(View.GONE);
            // cập nhật không cho nhập mã
            edtMaSPadd.setFocusable(false);
            edtMaSPadd.setClickable(false);
            imgAnhSanPhamAdd.setClickable(false);
            imgAnhSanPhamAdd.setFocusable(false);

            edtMaSPadd.setText(sp.getMaSanPham());
            edtTenSPadd.setText(sp.getTenSanPham());
            imgAnhSanPhamAdd.setImageURI(sp.getAnhSanPham());

            if (sp.getTrangThai() == 0) {
                rdoConHangAdd.setChecked(true);
            } else if (sp.getTrangThai() == 1) {
                rdoDatHangAdd.setChecked(true);
            } else {
                rdoNgungBanAdd.setChecked(true);
            }

            // set mã bảng giá của sản phẩm hiện tại về vị trí đầu tiên
            ArrMabg.insert(bgDao.getID(sp.getMaBangGia()).getSize(), 0);
        }else {
            imgAnhSanPhamAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openImagePicker();
                }
            });
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

                String masp = edtMaSPadd.getText().toString().trim();

                String tensp = edtTenSPadd.getText().toString().trim();

                int selectedId = radio_groupAdd.getCheckedRadioButtonId(); // check radiogroup xem có cái nào đc tích không

                if (masp.isEmpty() || tensp.isEmpty() || selectedId == -1) {
                    Toast.makeText(context, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                } else {
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
                        sp.setAnhSanPham(sp.getAnhSanPham());
                        if (spDao.update(sp) != -1) {
                            Toast.makeText(context, "Đang cập Cập nhật hãy load lại danh sách", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        sp.setMaSanPham(masp);
                        sp.setAnhSanPham(sanPham.getAnhSanPham());
                        if (spDao.insert(sp) != -1) {
                            loadData(list1);
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                list1.clear();
                list1.addAll(spDao.getAll());

            }
        });
    }

    public void requestPermission(Context context) {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openImagePicker();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(context, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                TedPermission.create()
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("\n" +
                                "Nếu bạn từ chối quyền, bạn không thể sử dụng dịch vụ này\n\nVui lòng bật quyền tại [Cài đặt] > [Quyền]")
                        .setPermissions(Manifest.permission.READ_MEDIA_IMAGES)
                        .check();
            }else {
                TedPermission.create()
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("\n" +
                                "Nếu bạn từ chối quyền, bạn không thể sử dụng dịch vụ này\n\nVui lòng bật quyền tại [Cài đặt] > [Quyền]")
                        .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .check();
            }
        }


    }

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Log.e(TAG, "onActivityResult");
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data == null) {
                        return;
                    }
                    Uri uri = data.getData();
                    sanPham.setAnhSanPham(uri);
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                        imgPicker.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    // Phương thức này được gọi khi người dùng nhấn vào nút upload ảnh
    public void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncher.launch(intent);
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        activityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
//        activityResultLauncher.launch(intent.setData(selectedImageUri));
    }
}