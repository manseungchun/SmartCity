package com.example.app0907;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class sliding extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] question = {"회원가입은 어디서하나요?", "신고는 어떻게 해야하나요?", "위치정보를 꼭 입력해야하나요?", "어떤걸 신고해야하나요?", "신고처리현황은 어디서확인하나요?", "마일리지 적립은 어떻게되나요?","마일리지 사용시 상품권은 어떻게 수령하나요?", "이 외의 다른문의사항이 있어요"};
    String[] answer1 = {"ㅤㅤㅤㅤ❓회원가입은 어디서하나요?",
            "▶어플 우측상단에 있는 아이콘을 클릭해주시면 회원가입하러가기 버튼이 있습니다. 회원가입 버튼을 통해 회원가입을 진행해주세요~!"};
    String[] answer2 = {"ㅤㅤㅤㅤ❓신고는 어떻게 해야하나요?",
            "▶어플 하단에 있는 안전신고 버튼을 누르신후 직접촬영하신다면 카메라 촬영하기를, 또는 갤러리에서 사진을 가져오신후 신고확인 버튼을 눌러주시면 됩니다~!"};
    String[] answer3 = {"ㅤㅤㅤㅤ❓위치정보를 꼭 입력해야하나요?",
            "▶위치정보는 찍어주신 사진에서 위치정보를 가져올수있으므로 필수는 아닙니다. 하지만 상세한위치를 적어주시면 더욱더 도움이 됩니다~!"};
    String[] answer4 = {"ㅤㅤㅤㅤ❓어떤걸 신고해야하나요?",
            "▶건물의 크랙 또는 박리된 부분을 찍어서 신고해주시면 됩니다~!"};
    String[] answer5 = {"ㅤㅤㅤㅤ❓신고처리현황은 어디서확인하나요?",
            "▶신고처리 현황은 우측하단에 있는 마이페이지 버튼에서 확인 가능하십니다~!"};
    String[] answer6 = {"ㅤㅤㅤㅤ❓마일리지 적립은 어떻게되나요?",
            "▶마일리지 적립은 신고해주신 사진을 인공지능이 파악후 크랙,박리가 확인된다면 100포인트씩 일괄 적립됩니다~!"};
    String[] answer7 = {"ㅤㅤㅤㅤ❓마일리지 사용시 상품권은 어떻게 수령하나요?",
            "▶가입시 기재해주신 핸드폰으로 문자 발송이 됩니다. \n 핸드폰번호를 다시 한번 확인해주세요~!"};
    String[] answer8 = {"ㅤㅤㅤㅤ❓이 외의 다른문의사항이 있어요",
            "▶이 외의 다른 문의사항은 pixer@pixer.com으로 메일을 남겨주시면 확인후 순차대로 답변드리겠습니다 감사합니다~!"};
    String[] answer9 = {"ㅤㅤㅤ❗❗아래에 질문목록을 열어주세요❗❗"};

    ListView answer_list;
    TextView textView14;

    // 로그인 로그아웃
    ImageView loginbtn, logoutbtn;
    String name;
    RequestQueue requestQueue;
    // 로고 클릭시 home으로 가기
    TextView tvHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding);

        loginbtn = findViewById(R.id.loginbtn);
        logoutbtn = findViewById(R.id.logoutbtn);
        textView14 = findViewById(R.id.tvHome);
        answer_list = findViewById(R.id.answer_list);

        // 로고 클릭시 home으로 가기
        tvHome = findViewById(R.id.tvHome);
        tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // volley 연결 시 필요
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        // 로그인 시 이름 저장하고 빼오기
        SharedPreferences sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name","");

        // 로그인 했을때 잠금버튼 로그아웃 했을때 로그인 버튼
        if(name.equals("")){
            loginbtn.setVisibility(View.VISIBLE);
            logoutbtn.setVisibility(View.INVISIBLE);
        }else{
            logoutbtn.setVisibility(View.VISIBLE);
            loginbtn.setVisibility(View.INVISIBLE);
        }
        // 로그아웃 버튼 클릭시
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그아웃 버튼 시 알림창
                AlertDialog.Builder builder = new AlertDialog.Builder(sliding.this);

                // 로그아웃 메시지
                builder.setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?");

                // 로그아웃 아니요 눌렀을 때
                builder.setPositiveButton("아니요", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getApplicationContext(), "로그아웃 취소", Toast.LENGTH_SHORT).show();
                    }
                });

                // 로그아웃 예 눌렀을 때
                builder.setNegativeButton("예", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getApplicationContext(), "로그아웃 성공", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();

                        Intent intent = new Intent(sliding.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        // 로그인 버튼 클릭시
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),login.class);
                startActivity(intent);
            }
        });

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, question));
        listView.setOnItemClickListener(this);

        //추가코드
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, answer9);
        answer_list.setAdapter(mAdapter);

    }


    // 슬라이드 올려서 질문 선택시
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//        for (i = 0; i < question.length; i++) {
//            if (question[i].equals(question[i])) {
//                answer_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Collections.answer[i]));
//                break;
//            }

        if (question[i].equals("회원가입은 어디서하나요?")) {
            answer_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, answer1));
        } else if (question[i].equals("신고는 어떻게 해야하나요?")) {
            answer_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, answer2));
        } else if (question[i].equals("위치정보를 꼭 입력해야하나요?")) {
            answer_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, answer3));
        } else if (question[i].equals("어떤걸 신고해야하나요?")) {
            answer_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, answer4));
        } else if (question[i].equals("신고처리현황은 어디서확인하나요?")) {
            answer_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, answer5));
        } else if (question[i].equals("마일리지 적립은 어떻게되나요?")) {
            answer_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, answer6));
        } else if (question[i].equals("마일리지 사용시 상품권은 어떻게 수령하나요?")) {
            answer_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, answer7));
        } else if (question[i].equals("이 외의 다른문의사항이 있어요")) {
            answer_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, answer8));

        }
    }

}
