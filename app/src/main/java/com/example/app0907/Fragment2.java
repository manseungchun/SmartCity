package com.example.app0907;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Base64;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class Fragment2 extends Fragment {


    //위치권환 요청
    int permissionCheck;
    
    Button galbtn, picbtn, submitBtn;
    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imv;
    //    추가
    private static final int REQUEST_IMAGE_CODE = 101;
    //    final static int TAKE_PICTURE=1;
    private Bitmap bitmap;

    RequestQueue requestQueue;

    File file;

    // login, logout
    ImageView loginbtn, logoutbtn;
    String name;

    // 로고 클릭시 home으로 가기
    TextView tvHome;

    // 로딩중 표시
    private ProgressDialog progressDialog;

    // 서버에 업로드할 문자열 된 이미지
    private String imageString;

    // 현재 사진 경로 저장
//    private String mCurrentPhotoPath;

    // 변수선언
//    private Float lat, lon;
    Geocoder geocoder;
    private boolean valid = false;

//    static final String TAG = "카메라";

    // 카메라
    final private static String TAG = "GILBOMI";
    Button btn_photo;
    ImageView iv_photo;

    final static int TAKE_PICTURE = 1;

    String mmCurrentPhotoPath;
    final static int REQUEST_TAKE_PHOTO = 1;

    // 위도 경도 저장
    Double lat, lon;

    // 좌표(위도,경도)를 주소나 지명으로 변환(반환타입)
    List<Address> address;

    // 추가사항
    EditText edtDetail;
    String detail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        submitBtn = view.findViewById(R.id.submitBtn);

        galbtn = view.findViewById(R.id.galbtn);
        picbtn = view.findViewById(R.id.picbtn);
        imv = (ImageView) view.findViewById(R.id.imv);

        edtDetail = view.findViewById(R.id.edtDetail);

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



        // 위도경도로 주소 찾기
        geocoder = new Geocoder(view.getContext());



        // 저장된 로그인 시 이름 빼오기
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name", "");

        // 로그아웃
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?");
                builder.setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity().getApplicationContext(), "로그아웃 취소", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
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

        // volley 연결할때 필요
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }

        // 제출하기 버튼 클릭시
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bitmap != null) {
                    progressDialog = new ProgressDialog(view.getContext());
                    progressDialog.setMessage("분석중입니다.\n잠시만 기다려 주세요.");
                    progressDialog.show();

                    sendImage();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("제출실패").setMessage("사진을 업로드 해주시길 바랍니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }
        });

        // 다중 권한 (카메라,위치,데이터)설정
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_MEDIA_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

//        //위치권한 요청
//        permissionCheck = ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
//        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
//        }

        // 카메라 권한 승인
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        Log.d(TAG, "onRequestPermissionsResult");
//        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
//        }
//    }


        // 사진찍기 버튼 클릭시
        picbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.picbtn:
                        dispatchTakePictureIntent();
                        break;
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

    // 해상도 조절
