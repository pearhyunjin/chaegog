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
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;
import com.lakue.lakuepopupactivity.PopupActivity;
import com.lakue.lakuepopupactivity.PopupGravity;
import com.lakue.lakuepopupactivity.PopupResult;
import com.lakue.lakuepopupactivity.PopupType;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    TextView ocrTextView;

    // visibility=gone 상태인것
    TextView n_ingredient_text;
    TextView n_ingredient;
    TextView allergy_text;
    TextView allergy_ingredient;
    TextView recomm_text;
    TextView y_ingredient_text;
    ImageView recomm_image; // 추후 리사이클러뷰로 구현해야함.

    FoodIngreItem foodList;
    List<FoodIngreData>  foodInfo;
    String foodInfoGroup;
    String foodInfoName;
    public String resultText;
    String OcrFoodStr;
    String OcrResultStr;
    boolean checkFit; // flag 변수
    String USER_ID; // 사용자 닉네임
    String USER_TYPE; // 사용자 채식주의 유형


    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);

        ocrImage = findViewById(R.id.ocrimagepopup);
//        cameraBtn = findViewById(R.id.cameraBtn);
//        galleryBtn = findViewById(R.id.galleryBtn);
        goOcr = findViewById(R.id.goOcr);

        n_ingredient_text = findViewById(R.id.n_ingredient_text);
        y_ingredient_text = findViewById(R.id.y_ingredient_text);
        n_ingredient = findViewById(R.id.n_ingredient);
        allergy_text = findViewById(R.id.allergy_text);
        allergy_ingredient = findViewById(R.id.allergy_ingredient);
        recomm_text = findViewById(R.id.recomm_text);
        recomm_image = findViewById(R.id.recomm_image);


        Intent mainIntent = getIntent();
        USER_ID = mainIntent.getStringExtra("userID");

        TextRecognizer recognizer = TextRecognition.getClient(new KoreanTextRecognizerOptions.Builder().build());

        Intent intent = new Intent(getBaseContext(), PopupActivity.class);
        intent.putExtra("type", PopupType.SELECT);
        intent.putExtra("gravity", PopupGravity.CENTER);
        intent.putExtra("title", "사진을 불러올 기능을 선택하세요");
