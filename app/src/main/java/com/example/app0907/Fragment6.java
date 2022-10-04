package com.example.app0907;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class Fragment6 extends Fragment {

    // 데이터 관리할 공간
    ArrayList<MileageVO> data;
    ListView lv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_6, container, false);

        data = new ArrayList<>();
        lv = view.findViewById(R.id.lv6);

        data.add(new MileageVO("2022-10-04","-100"));

        MileageAdapter adapter = new MileageAdapter(view.getContext(), R.layout.mileagelv, data);

        lv.setAdapter(adapter);

        return view;

    }


}
