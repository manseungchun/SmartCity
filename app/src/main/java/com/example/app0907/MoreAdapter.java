package com.example.app0907;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MoreAdapter extends BaseAdapter {

    // 필드
    private Context context;
    private int layout;
    private ArrayList<MoreVO> data;
    private LayoutInflater inflater;

    public MoreAdapter(Context context, int layout, ArrayList<MoreVO> data) {
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

        TextView date = view.findViewById(R.id.date);
        date.setText(data.get(i).getUploaddate());

        TextView realddr = view.findViewById(R.id.realaddr);
        realddr.setText(data.get(i).getSpot());

        TextView id = view.findViewById(R.id.id);
        id.setText(data.get(i).getId());

        TextView detail = view.findViewById(R.id.detail);
        detail.setText(data.get(i).getDetail());

        TextView order = view.findViewById(R.id.order);
        order.setText(data.get(i).getReceipt());

        return view;
    }



}
