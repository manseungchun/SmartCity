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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


public class Fragment8 extends Fragment {

    // login, logout
    ImageView loginbtn, logoutbtn;
    String name;

    RequestQueue requestQueue;

    // 로고 클릭시 home으로 가기
    TextView tvHome;

    // 정보수정
    TextView UpdateId, UpdateName;
    EditText UpdatePw, UpdateAddr, UpdateCall;
    Button UpdateBtn;

    // 로그인한 정보
    String pw,reName,addr,call;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_8, container, false);

        // 정보수정
        UpdateId = view.findViewById(R.id.UpdateId);
        UpdateName = view.findViewById(R.id.UpdateName);
        UpdatePw = view.findViewById(R.id.UpdatePw);
        UpdateAddr = view.findViewById(R.id.UpdateAddr);
        UpdateCall = view.findViewById(R.id.UpdateCall);
        UpdateBtn = view.findViewById(R.id.UpdateBtn);

        // login, logout
        loginbtn = view.findViewById(R.id.loginbtn);
        logoutbtn = view.findViewById(R.id.logoutbtn);
        loginbtn.setVisibility(View.INVISIBLE);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }

        // 로고 클릭시 home으로 가기
        tvHome = view.findViewById(R.id.tvHome);
        tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // 저장된 로그인 시 이름 빼오기
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name","");
        pw=sharedPreferences.getString("pw","");
        reName=sharedPreferences.getString("reName","");
        addr =sharedPreferences.getString("addr","");
        call=sharedPreferences.getString("call","");


        UpdateId.setText(name);
        try {
            UpdateName.setText(URLDecoder.decode(reName,"utf-8"));
            UpdateAddr.setText(URLDecoder.decode(addr,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        UpdateCall.setText(call);

        // 정보수정 클릭시 정보 수정하기
        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edPw = UpdatePw.getText().toString();
                String edAddr = UpdateAddr.getText().toString();
                String edCall = UpdateCall.getText().toString();

                if(edPw.equals("")){
                    Toast.makeText(getActivity(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    UpdatePw.requestFocus();
                    return;
                }else if(edAddr.equals("")){
                    Toast.makeText(getActivity(), "주소를 입력해주세요", Toast.LENGTH_SHORT).show();
                    UpdateAddr.requestFocus();
                    return;
                }else if(edCall.equals("")){
                    Toast.makeText(getActivity(), "휴대폰번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    UpdateCall.requestFocus();
                    return;
                }else{
                    String url = "http://222.102.104.237:5000/update";

                    StringRequest request = new StringRequest(
                            Request.Method.POST,
                            url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // 서버 응답 성공
                                    Toast.makeText(getActivity(), "정보 수정 서버 연결 성공", Toast.LENGTH_SHORT).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // 서버 응답 실패
                                    Toast.makeText(getActivity(), "정보 수정 서버 연결 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                    ){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("id",name);
                            params.put("pw",edPw);
                            params.put("addr",edAddr);
                            params.put("call",edCall);
                            return params;
                        }
                    };
                    request.setShouldCache(false);
                    requestQueue.add(request);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("정보수정").setMessage("수정완료되었습니다.");
                    builder.setNegativeButton("확인", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            FragmentView(4);
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

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

            case 4:
                Fragment4 fragment4 = new Fragment4();
                transaction.replace(R.id.fl2,fragment4);
                transaction.commit();
        }
    }
}