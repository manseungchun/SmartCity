package com.example.app0907;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

public class ReportAdapter extends BaseAdapter {

    // 필드
    private Context context;
    private int layout;
    private ArrayList<ReportVO> data;
    private LayoutInflater inflater;

    public ReportAdapter(Context context, int layout, ArrayList<ReportVO> data) {
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

        TextView tvDate = view.findViewById(R.id.tvDate);
        tvDate.setText(data.get(i).getUploaddate());

        TextView tvPoint = view.findViewById(R.id.tvPoint);
        tvPoint.setText(data.get(i).getPoint_upload());

        return view;
    }
}
