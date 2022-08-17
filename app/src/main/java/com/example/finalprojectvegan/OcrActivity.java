package com.example.finalprojectvegan;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OcrActivity extends AppCompatActivity {

    final private static String TAG = "tag";
    private static final int CAMERA = 100;
    private static final int GALLERY = 101;

    Bitmap bitmap;
    Button cameraBtn;
    Button galleryBtn;
    Button goOcr;
    ImageView ocrImage;
    Intent intent;
    InputImage image;

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);

        ocrImage = findViewById(R.id.ocrimagepopup);
        cameraBtn = findViewById(R.id.cameraBtn);
        galleryBtn = findViewById(R.id.galleryBtn);
        goOcr = findViewById(R.id.goOcr);

        TextRecognizer recognizer = TextRecognition.getClient(new KoreanTextRecognizerOptions.Builder().build());

        // 카메라 권한 설정
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(OcrActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        // ocr 스캔하기 버튼 클릭시
        goOcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 텍스트 인식 함수 실행
                TextRecognition(recognizer);

            }
        });

        // 카메라 버튼 클릭시
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityResultPicture.launch(intent);
            }
        });

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY);
            }
        });

    }

    // 카메라 실행시
    ActivityResultLauncher<Intent> activityResultPicture = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bundle extras = result.getData().getExtras();
                        bitmap = (Bitmap) extras.get("data");
                        ocrImage.setImageBitmap(bitmap);

                        image = InputImage.fromBitmap(bitmap, 0);
                    }
                }
            }
    );


    // 갤러리 실행시
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA && resultCode == RESULT_OK) {

        }
        else if(requestCode == GALLERY && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            setImage(uri);

        }

    }

    // 갤러리 이미지 이미지뷰에
    private void setImage(Uri uri) {
        try{
            InputStream in = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            ocrImage.setImageBitmap(bitmap);

            image = InputImage.fromBitmap(bitmap, 0);
            Log.e("setImage", "이미지 to 비트맵");
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    // 텍스트 인식 실행 결과
    private void TextRecognition(TextRecognizer recognizer) {
        Task<Text> result = recognizer.process(image)
                // 이미지 인식에 성공하면 실행되는 리스너
                .addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text visionText) {
                        Log.e("텍스트 인식", "성공");
                        // Task completed successfully
                        String resultText = visionText.getText();
                        getAlertDialog("[OCR] 사진 인식 결과",
                                String.valueOf(resultText),
                                "확인", "", "");

//                        text_info.setText(resultText);  // 인식한 텍스트를 TextView에 세팅
                    }
                })
                // 이미지 인식에 실패하면 실행되는 리스너
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("텍스트 인식", "실패: " + e.getMessage());
                            }
                        });
    }

    // 팝업창
    public void getAlertDialog(String header, String content, String ok, String no, String normal) {

        //TODO 타이틀 및 내용 표시
        final String Tittle = header;
        final String Message = content;

        //TODO 버튼 이름 정의
        String buttonNo = no;
        String buttonYes = ok;
        String buttonNature = normal;

        //TODO AlertDialog 팝업창 생성
        new AlertDialog.Builder(OcrActivity.this)
                .setTitle(Tittle) //[팝업창 타이틀 지정]
                //.setIcon(R.drawable.tk_app_icon) //[팝업창 아이콘 지정]
                .setMessage(Message) //[팝업창 내용 지정]
                .setCancelable(false) //[외부 레이아웃 클릭시도 팝업창이 사라지지않게 설정]
                .setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                })
                .setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                })
                .setNeutralButton(buttonNature, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                })
                .show();
    }
}