package com.example.app0907;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MoreAdapter extends BaseAdapter {

    // 필드
    private Context context;
    private int layout;
    private ArrayList<MoreVO> data;
    private LayoutInflater inflater;
    private Bitmap bitmap;

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

        Thread uThread = new Thread() {
            @Override
            public void run() {
                try {
                    // 이미지 URL 경로
                    URL img_url = new URL("http://222.102.104.237:5000/static/" + data.get(i).getImg());

                    // web에서 이미지를 가져와 ImageView에 저장할 Bitmap을 만든다.
                    HttpURLConnection conn = (HttpURLConnection) img_url.openConnection();
                    conn.setDoInput(true); // 서버로부터 응답 수신
                    conn.connect(); //연결된 곳에 접속할 때 (connect() 호출해야 실제 통신 가능함)
                    InputStream is = conn.getInputStream(); //inputStream 값 가져오기
                    bitmap = BitmapFactory.decodeStream(is); // Bitmap으로 변환
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        uThread.start(); // 작업 Thread 실행

        try {
            //메인 Thread는 별도의 작업 Thread가 작업을 완료할 때까지 대기해야 한다.
            //join() 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다리도록 한다.
            //join() 메서드는 InterruptedException을 발생시킨다.
            uThread.join();

            //작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
            //UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지 지정
            ImageView ivImg = view.findViewById(R.id.ivImg);
            ivImg.setImageBitmap(bitmap);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return view;
    }



}