//                intent.putExtra("content", "Popup Activity was made by Lakue");
        intent.putExtra("buttonLeft", "카메라");
        intent.putExtra("buttonRight", "갤러리");
        startActivityForResult(intent, 2);

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
                // ImageView에 이미지가 있으면
                if(ocrImage.getDrawable() != null){
                    // 텍스트 인식 함수 실행
                    TextRecognition(recognizer);
                } else {
                    Toast.makeText(OcrActivity.this, "이미지를 넣어주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 카메라 버튼 클릭시
//        cameraBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                activityResultPicture.launch(intent);
//            }
//        });
//
//        galleryBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                intent = new Intent(Intent.ACTION_PICK);
//                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//                intent.setType("image/*");
//                startActivityForResult(intent, GALLERY);
//            }
//        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //데이터 받기
            if (requestCode == 2) {
                PopupResult result = (PopupResult) data.getSerializableExtra("result");
                if (result == PopupResult.LEFT) {
                    // 작성 코드
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    activityResultPicture.launch(intent);

                } else if (result == PopupResult.RIGHT) {
                    // 작성 코드
                    intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setType("image/*");
                    startActivityForResult(intent, GALLERY);
                }
            }

            else if(requestCode == GALLERY) {
                Uri uri = data.getData();
                setImage(uri);

            }
        }

        // 갤러리
//        else if(requestCode == GALLERY && resultCode == RESULT_OK) {
//            Uri uri = data.getData();
//            setImage(uri);
//
//        }
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
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == CAMERA && resultCode == RESULT_OK) {
//
//        }
//        else if(requestCode == GALLERY && resultCode == RESULT_OK) {
//            Uri uri = data.getData();
//            setImage(uri);
//
//        }
//
//    }

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
                        resultText = visionText.getText();

//                        getAlertDialog("[OCR] 사진 인식 결과",
//                                String.valueOf(resultText),
//                                "확인", "", "");

                        compare();
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

    // 비교
    public void compare(){
        checkFit = true;
        ocrTextView = findViewById(R.id.ocrTextView);
        USER_TYPE = "페스코";
        // 부적합한 원재료명을 넣을 리스트
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        FoodIngreApiInterface apiInterface = NaverMapRequest.getClient().create(FoodIngreApiInterface.class);
        Call<FoodIngreItem> call = apiInterface.getFoodIngredientData();
        call.enqueue(new Callback<FoodIngreItem>(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<FoodIngreItem> call, Response<FoodIngreItem> response) {
                foodList = response.body();
                foodInfo = foodList.FoodIngredient;

                for(int i=0; i < foodInfo.size(); i++){
                    foodInfoGroup = foodInfo.get(i).getFoodGroup();
                    foodInfoName = foodInfo.get(i).getFoodName();
                    String foodNameArr[] = foodInfoName.split(",");
                    String resultArr[] = resultText.split(",");
                    int arrSize = foodNameArr.length;
                    int resultSize = resultArr.length;

                    switch (USER_TYPE){
                        case "비건":
                            if(foodInfoGroup.equals("육류 및 그 제품") || foodInfoGroup.equals("어패류 및 그 제품") || foodInfoGroup.equals("우유 및 그 제품") || foodInfoGroup.equals("난류")){
                                for(int j=0; j < resultSize; j++) {
                                    OcrResultStr = resultArr[j].trim();
                                    for (int k = 0; k < arrSize; k++) {
                                        OcrFoodStr = foodNameArr[k].trim();
                                        if (OcrResultStr.equals(OcrFoodStr)) {
                                            checkFit = false;
                                        }
                                    }
                                }
                            }
                            break;
                        case "락토":
                            if(foodInfoGroup.equals("육류 및 그 제품") || foodInfoGroup.equals("어패류 및 그 제품") || foodInfoGroup.equals("난류"))  {
                                for(int j=0; j < resultSize; j++) {
                                    OcrResultStr = resultArr[j].trim();
                                    for (int k = 0; k < arrSize; k++) {
                                        OcrFoodStr = foodNameArr[k].trim();
                                        if (OcrResultStr.equals(OcrFoodStr)) {
                                            checkFit = false;
                                        }
                                    }
                                }
                            }
                        case "오보":
                            if(foodInfoGroup.equals("육류 및 그 제품") || foodInfoGroup.equals("어패류 및 그 제품") || foodInfoGroup.equals("우유 및 그 제품")){
                                for(int j=0; j < resultSize; j++) {
                                    OcrResultStr = resultArr[j].trim();
                                    for (int k = 0; k < arrSize; k++) {
                                        OcrFoodStr = foodNameArr[k].trim();
                                        if (OcrResultStr.equals(OcrFoodStr)) {
                                            checkFit = false;
                                        }
                                    }
                                }
                            }
                        case "락토오보":
                            if(foodInfoGroup.equals("육류 및 그 제품") || foodInfoGroup.equals("어패류 및 그 제품")) {
                                for(int j=0; j < resultSize; j++) {
                                    OcrResultStr = resultArr[j].trim();
                                    for (int k = 0; k < arrSize; k++) {
                                        OcrFoodStr = foodNameArr[k].trim();
                                        if (OcrResultStr.equals(OcrFoodStr)) {
                                            checkFit = false;
                                        }
                                    }
                                }
                            }
                            break;
                        case "페스코":
                            if(foodInfoGroup.equals("육류 및 그 제품")) {
                                for(int j=0; j < resultSize; j++) {
                                    OcrResultStr = resultArr[j].trim();
                                    for (int k = 0; k < arrSize; k++) {
                                        OcrFoodStr = foodNameArr[k].trim();
                                        if (OcrResultStr.equals(OcrFoodStr)) {
                                            list1.add(OcrFoodStr);
                                            list2.add(OcrResultStr);
                                            list1.retainAll(list2);
                                            checkFit = false;
                                        }
                                    }
                                }
                            }
                            break;
//                        case "폴로":
//                            break;
                        default:

                    }

                }

                List<String> newList = list1.stream().distinct().collect(Collectors.toList());
                String n_ingre1 = newList.toString().replace("[","").replace("]","");

;                if(!checkFit){
                    Log.e("OCRTEST", resultText + " - 채식유형에 부적합합니다.");
                    ocrTextView.setText(USER_ID + "님의 채식 유형에 맞지않는 제품입니다.");
                    ocrTextView.setTextSize(20);
                    n_ingredient.setText(n_ingre1);

                    // 숨기기
                    ocrImage.setVisibility(View.GONE);
//                    galleryBtn.setVisibility(View.GONE);
//                    cameraBtn.setVisibility(View.GONE);
                    goOcr.setVisibility(View.GONE);

                    // 보여주기
                    n_ingredient_text.setVisibility(View.VISIBLE);
                    n_ingredient.setVisibility(View.VISIBLE);
                    allergy_text.setVisibility(View.VISIBLE);
                    allergy_ingredient.setVisibility(View.VISIBLE);
                    recomm_text.setVisibility(View.VISIBLE);
                    recomm_image.setVisibility(View.VISIBLE);

                }else{
                    Log.e("OCRTEST", resultText + " - 채식유형에 적합합니다.");
                    y_ingredient_text.setText(USER_ID + "님의 채식 유형에\n적합한 제품입니다.");
                    y_ingredient_text.setVisibility(View.VISIBLE);

                    // 숨기기
                    ocrTextView.setVisibility(View.GONE);
                    ocrImage.setVisibility(View.GONE);
//                    galleryBtn.setVisibility(View.GONE);
//                    cameraBtn.setVisibility(View.GONE);
                    goOcr.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<FoodIngreItem> call, Throwable t) {
                Log.d("FoodIngreItem", t.toString());
            }
        });
    }
}