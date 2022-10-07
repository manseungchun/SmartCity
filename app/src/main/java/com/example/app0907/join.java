package com.example.app0907;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    // 로고 클릭시 home으로 가기
    TextView tvHome;

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

        // 로고 클릭시 home으로 가기
        tvHome = findViewById(R.id.tvHome);
        tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        JoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = JoinId.getText().toString();
                String pw = JoinPw.getText().toString();
                String name = JoinName.getText().toString();
                String addr = JoinAddr.getText().toString();
                String call = JoinCall.getText().toString();

                String url = "http://222.102.104.237:5000/join_result";

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

                finish();
            }
        });

    }
}