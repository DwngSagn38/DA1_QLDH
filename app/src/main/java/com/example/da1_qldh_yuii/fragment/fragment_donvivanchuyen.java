package com.example.da1_qldh_yuii.fragment;

import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.adapter.KhachHangAdapter;
import com.example.da1_qldh_yuii.adapter.VanChuyenAdapter;
import com.example.da1_qldh_yuii.dao.VanChuyenDAO;
import com.example.da1_qldh_yuii.model.KhachHang;
import com.example.da1_qldh_yuii.model.VanChuyen;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;


public class fragment_donvivanchuyen extends Fragment {


    VanChuyen vc;
    VanChuyenDAO vcDao;
    VanChuyenAdapter adapter;
    ArrayList<VanChuyen> list = new ArrayList<>();
    private RecyclerView rcvDonViVanChuyen;
    LinearLayout llDVVCNgung,llDVVCds,llDVVCadd;
    ImageView imgback;

    private SearchView searchView;


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
                rcvDonViVanChuyen.setVisibility(View.VISIBLE);
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
        View view = inflater.inflate(R.layout.fragment_donvivanchuyen, container, false);
        rcvDonViVanChuyen = view.findViewById(R.id.rcvDonViVanChuyen);
        llDVVCNgung = view.findViewById(R.id.llDVVCNgung);
        llDVVCds = view.findViewById(R.id.llDVVCds);
        llDVVCadd = view.findViewById(R.id.llDVVCadd);
        imgback = view.findViewById(R.id.imgback);

        SharedPreferences pref = getContext().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        int level = pref.getInt("LEVEL", 1);
        if (level == 1){
            llDVVCadd.setVisibility(View.GONE);
            llDVVCNgung.setVisibility(View.GONE);
            llDVVCds.setVisibility(View.GONE);
            rcvDonViVanChuyen.setVisibility(View.VISIBLE);
        }

        vcDao = new VanChuyenDAO(getContext());
        list.addAll(vcDao.getAll());
        loadData(list);
        llDVVCds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData(list);
                loadds(llDVVCds,llDVVCadd,llDVVCNgung);
            }
        });

        llDVVCNgung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<VanChuyen> list1 = new ArrayList<>();
                for (VanChuyen vc : list){
                    if (vc.getTrangThai() == 1){
                        list1.add(vc);
                    }
                }
                loadData(list1);
                loadds(llDVVCNgung,llDVVCds,llDVVCadd);
            }
        });

        llDVVCadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vc = new VanChuyen();
                adapter = new VanChuyenAdapter(getContext(),list);
                adapter.opendialog(vc,requireActivity(),0,list);
            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void loadData(ArrayList<VanChuyen> list){
//        ArrayList<VanChuyen> list1 = new ArrayList<>(list);
        rcvDonViVanChuyen.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new VanChuyenAdapter(getContext(),list);
        rcvDonViVanChuyen.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void back(){
        llDVVCadd.setBackgroundResource(R.drawable.bg_bottom);
        llDVVCds.setBackgroundResource(R.drawable.bg_bottom);
        llDVVCNgung.setBackgroundResource(R.drawable.bg_bottom);
        llDVVCds.setVisibility(View.VISIBLE);
        llDVVCadd.setVisibility(View.VISIBLE);
        llDVVCNgung.setVisibility(View.VISIBLE);
        imgback.setVisibility(View.GONE);
        rcvDonViVanChuyen.setVisibility(View.GONE);
    }

    public void loadds(LinearLayout  lo1, LinearLayout lo2, LinearLayout lo3){
        lo1.setBackgroundResource(R.drawable.khungdn);
        lo2.setVisibility(View.GONE);
        lo3.setVisibility(View.GONE);
        imgback.setVisibility(View.VISIBLE);
        rcvDonViVanChuyen.setVisibility(View.VISIBLE);
    }

    private String removeDau(String str) {
        String strKhongDau = Normalizer.normalize(str, Normalizer.Form.NFD);
        return strKhongDau.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
    private void handleSearch(String query) {
        List<VanChuyen> listSearch = new ArrayList<>();
        String tenTimKhongDau = removeDau(query.toLowerCase());
        for (VanChuyen vc : list) {
            String tenKhongDau = removeDau(vc.getTenVanChuyen().toLowerCase());
            if (tenKhongDau.contains(tenTimKhongDau)) {
                listSearch.add(vc);
            }
        }
        adapter = new VanChuyenAdapter(getActivity(), (ArrayList<VanChuyen>) listSearch);
        rcvDonViVanChuyen.setAdapter(adapter);

    }


}