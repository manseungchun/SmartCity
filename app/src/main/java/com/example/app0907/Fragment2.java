package com.example.app0907;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


public class Fragment2 extends Fragment {

    Button galbtn, picbtn, submitBtn;
    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imv;
//    추가
    private static final int REQUEST_IMAGE_CODE = 101;
    final static int TAKE_PICTURE=1;
    Bitmap bitmap;

    // 사진 메타데이터 가져오기
    private boolean valid = false;
    private Float latitude, longitude;
    Geocoder geocoder;
    private Uri selectedImageUri;
    TextView testView;
    //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        // 사진 메타데이터 가져오기
        geocoder = new Geocoder(view.getContext());
        testView = view.findViewById(R.id.testView);
        //


        submitBtn = view.findViewById(R.id.submitBtn);

        galbtn = view.findViewById(R.id.galbtn);
        picbtn = view.findViewById(R.id.picbtn);
        imv = (ImageView) view.findViewById(R.id.imv);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = Environment.getExternalStorageDirectory().getPath();
                Toast.makeText(getActivity(), filename, Toast.LENGTH_SHORT).show();
                try {
                    ExifInterface exif = new ExifInterface(filename);
                    showExif(exif);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                }

            }

            private void showExif(ExifInterface exif) {
                String myAttribute = "[Exif information] \n\n";

                myAttribute += exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                myAttribute += exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
                myAttribute += exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                myAttribute += exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);

                testView.setText(myAttribute);

            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 갤러리 이미지 가져오는 기능
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
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
}


