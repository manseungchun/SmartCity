package com.example.app0907;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class More extends AppCompatActivity {

    // 데이터 관리할 공간
    ArrayList<MoreVO> data;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);


        data = new ArrayList<>();
        lv = findViewById(R.id.mvlv);

        data.add(new MoreVO("2022-10-04","접수중","광주광역시"));

        MoreAdapter adapter = new MoreAdapter(More.this, R.layout.more, data);

        lv.setAdapter(adapter);

    }
}