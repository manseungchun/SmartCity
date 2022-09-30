package com.example.app0907;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class sliding extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String [] question = {"로그인 어떻게 하나요?", "사진", "균열", "크랙", "박리", "마일리지"};
    String [] answer1 = {"로그인","회원가입"};
    String [] answer2 = {"카메라 촬영", "갤러리 선택"};

    TextView answer, answer_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding);

        answer = findViewById(R.id.answer);
        answer_ = findViewById(R.id.answer1);

        ListView listView = findViewById(R.id.listView);
//        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
//                new String[] {"위치는 어떻게 ~~", "잔", "짜", "잔", "짜", "잔"}));
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, question));
        listView.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String question = this.question[i];
        answer.setText(question);

    }
}