//    private Bitmap setPic(Bitmap bitmap) {
//        // Get the dimensions of the View
//        int targetW = imv.getWidth();
//        int targetH = imv.getHeight();
//        Log.d("해상도", String.valueOf(targetW));
//        Log.d("해상도", String.valueOf(targetH));
//
////        targetW = 302;
////        targetH = 302;
//
//        // Get the dimensions of the bitmap
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
//
//        int photoW = bmOptions.outWidth;
//        int photoH = bmOptions.outHeight;
//        photoW = 604 * 1024;
//        photoH = 604 * 1024;
//        Log.d("해상도", String.valueOf(photoW));
//        Log.d("해상도", String.valueOf(photoH));
//
//
//        // Determine how much to scale down the image
//        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
//
//        // Decode the image file into a Bitmap sized to fill the View
//        bmOptions.inJustDecodeBounds = false;
//        bmOptions.inSampleSize = scaleFactor;
//        bmOptions.inPurgeable = true;
//
//        bitmap = BitmapFactory.decodeFile(mmCurrentPhotoPath, bmOptions);
////        imv.setImageBitmap(bitmap);
//        return bitmap;
//    }

    // 이미지를 플라스크에 전송
    private void sendImage() {

        detail = edtDetail.getText().toString();
        if(detail.isEmpty()){
            detail = "추가사항이 없습니다.";
        }
        //비트맵 이미지를 byte로 변환 -> base64형태로 변환
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

//        // start
//        setPic(bitmap);
//        // end


        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //base64형태로 변환된 이미지 데이터를 플라스크 서버로 전송
        String flask_url = "http://222.102.104.237:5000/sendImage";
        StringRequest request = new StringRequest(Request.Method.POST, flask_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if (response.equals("true")) {
                            Toast.makeText(getActivity(), "업로드 성공", Toast.LENGTH_LONG).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                            builder.setTitle("분석결과").setMessage("분석결과 해당 사진은\n" + response + "(으)로 판별되었습니다.\n100포인트가 적립됩니다.");
                            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    // 어디로 보낼지 모르게땨 ~
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        } else {
                            Toast.makeText(getActivity(), "업로드 실패", Toast.LENGTH_LONG).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                            builder.setTitle("분석결과").setMessage("분석결과 해당 사진은 크랙이 감지되지 않았습니다.\n이용해주셔서 감사합니다.\n다음에 다시 이용해주세요.");
                            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    // 어디로 보낼지 모르게땨 ~
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "업로드 서버 연결 실패", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("image", imageString);
                params.put("id",name);
                params.put("lat", String.valueOf(lat));
                params.put("lon", String.valueOf(lon));
                params.put("addr", address.get(0).getAddressLine(0));
                params.put("detail",detail);

                Log.v("업로드",name);
                Log.v("업로드", String.valueOf(lat));
                Log.v("업로드", String.valueOf(lon));
                Log.v("업로드",address.get(0).getAddressLine(0));
                Log.v("업로드",detail);

                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }


    // 이미지를 이미지뷰에 넣기
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 갤러리 이미지 가져오는 기능
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            // bitmap에 저장
            getBitmap(selectedImageUri);


            String realPath = getRealPathFromURI(selectedImageUri);

            Log.v("asdf", realPath);

            try {
                ExifInterface eif = new ExifInterface(realPath);
                Log.v("asdf", String.valueOf(eif));
                double[] GPS_LATITUDE = eif.getLatLong();
                Log.v("asdf", String.valueOf(GPS_LATITUDE));
                if(GPS_LATITUDE == null){
                    Toast.makeText(getActivity(), "설정에서 위치 정보를 허용해주세요", Toast.LENGTH_SHORT).show();
                }else{

                    imv.setImageURI(selectedImageUri);

                    lat = GPS_LATITUDE[0]; // 위도 저장
                    lon = GPS_LATITUDE[1]; // 경도 저장



                    Log.v("asdf 1", GPS_LATITUDE[0] + "");
                    Log.v("asdf 2", GPS_LATITUDE[1] + "");

                    // 위도경도를 주소로 변환
                    address = null;
                    try {
                        address = geocoder.getFromLocation(lat,lon,10);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("위도경도","입출력 오류");
                    }
                    if (address != null) {
                        if (address.size() == 0) {
                            Log.v("위도경도","주소찾기 오류");
                        }else{
                            Log.v("위도경도", String.valueOf(address.get(0)));
                        }
                    }
                }


            } catch (IOException e) {
                Log.v("asdf", e.toString());
            }




        }
        // 촬영한 사진 가져오는 기능
        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO: {
                    if (resultCode == RESULT_OK) {
                        file = new File(mmCurrentPhotoPath);
                        if (Build.VERSION.SDK_INT >= 29) {
                            ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), Uri.fromFile(file));
                            try {
                                bitmap = ImageDecoder.decodeBitmap(source);
                                saveFile(mmCurrentPhotoPath);
                                if (bitmap != null) {
                                    imv.setImageBitmap(bitmap);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(file));
                                saveFile(mmCurrentPhotoPath);
                                if (bitmap != null) {

                                    imv.setImageBitmap(bitmap);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        String realPath = getRealPathFromURI(Uri.fromFile(file));
                        Log.v("asdf", realPath);

                        try {
                            ExifInterface eif = new ExifInterface(realPath);
                            double[] GPS_LATITUDE = eif.getLatLong();

                            lat = GPS_LATITUDE[0]; // 위도 저장
                            lon = GPS_LATITUDE[1]; // 경도 저장

                            // 위도경도를 주소로 변환
                            address = null;
                            try {
                                address = geocoder.getFromLocation(lat,lon,10);
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d("위도경도","입출력 오류");
                            }
                            if (address != null) {
                                if (address.size() == 0) {
                                    Log.v("위도경도","주소찾기 오류");
                                }else{
                                    Log.v("위도경도",address.get(0).toString());
                                }
                            }


                            Log.v("asdf 1", GPS_LATITUDE[0] + "");
                            Log.v("asdf 2", GPS_LATITUDE[1] + "");

//                            String GPS_LATITUDE = eif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
//                            String attrLATITUDE_REF = eif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
//                            String attrLONGITUDE = eif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
//                            String attrLONGITUDE_REF = eif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
//
//
//                            Log.v("asdf 1", GPS_LATITUDE);
//                            Log.v("asdf 2", attrLATITUDE_REF);
//                            Log.v("asdf 3", attrLONGITUDE);
//                            Log.v("asdf 4", attrLONGITUDE_REF);

                        } catch (IOException e) {
                            Log.v("asdf", e.toString());
                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 카메라 촬영 시 임시로 사진을 저장하고 사진위치에 대한 Uri 정보를 가져오는 메소드
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        Log.d(TAG, "사진저장 >>" + storageDir);

        mmCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }

    //갤러리 사진 저장 기능
    private void saveFile(String mCurrentPhotoPath) {

        Bitmap bitmap = BitmapFactory.decodeFile( mCurrentPhotoPath );

        ContentValues values = new ContentValues( );

        //실제 앨범에 저장될 이미지이름
        values.put( MediaStore.Images.Media.DISPLAY_NAME, new SimpleDateFormat( "yyyyMMdd_HHmmss", Locale.US ).format( new Date( ) ) + ".jpg" );
        values.put( MediaStore.Images.Media.MIME_TYPE, "image/*" );

        //저장될 경로 -> /내장 메모리/DCIM/ 에 'AndroidQ' 폴더로 지정
        values.put( MediaStore.Images.Media.RELATIVE_PATH, "DCIM/AndroidQ" );

        Uri u = MediaStore.Images.Media.getContentUri( MediaStore.VOLUME_EXTERNAL );
        Uri uri = getActivity().getContentResolver( ).insert( u, values ); //이미지 Uri를 MediaStore.Images에 저장

        try {
            /*
             ParcelFileDescriptor: 공유 파일 요청 객체
             ContentResolver: 어플리케이션끼리 특정한 데이터를 주고 받을 수 있게 해주는 기술(공용 데이터베이스)
                            ex) 주소록이나 음악 앨범이나 플레이리스트 같은 것에도 접근하는 것이 가능

            getContentResolver(): ContentResolver객체 반환
            */

            ParcelFileDescriptor parcelFileDescriptor = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                parcelFileDescriptor = getActivity().getContentResolver( ).openFileDescriptor( uri, "w", null ); //미디어 파일 열기
            }
            if ( parcelFileDescriptor == null ) return;

            //바이트기반스트림을 이용하여 JPEG파일을 바이트단위로 쪼갠 후 저장
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream( );

            //비트맵 형태 이미지 크기 압축
            bitmap.compress( Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream );
            byte[] b = byteArrayOutputStream.toByteArray( );
            InputStream inputStream = new ByteArrayInputStream( b );

            ByteArrayOutputStream buffer = new ByteArrayOutputStream( );
            int bufferSize = 1024;
            byte[] buffers = new byte[ bufferSize ];

            int len = 0;
            while ( ( len = inputStream.read( buffers ) ) != -1 ) {
                buffer.write( buffers, 0, len );
            }

            byte[] bs = buffer.toByteArray( );
            FileOutputStream fileOutputStream = new FileOutputStream( parcelFileDescriptor.getFileDescriptor( ) );
            fileOutputStream.write( bs );
            fileOutputStream.close( );
            inputStream.close( );
            parcelFileDescriptor.close( );

            getActivity().getContentResolver( ).update( uri, values, null, null ); //MediaStore.Images 테이블에 이미지 행 추가 후 업데이트

        } catch ( Exception e ) {
            e.printStackTrace( );
        }

        values.clear( );
        values.put( MediaStore.Images.Media.IS_PENDING, 0 ); //실행하는 기기에서 앱이 IS_PENDING 값을 1로 설정하면 독점 액세스 권한 획득
        getActivity().getContentResolver( ).update( uri, values, null, null );

    }

    // Uri에서 bisap
    private void getBitmap(Uri selectedImageUri) {
        // 서버로 이미지를 전송하기 위한 비트맵 변환
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // 카메라 인텐트 실행하는 부분
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(), "com.example.app0907.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    // 로그아웃시 프라그먼트1로 가기
    private void FragmentView(int i) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (i) {
            case 1:
                Fragment1 fragment1 = new Fragment1();
                transaction.replace(R.id.fl2, fragment1);
                // 새로고침해서 로그인 버튼으로 출력
                try {
                    // TODO 액티비티 화면 재갱신 시키는 코드
                    Intent intent = getActivity().getIntent();
                    getActivity().finish(); // 현재 액티비티 종료 실시
                    getActivity().overridePendingTransition(0, 0);// 인텐트 애니메이션 없애기
                    startActivity(intent);// 현재 액티비티 재실행 실시
                    getActivity().overridePendingTransition(0, 0);// 인텐트 애니메이션 없애기
                } catch (Exception e) {
                    e.printStackTrace();
                }
                transaction.commit();
                break;

        }
    }


    private String getRealPathFromURI(Uri contentURI) {

        String result;
        Cursor cursor = getContext().getContentResolver().query(contentURI, null, null, null, null);

        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();

        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }

        return result;
    }
}


