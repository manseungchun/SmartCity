package com.example.app0907;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class Fragment7 extends Fragment {

    // 데이터 관리할 공간
    ArrayList<MoreVO> data;
    ListView lv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_7, container, false);


        data = new ArrayList<>();
        lv = view.findViewById(R.id.mvlv);

        data.add(new MoreVO("2022-10-04","접수중","광주광역시"));

        MoreAdapter adapter = new MoreAdapter(view.getContext(), R.layout.more, data);

        lv.setAdapter(adapter);

        return view;

    }


}
