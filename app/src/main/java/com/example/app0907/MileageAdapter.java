package com.example.app0907;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MileageAdapter extends BaseAdapter {

    // 필드
    private Context context;
    private int layout;
    private ArrayList<MileageVO> data;
    private LayoutInflater inflater;

    public MileageAdapter(Context context, int layout, ArrayList<MileageVO> data) {
        this.context = context;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(layout,viewGroup,false);
        }

        TextView tvMiledate = view.findViewById(R.id.tvMiledate);
        tvMiledate.setText(data.get(i).getPointdate());

        TextView tvMilie = view.findViewById(R.id.tvMile);
        tvMilie.setText(data.get(i).getPoint());


        return view;
    }
}
