package com.example.app0907;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Fragment6 extends Fragment {

    // 데이터 관리할 공간
    ArrayList<MileageVO> data;
    ListView lv;

    RequestQueue requestQueue;

    String name;
    TextView tv;

    // 회원정보수정
    TextView tvUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_6, container, false);

        data = new ArrayList<>();
        lv = view.findViewById(R.id.lv6);
        tv=view.findViewById(R.id.tv);
        tv.setVisibility(View.INVISIBLE);

        // 회원정보 수정
        tvUpdate = view.findViewById(R.id.tvUpdate);


        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name","");

        String url = "http://222.102.104.237:5000/point";

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // 서버 응답 성공
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (jsonArray.length() != 0) {
                                for(int i=0; i<jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String date = jsonObject1.getString("date");
                                    String point = jsonObject1.getString("point");

                                    data.add(new MileageVO(date, point));

                                    MileageAdapter adapter = new MileageAdapter(view.getContext(), R.layout.mileagelv, data);

                                    lv.setAdapter(adapter);
                                }
                            }else{
                                tv.setVisibility(View.VISIBLE);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 서버 응답 실패
                        Toast.makeText(getActivity(), "마일리지 연결 실패", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
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


        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentView(8);
            }
        });

        return view;

    }

    // 더보기 버튼 클릭시 더보기 프라그먼트로 이동하는 메소드
    private void FragmentView(int i) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (i){
            case 8:
                Fragment8 fragment8 = new Fragment8();
                transaction.replace(R.id.fl2,fragment8);
                transaction.commit();
                break;

        }
    }

}
