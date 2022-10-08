package com.example.app0907;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;

import com.android.volley.toolbox.Volley;




public class Fragment1 extends Fragment {
    int[] imgs = {R.drawable.crack,R.drawable.crack2,R.drawable.crack3,R.drawable.crack4
            ,R.drawable.crack5,R.drawable.crack6,R.drawable.crack7,R.drawable.crack8
            ,R.drawable.crack9};
    int idx=0;
    ImageView loginbtn,logoutbtn,leftbtn,rightbtn;
    ImageView ckimv1,ckimv2,ckimv3;
    ImageButton imageButton;
    RequestQueue requestQueue;
    TextView vm;
    String name;


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

        // 실시간 현황 슬라이드 오른쪽 버튼 클릭시
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

        // 실시간 현황 슬라이드 왼쪽 버튼 클릭 시
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