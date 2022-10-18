package com.example.app0907;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {


    EditText etId, etPw;
    Button loginbtn;
    ImageView imgJoin;

    RequestQueue requestQueue;

    // 로고 클릭시 home으로 가기
    TextView tvHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etId = findViewById(R.id.etid);
        etPw = findViewById(R.id.etpw);
        loginbtn = findViewById(R.id.button9);
        imgJoin = findViewById(R.id.imgJoin);

        // 로고 클릭시 home으로 가기
        tvHome = findViewById(R.id.tvHome);
        tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        // 로그인 버튼을 눌렀을 때,
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // etId, etPw를 id, pw에 담기
                String id = etId.getText().toString();
                String pw = etPw.getText().toString();

                // flask url -> login해주는 기능
                String url = "http://222.102.104.237:5000/loginApp";

                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // 서버 응답 성공
//                                Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(login.this, MainActivity.class);
//
//                                // 이름 가져오기
//                                String name = response;
//                                intent.putExtra("name", name);
//                                startActivity(intent);

                                if(!response.equals("로그인 실패")){

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                                        if (jsonArray.length() != 0) {
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                                String id = jsonObject1.getString("id");
                                                String pw = jsonObject1.getString("pw");
                                                String name = jsonObject1.getString("name");
                                                String addr = jsonObject1.getString("addr");
                                                String call = jsonObject1.getString("call");

                                                SharedPreferences sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);    // test 이름의 기본모드 설정
                                                SharedPreferences.Editor editor = sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
                                                editor.putString("name", id); // key,value 형식으로 저장
                                                editor.putString("pw",pw);
                                                editor.putString("reName",name);
                                                editor.putString("addr",addr);
                                                editor.putString("call",call);
                                                editor.commit();    //최종 커밋. 커밋을 해야 저장이 된다.

                                            }

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    // 로그인 성공시 로그인 버튼을 로그아웃 버튼으로 변하게 하기
                                    try{
                                        // TODO 액티비티 화면 재갱신 시키는 코드
                                        Intent intent = new Intent(login.this, MainActivity.class);
                                        finish(); // 현재 액티비티 종료 실시
                                        overridePendingTransition(0,0);// 인텐트 애니메이션 없애기
                                        startActivity(intent);// 현재 액티비티 재실행 실시
                                        overridePendingTransition(0,0);// 인텐트 애니메이션 없애기
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }else{
                                    // 로그인 실패시 로그인 화면 그대로
                                    try{
                                        // TODO 액티비티 화면 재갱신 시키는 코드
                                        Intent intent = login.this.getIntent();
                                        finish(); // 현재 액티비티 종료 실시
                                        overridePendingTransition(0,0);// 인텐트 애니메이션 없애기
                                        startActivity(intent);// 현재 액티비티 재실행 실시
                                        overridePendingTransition(0,0);// 인텐트 애니메이션 없애기
                                        Toast.makeText(login.this, "비밀번호를 잘못입력하셨습니다", Toast.LENGTH_SHORT).show();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // 서버 응답 실패
                                Toast.makeText(login.this, "로그인 연결 실패", Toast.LENGTH_SHORT).show();
                            }
                        }
                ){
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        try {
                            String utf8String = new String(response.data, "UTF-8");
                            return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                        } catch (UnsupportedEncodingException e) {
                            // log error
                            return Response.error(new ParseError(e));
                        } catch (Exception e) {
                            // log error
                            return Response.error(new ParseError(e));
                        }

                    }

                    // id, pw 넘기기
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("id",id);
                        params.put("pw",pw);
                        return params;
                    }
                };

                request.setShouldCache(false);
                requestQueue.add(request);
            }
        });




        imgJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, join.class);
                startActivity(intent);
            }
        });
    }
}