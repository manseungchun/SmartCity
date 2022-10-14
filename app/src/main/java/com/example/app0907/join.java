package com.example.app0907;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class join extends AppCompatActivity {

    EditText JoinId, JoinPw, JoinPw2, JoinName, JoinAddr, JoinCall;
    Button JoinBtn;

    RequestQueue requestQueue;

    // 로고 클릭시 home으로 가기 / 로그인 중복체크
    TextView tvHome, tvIdCheck;
    Boolean IdCheck;
    TextView tvPw2;

    ImageView loginbtn;

    String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        JoinId = findViewById(R.id.JoinId);
        JoinPw = findViewById(R.id.JoinPw);
        JoinPw2 = findViewById(R.id.JoinPw2);
        JoinName = findViewById(R.id.JoinName);
        JoinAddr = findViewById(R.id.JoinAddr);
        JoinCall = findViewById(R.id.JoinCall);
        JoinBtn = findViewById(R.id.JoinBtn);
        loginbtn = findViewById(R.id.loginbtn);

        tvPw2 = findViewById(R.id.tvPw2);
        tvIdCheck = findViewById(R.id.tvIdCheck);
        IdCheck = false;

        // 로고 클릭시 home으로 가기
        tvHome = findViewById(R.id.tvHome);
        tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // 로그인 버튼 클릭시 로그인
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(join.this, login.class);
                startActivity(intent);
            }
        });

        // volley 연결시 필요
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        // 아이디 중복 체크
        tvIdCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IdCheck = true;
                String id = JoinId.getText().toString();

                String url = "http://222.102.104.237:5000/idCheck";

                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // 서버 응답 성공

                                // 아이디 겹치면 사용 x알림창 + edittext창 초기화
                                if(response.equals("사용불가능")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(join.this);

                                    builder.setTitle("이이디 중복 확인").setMessage("사용할 수 없는 아이디입니다.");
                                    builder.setNegativeButton("확인", new DialogInterface.OnClickListener(){
                                        @Override
                                        public void onClick(DialogInterface dialog, int id)
                                        {
                                            JoinId.setText(null);
                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();

                                }else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(join.this);

                                    builder.setTitle("이이디 중복 확인").setMessage("사용할 수 있는 아이디입니다.");
                                    builder.setNegativeButton("확인", new DialogInterface.OnClickListener(){
                                        @Override
                                        public void onClick(DialogInterface dialog, int id)
                                        {
                                            JoinId.setClickable(false);
                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // 서버 응답 실패
                                Toast.makeText(join.this, "중복확인 체크 실패", Toast.LENGTH_SHORT).show();
                            }
                        }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("id",id);
                        return params;
                    }
                };

                request.setShouldCache(false);
                requestQueue.add(request);
            }
        });


        JoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = JoinId.getText().toString();
                String pw = JoinPw.getText().toString();
                String pw2 = JoinPw2.getText().toString();
                name = JoinName.getText().toString();
                String addr = JoinAddr.getText().toString();
                String call = JoinCall.getText().toString();

                if(IdCheck==false){
                    Toast.makeText(join.this, "아이디 중복을 확인해주세요", Toast.LENGTH_SHORT).show();
                    JoinId.requestFocus();
                    return;
                }else if(pw.equals("")){
                    Toast.makeText(join.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    JoinPw.requestFocus();
                    return;
                }else if(pw2.equals("")){
                    Toast.makeText(join.this, "비밀번호를 다시 한번 입력해주세요", Toast.LENGTH_SHORT).show();
                    JoinPw2.requestFocus();
                    return;
                }else if(!pw.equals(pw2)){
                    tvPw2.setText("비밀번호가 일치하지 않습니다.");
                    JoinPw2.setText(null);
                    JoinPw2.requestFocus();
                    return;
                }else if(name.equals("")){
                    Toast.makeText(join.this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    JoinName.requestFocus();
                    return;
                }else if(addr.equals("")){
                    Toast.makeText(join.this, "주소를 입력해주세요", Toast.LENGTH_SHORT).show();
                    JoinAddr.requestFocus();
                    return;
                }else if(call.equals("")){
                    Toast.makeText(join.this, "휴대폰번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    JoinCall.requestFocus();
                    return;
                }else{
                    tvPw2.setText("비밀번호가 일치합니다.");

                    String url = "http://222.102.104.237:5000/join";

                    StringRequest request = new StringRequest(
                            Request.Method.POST,
                            url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(join.this, "회원가입 연결 성공", Toast.LENGTH_SHORT).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(join.this, "회원가입 연결 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                    ){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("id",id);
                            params.put("pw",pw);
                            params.put("name",name);
                            params.put("addr",addr);
                            params.put("call",call);
                            return params;
                        }
                    };
                    request.setShouldCache(false);
                    requestQueue.add(request);

                    AlertDialog.Builder builder = new AlertDialog.Builder(join.this);

                    builder.setTitle("회원가입").setMessage(name+"님 회원가입을 축하드립니다.🎉🎉");
                    builder.setNegativeButton("확인", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            finish();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }
        });

    }
}