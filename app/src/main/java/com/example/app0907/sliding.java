package com.example.app0907;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class sliding extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String [] question = {"메뉴얼", "사진", "균열", "크랙", "박리", "마일리지"};
    String [] answer1 = {"어떻게 이 어플을 사용하나요","로그인 어떻게 하나요","회원가입 어떻게 하나요"};
    String [] answer2 = {"카메라 촬영", "갤러리 선택", "주소는 어떻게 아나요"};

    TextView answer, answer_;

    ListView answer_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding);

        // 텍스트뷰를 변경할때
//        answer = findViewById(R.id.answer);
//        answer_ = findViewById(R.id.answer_);

        // 리스트뷰로 만들때
        answer_list = findViewById(R.id.answer_list);

        ListView listView = findViewById(R.id.listView);
//        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
//                new String[] {"위치는 어떻게 ~~", "잔", "짜", "잔", "짜", "잔"}));
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, question));
        listView.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // 그냥 텍스트뷰로 질문들 띄우기
//        if (question[i].equals("로그인 어떻게 하나요?")){
//            answer.setText(question);
//            answer_.setText("상단 버튼에" +
//                    "열쇠 든 아이콘" +
//                    "누르고 ~ " +
//                    "아이디 없으면" +
//                    "회원가입하구 ~" +
//                    "로그인 하면 돼 ~");
//        }else{
//            Toast.makeText(this, question, Toast.LENGTH_SHORT).show();
//        }


        Toast.makeText(this, question[i], Toast.LENGTH_SHORT).show();

    }
}