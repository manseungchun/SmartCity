package com.example.app0907;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class login extends AppCompatActivity {


    EditText etId, etPw;
    Button loginbtn;
    ImageView imgJoin;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etId = findViewById(R.id.etid);
        etPw = findViewById(R.id.etpw);
        loginbtn = findViewById(R.id.button9);
        imgJoin = findViewById(R.id.imgJoin);

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
                String url = "http://222.102.104.237:5000/login";

                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // 서버 응답 성공
                                Toast.makeText(login.this, "로그인 연결 성공", Toast.LENGTH_SHORT).show();
                                Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(login.this, MainActivity.class);

                                // 이름 가져오기
                                String name = response;
                                intent.putExtra("name", name);
                                startActivity(intent);
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