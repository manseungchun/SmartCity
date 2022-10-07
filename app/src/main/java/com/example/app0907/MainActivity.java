package com.example.app0907;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnv;
    FrameLayout fl;
    Button fragbtn5,fragbtn6;

    String name;

    // bottombar에서 선택한 메뉴에 따라 FrameLayout에 올라갈
    // Fragment가 결정이 된다
    // : 어떤 메뉴가 선택 되었는지 알아내야함
    // 그래서 메뉴아이템에 Id값을 줬다!!

    // Fragment를 구현하는 방법
    // NavigatonView에서 선택한 메뉴에 따라
    // FragmentLayout에 표시할 Fragment를 바꾼다!

    // 1) 4번 Fragment에 버튼 넣고 Event주기
    // 2) 1번 Fragment에 WebView 넣고 Web띄우기
    // 3) 1)+2)
    // 4번에서 EditText에 url 작성후 버튼 누르면 URL정보를 Fragment1로 보내서
    // 웹페이지 띄우기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        bnv=findViewById(R.id.bnv2);
        fl=findViewById(R.id.fl2);
        fragbtn5=findViewById(R.id.fragbtn5);
        fragbtn6=findViewById(R.id.fragbtn6);


        SharedPreferences sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name","");

        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();


        // 어플을 처음 실행시켰을때 첫 화면이 Fragment1이 되게 하기 위해서
        getSupportFragmentManager().beginTransaction().replace(
                R.id.fl2,
                new Fragment1()).commit();

        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if(!name.equals("")){
                    switch (item.getItemId()){
                        case R.id.tab1:
                            getSupportFragmentManager().beginTransaction().replace(
                                    R.id.fl2,
                                    new Fragment1()).commit();
                            break;

                        case R.id.tab2:
                            getSupportFragmentManager().beginTransaction().replace(
                                    R.id.fl2,
                                    new Fragment2()).commit();
                            break;
                        case R.id.tab3:
                            getSupportFragmentManager().beginTransaction().replace(
                                    R.id.fl2,
                                    new Fragment3()).commit();
                            break;
                        case R.id.tab4:
                            getSupportFragmentManager().beginTransaction().replace(
                                    R.id.fl2,
                                    new Fragment4()).commit();
                            break;
                    }
                    // event 처리가 끝나지 않았다.
                    // LongClick 일반클릭 안먹힘
                    // return ---> true 이벤트 종료를 감지 해야한다!
                    return true;

                }else{
                    switch (item.getItemId()){
                        case R.id.tab1:
                            getSupportFragmentManager().beginTransaction().replace(
                                    R.id.fl2,
                                    new Fragment1()).commit();
                            return true;
                        default:
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                            builder.setTitle("로그인").setMessage("로그인을 해주세요");
                            builder.setPositiveButton("아니요", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    Toast.makeText(getApplicationContext(), "로그인 취소", Toast.LENGTH_SHORT).show();
                                }
                            });
                            builder.setNegativeButton("예", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    Toast.makeText(getApplicationContext(), "로그인 하기", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(MainActivity.this, login.class);
                                    startActivity(intent);
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            return false;

                    }

                }

            }
        });

    }
}