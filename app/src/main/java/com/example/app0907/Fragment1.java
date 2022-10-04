package com.example.app0907;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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


        ckimv1 = view.findViewById(R.id.ckimv1);
        ckimv2 = view.findViewById(R.id.ckimv2);
        ckimv3 = view.findViewById(R.id.ckimv3);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name","");
        Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
        if(name.equals("")){
            loginbtn.setVisibility(View.VISIBLE);
            logoutbtn.setVisibility(View.INVISIBLE);
        }else{
            Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
            logoutbtn.setVisibility(View.VISIBLE);
            loginbtn.setVisibility(View.INVISIBLE);
        }
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
                        editor.remove("name");
                        editor.commit();
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