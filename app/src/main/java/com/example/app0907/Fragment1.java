package com.example.app0907;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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


public class Fragment1 extends Fragment {
    int[] imgs = {R.drawable.crack,R.drawable.crack2,R.drawable.crack3,R.drawable.crack4
            ,R.drawable.crack5,R.drawable.crack6,R.drawable.crack7,R.drawable.crack8
            ,R.drawable.crack9};
    int idx=0;
    ImageView loginbtn,logoutbtn,leftbtn,rightbtn;
    ImageView ckimv1,ckimv2,ckimv3;
    String name;
    ImageButton imageButton;
    RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_1, container, false);
        loginbtn = view.findViewById(R.id.loginbtn);
        logoutbtn = view.findViewById(R.id.logoutbtn);
        imageButton = view.findViewById(R.id.imageButton);
        leftbtn = view.findViewById(R.id.leftbtn);
        rightbtn = view.findViewById(R.id.rightbtn);

        ckimv1 = view.findViewById(R.id.ckimv1);
        ckimv2 = view.findViewById(R.id.ckimv2);
        ckimv3 = view.findViewById(R.id.ckimv3);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }

        Intent intent = new Intent(getActivity().getIntent());
        name = intent.getStringExtra("name");
        if(name!=null){
            logoutbtn.setVisibility(View.VISIBLE);
            loginbtn.setVisibility(View.INVISIBLE);
            logoutbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "로그아웃 버튼 클릭", Toast.LENGTH_SHORT).show();

                    String url = "http://222.102.104.237:5000/logout";

                    StringRequest request = new StringRequest(
                            Request.Method.GET,
                            url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // 서버 응답 성공
                                    Toast.makeText(getActivity(), "로그아웃 연결 성공", Toast.LENGTH_SHORT).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // 서버 응답 실패
                                    Toast.makeText(getActivity(), "로그아웃 연결 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                }
            });
        }else{
            loginbtn.setVisibility(View.VISIBLE);
            logoutbtn.setVisibility(View.INVISIBLE);
        }

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(),login.class);
                startActivity(intent);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), sliding.class);
                startActivity(intent);
            }
        });

        rightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idx==imgs.length-3){
                    idx=0;
                }else{
                    idx++;
                }
                ckimv1.setImageResource(imgs[idx]);
                ckimv2.setImageResource(imgs[idx+1]);
                ckimv3.setImageResource(imgs[idx+2]);
            }
        });

        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idx==0){
                    idx=imgs.length-3;
                }else{
                    idx--;
                }
                ckimv1.setImageResource(imgs[idx]);
                ckimv2.setImageResource(imgs[idx+1]);
                ckimv3.setImageResource(imgs[idx+2]);
            }
        });
        


        return view;
    }
}