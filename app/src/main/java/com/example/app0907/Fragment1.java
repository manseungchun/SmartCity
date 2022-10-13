package com.example.app0907;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class Fragment1 extends Fragment {
    ArrayList<String> imgs;
    int idx=0;
    ImageView loginbtn,logoutbtn,leftbtn,rightbtn;
    ImageView ckimv1,ckimv2,ckimv3;
    ImageButton imageButton;
    RequestQueue requestQueue;
    TextView vm;
    String name;

    URL img_url;
    Bitmap bitmap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_1, container, false);
        loginbtn = view.findViewById(R.id.loginbtn);
        logoutbtn = view.findViewById(R.id.logoutbtn);
        imageButton = view.findViewById(R.id.imageButton);
        leftbtn = view.findViewById(R.id.leftbtn);
        rightbtn = view.findViewById(R.id.rightbtn);
        vm = view.findViewById(R.id.vm);

        // img 담을 배열
        imgs = new ArrayList<>();


        ckimv1 = view.findViewById(R.id.ckimv1);
        ckimv2 = view.findViewById(R.id.ckimv2);
        ckimv3 = view.findViewById(R.id.ckimv3);

        // volley 연결 시 필요
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }

        // 로그인 시 이름 저장하고 빼오기
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name","");

        // 로그인 했을때 잠금버튼 로그아웃 했을때 로그인 버튼
        if(name.equals("")){
            loginbtn.setVisibility(View.VISIBLE);
            logoutbtn.setVisibility(View.INVISIBLE);
        }else{
            Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
            logoutbtn.setVisibility(View.VISIBLE);
            loginbtn.setVisibility(View.INVISIBLE);
        }

        // 로그아웃 버튼 클릭시
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그아웃 버튼 시 알림창
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // 로그아웃 메시지
                builder.setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?");

                // 로그아웃 아니요 눌렀을 때
                builder.setPositiveButton("아니요", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "로그아웃 취소", Toast.LENGTH_SHORT).show();
                    }
                });

                // 로그아웃 예 눌렀을 때
                builder.setNegativeButton("예", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "로그아웃 성공", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();

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
                Intent intent = new Intent(getActivity().getApplicationContext(),login.class);
                startActivity(intent);
            }
        });

        // 문의하기 버튼 클릭시
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), sliding.class);
                startActivity(intent);
            }
        });

        String url = "http://222.102.104.237:5000/AllImg";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 서버 연결 성공
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (jsonArray.length() != 0) {
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String img = jsonObject1.getString("img");
                                    imgs.add(img);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                            Thread uThread = new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        // 이미지 URL 경로
                                        for(int i=0; i<imgs.size();i++){
                                            img_url = new URL("http://222.102.104.237:5000/static/" + imgs.get(0));
                                            Log.d("img", String.valueOf(img_url));

                                            // web에서 이미지를 가져와 ImageView에 저장할 Bitmap을 만든다.
                                            HttpURLConnection conn = (HttpURLConnection) img_url.openConnection();
                                            conn.setDoInput(true); // 서버로부터 응답 수신
                                            conn.connect(); //연결된 곳에 접속할 때 (connect() 호출해야 실제 통신 가능함)
                                            InputStream is = conn.getInputStream(); //inputStream 값 가져오기
                                            bitmap = BitmapFactory.decodeStream(is); // Bitmap으로 변환
                                        }


                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };

                            uThread.start(); // 작업 Thread 실행

                            try {
                                //메인 Thread는 별도의 작업 Thread가 작업을 완료할 때까지 대기해야 한다.
                                //join() 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다리도록 한다.
                                //join() 메서드는 InterruptedException을 발생시킨다.
                                uThread.join();

                                //작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
                                //UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지 지정
                                ckimv1.setImageBitmap(bitmap);

                                // 실시간 현황 슬라이드 오른쪽 버튼 클릭시
                                rightbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (idx == imgs.size() - 3) {
                                            idx = 0;
                                        } else {
                                            idx++;
                                        }
                                        ckimv1.setImageResource(Integer.parseInt(imgs.get(idx)));
                                        ckimv2.setImageResource(Integer.parseInt(imgs.get(idx + 1)));
                                        ckimv3.setImageResource(Integer.parseInt(imgs.get(idx + 2)));
                                    }
                                });

                                // 실시간 현황 슬라이드 왼쪽 버튼 클릭 시
                                leftbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (idx == 0) {
                                            idx = imgs.size() - 3;
                                        } else {
                                            idx--;
                                        }
                                        ckimv1.setImageResource(Integer.parseInt(imgs.get(idx)));
                                        ckimv2.setImageResource(Integer.parseInt(imgs.get(idx + 1)));
                                        ckimv3.setImageResource(Integer.parseInt(imgs.get(idx + 2)));
                                    }
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 서버 응답 실패
                        Toast.makeText(getActivity(), "전체 이미지 연결 실패", Toast.LENGTH_SHORT).show();
                    }
                });
        request.setShouldCache(false);
        requestQueue.add(request);


        // 더보기 버튼 클릭시
        vm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentView(7);
            }
        });

        return view;
    }

    // 더보기 버튼 클릭시 더보기 프라그먼트로 이동하는 메소드
    private void FragmentView(int i) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (i){
            case 7:
                Fragment7 fragment7 = new Fragment7();
                transaction.replace(R.id.fl2,fragment7);
                transaction.commit();
                break;

        }
    }


}