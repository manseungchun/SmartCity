package com.example.app0907;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Fragment5 extends Fragment {

    // 데이터 관리할 공간
    ArrayList<ReportVO> data;
    ListView lv;
    
    RequestQueue requestQueue;

    String name;
    TextView tv2;

    // 회원정보수정
    TextView tvUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_5, container, false);

        data = new ArrayList<>();
        lv = view.findViewById(R.id.lv5);
        tv2=view.findViewById(R.id.tv2);
        tv2.setVisibility(View.INVISIBLE);

        // 회원정보 수정
        tvUpdate = view.findViewById(R.id.tvUpdate);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name","");

        String url = "http://222.102.104.237:5000/report";

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (jsonArray.length() != 0) {
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String date = jsonObject1.getString("date");
                                    String point = jsonObject1.getString("point");
                                    String spot = jsonObject1.getString("spot");

                                    data.add(new ReportVO(date,point,spot));

                                    ReportAdapter adapter = new ReportAdapter(view.getContext(), R.layout.reportlv, data);

                                    lv.setAdapter(adapter);
                                }
                            }else{
                                tv2.setVisibility(View.VISIBLE);
                            }


//                            date = jsonObject.getString("1");
//                            point = jsonObject.getString("2");
//                            Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getActivity(), point, Toast.LENGTH_SHORT).show();

//                            data.add(new ReportVO(date,point));
//
//                            ReportAdapter adapter = new ReportAdapter(view.getContext(), R.layout.reportlv, data);
//
//                            lv.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 서버 응답 실패
                        Toast.makeText(getActivity(), "신고현황 연결 실패", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            // 안드로이드에서 한글 인코딩
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
            // id 넘기기
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
