package com.example.da1_qldh_yuii.fragment;

import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.da1_qldh_yuii.DangNhap;
import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.adapter.ThanhVienAdapter;
import com.example.da1_qldh_yuii.dao.ThanhVienDAO;
import com.example.da1_qldh_yuii.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;


public class fragment_thanhvien extends Fragment {

    private RecyclerView rcvThanhVien;
    private FloatingActionButton floatAddThanhVien;

    private ThanhVienAdapter adapter;

    private ThanhVienDAO tvDAO;
    DangNhap dangNhap = new DangNhap();

    ArrayList<ThanhVien> list = new ArrayList<>();

    private SearchView searchView;


    public fragment_thanhvien() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               handleSearch(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thanhvien, container, false);
        rcvThanhVien = view.findViewById(R.id.rcvThanhVien);
        floatAddThanhVien = view.findViewById(R.id.floatAddThanhVien);
        tvDAO = new ThanhVienDAO(getContext());

        loadData();

        floatAddThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangNhap.openDialog(getContext(),1,list);
                loadData();
            }
        });

        return view;
    }

    public void loadData() {
        list.clear();
        list.addAll(tvDAO.getAll());
        rcvThanhVien.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ThanhVienAdapter(getContext(), list);
        rcvThanhVien.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private String removeDau(String str) {
        String strKhongDau = Normalizer.normalize(str, Normalizer.Form.NFD);
        return strKhongDau.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
    private void handleSearch(String query) {
        List<ThanhVien> listSearch = new ArrayList<>();
        String tenTimKhongDau = removeDau(query.toLowerCase());
        for (ThanhVien tv : list) {
            String tenKhongDau = removeDau(tv.getTenThanhVien().toLowerCase());
            if (tenKhongDau.contains(tenTimKhongDau)) {
                listSearch.add(tv);
            }
        }
        adapter = new ThanhVienAdapter(getActivity(), (ArrayList<ThanhVien>) listSearch);
        rcvThanhVien.setAdapter(adapter);

    }


}