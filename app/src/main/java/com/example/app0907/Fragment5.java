package com.example.app0907;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class Fragment5 extends Fragment {

    // 데이터 관리할 공간
    ArrayList<ReportVO> data;
    ListView lv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_5, container, false);

        data = new ArrayList<>();
        lv = view.findViewById(R.id.lv5);

        data.add(new ReportVO("2022-10-04","100"));

        ReportAdapter adapter = new ReportAdapter(view.getContext(), R.layout.reportlv, data);

        lv.setAdapter(adapter);

        return view;
//주석테스트

    }


}
