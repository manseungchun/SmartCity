package com.example.app0907;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


public class Fragment3 extends Fragment {

    Button buybtn1,buybtn2,buybtn3,buybtn4;

    TextView tvAllPoint;

    RequestQueue requestQueue;

    // login, logout
    ImageView loginbtn, logoutbtn;
    String name;

    // 로고 클릭시 home으로 가기
    TextView tvHome;

    // 마일리지 사용할 url 저장
    String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);
        buybtn1=view.findViewById(R.id.buybtn1);
        buybtn2=view.findViewById(R.id.buybtn2);
        buybtn3=view.findViewById(R.id.buybtn3);
        buybtn4=view.findViewById(R.id.buybtn4);

        tvAllPoint = view.findViewById(R.id.tvAllPoint);

        // login, logout
        loginbtn = view.findViewById(R.id.loginbtn);
        logoutbtn = view.findViewById(R.id.logoutbtn);
        loginbtn.setVisibility(View.INVISIBLE);

        // 로고 클릭시 home으로 가기
        tvHome = view.findViewById(R.id.tvHome);
        tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // volley 연결시 필요
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }

        // 저장된 로그인 시 이름 빼오기
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name","");

        // 로그아웃
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?");
                builder.setPositiveButton("아니요", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "로그아웃 취소", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("예", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "로그아웃 성공", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();

                        FragmentView(1);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        url = "http://222.102.104.237:5000/UsePoint";

        // 포인트 3000원 버튼 클릭시
        buybtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("확인창").setMessage("포인트를 사용하시겠습니까?");
                builder.setPositiveButton("아니요", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "취소되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("예", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        if(Integer.parseInt(tvAllPoint.getText().toString())>=3000){
                            String point = "-3000";
                            StringRequest request = new StringRequest(
                                    Request.Method.POST,
                                    url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(getActivity().getApplicationContext(), "3000포인트 사용완료", Toast.LENGTH_SHORT).show();
                                            tvAllPoint.setText(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // 서버 연결 실패
                                            Toast.makeText(getActivity(), "마일리지 사용 연결 실패", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            ){
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("id",name);
                                    params.put("point",point);
                                    return params;
                                }
                            };
                            request.setShouldCache(false);
                            requestQueue.add(request);
                        }else{
                            Toast.makeText(getActivity(), "포인트가 부족합니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        // 포인트 5000원 버튼 클릭시
        buybtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("확인창").setMessage("포인트를 사용하시겠습니까?");
                builder.setPositiveButton("아니요", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "취소되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("예", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        if(Integer.parseInt(tvAllPoint.getText().toString())>=5000){
                            String point = "-5000";
                            StringRequest request = new StringRequest(
                                    Request.Method.POST,
                                    url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(getActivity().getApplicationContext(), "5000포인트 사용완료", Toast.LENGTH_SHORT).show();
                                            tvAllPoint.setText(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // 서버 연결 실패
                                            Toast.makeText(getActivity(), "마일리지 사용 연결 실패", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            ){
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("id",name);
                                    params.put("point",point);
                                    return params;
                                }
                            };
                            request.setShouldCache(false);
                            requestQueue.add(request);
                        }else{
                            Toast.makeText(getActivity(), "포인트가 부족합니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        // 포인트 10000원 버튼 클릭시
        buybtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("확인창").setMessage("포인트를 사용하시겠습니까?");
                builder.setPositiveButton("아니요", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "취소되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("예", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        if(Integer.parseInt(tvAllPoint.getText().toString())>=10000){
                            String point = "-10000";
                            StringRequest request = new StringRequest(
                                    Request.Method.POST,
                                    url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(getActivity().getApplicationContext(), "10000포인트 사용완료", Toast.LENGTH_SHORT).show();
                                            tvAllPoint.setText(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // 서버 연결 실패
                                            Toast.makeText(getActivity(), "마일리지 사용 연결 실패", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            ){
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("id",name);
                                    params.put("point",point);
                                    return params;
                                }
                            };
                            request.setShouldCache(false);
                            requestQueue.add(request);
                        }else{
                            Toast.makeText(getActivity(), "포인트가 부족합니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        //// 포인트 30000원 버튼 클릭시
        buybtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("확인창").setMessage("포인트를 사용하시겠습니까?");
                builder.setPositiveButton("아니요", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "취소되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("예", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        if(Integer.parseInt(tvAllPoint.getText().toString())>=30000){
                            String point = "-30000";
                            StringRequest request = new StringRequest(
                                    Request.Method.POST,
                                    url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(getActivity().getApplicationContext(), "30000포인트 사용완료", Toast.LENGTH_SHORT).show();
                                            tvAllPoint.setText(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // 서버 연결 실패
                                            Toast.makeText(getActivity(), "마일리지 사용 연결 실패", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            ){
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("id",name);
                                    params.put("point",point);
                                    return params;
                                }
                            };
                            request.setShouldCache(false);
                            requestQueue.add(request);
                        }else{
                            Toast.makeText(getActivity(), "포인트가 부족합니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        // 전체 포인트 출력하는 url
        String AllPointurl = "http://222.102.104.237:5000/AllPoint";

        // volley 연결
        StringRequest request = new StringRequest(
                Request.Method.POST,
                AllPointurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 서버 연결 성공
                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                        tvAllPoint.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "전체 마일리지 연결 실패", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            // 로그인시 저장된 id 빼오기
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",name);
                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);

        return view;
    }

    // 로그아웃시 프라그먼트1로 가기
    private void FragmentView(int i) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (i){
            case 1:
                Fragment1 fragment1 = new Fragment1();
                transaction.replace(R.id.fl2,fragment1);
                // 새로고침해서 로그인 버튼으로 출력
                try{
                    // TODO 액티비티 화면 재갱신 시키는 코드
                    Intent intent = getActivity().getIntent();
                    getActivity().finish(); // 현재 액티비티 종료 실시
                    getActivity().overridePendingTransition(0,0);// 인텐트 애니메이션 없애기
                    startActivity(intent);// 현재 액티비티 재실행 실시
                    getActivity().overridePendingTransition(0,0);// 인텐트 애니메이션 없애기
                }catch (Exception e){
                    e.printStackTrace();
                }
                transaction.commit();
                break;

        }
    }
}