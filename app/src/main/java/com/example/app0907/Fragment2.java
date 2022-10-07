package com.example.app0907;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Fragment2 extends Fragment {

    Button galbtn, picbtn, submitBtn;
    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imv;
//    추가
    private static final int REQUEST_IMAGE_CODE = 101;
    final static int TAKE_PICTURE=1;
    Bitmap bitmap;

    RequestQueue requestQueue;
    TextView testView;

    File file;

    // login, logout
    ImageView loginbtn, logoutbtn;
    String name;

    // 로고 클릭시 home으로 가기
    TextView tvHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        submitBtn = view.findViewById(R.id.submitBtn);

        galbtn = view.findViewById(R.id.galbtn);
        picbtn = view.findViewById(R.id.picbtn);
        imv = (ImageView) view.findViewById(R.id.imv);

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
                        editor.remove("name");
                        editor.commit();

                        FragmentView(1);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });


        testView = view.findViewById(R.id.testView);

        // volley 연결할때 필요
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }

        // 제출하기 버튼 클릭시
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = Environment.getExternalStorageDirectory().getPath()+"/"+imv;
                Toast.makeText(getActivity(), filename, Toast.LENGTH_SHORT).show();
            }
        });


        // 사진찍기 버튼 클릭시
        picbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionCheck = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA);
                if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                    // 권한 없음
                    ActivityCompat.requestPermissions((MainActivity) getActivity(), new String[]{Manifest.permission.CAMERA}, 0);
                } else {
                    //권한 있음
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);
                }

            }

        });

        // 갤러리 버튼 클릭시
        galbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });
        return view;
    }


    // 이미지를 이미지뷰에 넣기
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 갤러리 이미지 가져오는 기능
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            imv.setImageURI(selectedImageUri);
        }
        // 촬영한 사진 가져오는 기능
        switch (requestCode){
            case TAKE_PICTURE:
                if(resultCode==RESULT_OK && data.hasExtra("data")){
                    bitmap = (Bitmap)data.getExtras().get("data");
                    if(bitmap!=null){
                        imv.setImageBitmap(bitmap);
                    }
                }
                break;
        }

    }

    // 카메라 권한 승인
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults[0] == 0) {
                Toast.makeText(getActivity().getApplicationContext(), "카메라 권한이 승인됨", Toast.LENGTH_SHORT).show();
            } else {
                //권한 거절된 경우
                Toast.makeText(getActivity().getApplicationContext(), "카메라 권한이 거절 되었습니다.카메라를 이용하려면 권한을 승낙하여야 합니다.", Toast.LENGTH_SHORT).show();
            }
        }
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


