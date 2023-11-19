package com.example.da1_qldh_yuii.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.da1_qldh_yuii.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class fragment_banggiatheosize extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_banggiatheosize, container, false);
        RecyclerView rcvBangGiaTheoSize = view.findViewById(R.id.rcvBangGiaTheoSize);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);



        return view;
    }
}