package com.example.finalprojectvegan;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ClipData;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lakue.lakuepopupactivity.PopupActivity;
import com.lakue.lakuepopupactivity.PopupGravity;
import com.lakue.lakuepopupactivity.PopupResult;
import com.lakue.lakuepopupactivity.PopupType;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

public class WriteReviewActivity extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    Uri uri;
    Uri uri1;
    Bitmap bitmap;
    byte[] bytes;
    RatingBar ratingBar;
    ImageView imageView_review1, imageView_review2, imageView_review3;
    Button Btn_uploadReview;
    EditText editText_review;
    TextView restaurant_name_review;
    String name;
    final int PICTURE_REQUEST_CODE = 100;
    private static final int GALLERY = 101;
    String rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);


        imageView_review1 = findViewById(R.id.image_review1);
        imageView_review2 = findViewById(R.id.image_review2);
        imageView_review3 = findViewById(R.id.image_review3);
        Btn_uploadReview = findViewById(R.id.Btn_uploadReview);
        editText_review = findViewById(R.id.edit_review);
        restaurant_name_review = findViewById(R.id.restaurant_name_review);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        restaurant_name_review.setText(name);

        Btn_uploadReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadImage(bytes);
                Toast.makeText(WriteReviewActivity.this, "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imageView_review1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getBaseContext(), PopupActivity.class);
                intent.putExtra("type", PopupType.SELECT);
                intent.putExtra("gravity", PopupGravity.CENTER);
                intent.putExtra("title", "사진을 불러올 기능을 선택하세요");
                intent.putExtra("buttonLeft", "카메라");
                intent.putExtra("buttonRight", "갤러리");
                startActivityForResult(intent, 2);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    } else {
                        ActivityCompat.requestPermissions(WriteReviewActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                }

            }
        });
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
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setType("image/*");
                    startActivityForResult(intent, GALLERY);
                }
            } else if (requestCode == GALLERY) {
                Uri uri = data.getData();
                setImage(uri);

            }
        }

    }

    ActivityResultLauncher<Intent> activityResultPicture = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bundle extras = result.getData().getExtras();
                        bitmap = (Bitmap) extras.get("data");
                        imageView_review1.setImageBitmap(bitmap);

                        bytes = bitmapToByteArray(bitmap);
                    }
                }
            }
    );

    private void setImage(Uri uri) {
        try{
            InputStream in = getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(in);
            Glide.with(WriteReviewActivity.this)
                    .load(uri).override(500)
                    .apply(new RequestOptions().transform(new CenterCrop()))
                    .into(imageView_review1);
            Log.e("uri", "uri : " + uri);
            bytes = bitmapToByteArray(bitmap);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    // 다중 이미지 업로드 진행중
//        imageView_review1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.setType("image/*");
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICTURE_REQUEST_CODE);
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                    } else {
//                        ActivityCompat.requestPermissions(WriteReviewActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                    }
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//
//        if(requestCode == PICTURE_REQUEST_CODE){
//            if(resultCode == RESULT_OK){
//                imageView_review1.setImageResource(0);
//                imageView_review2.setImageResource(0);
//                imageView_review3.setImageResource(0);
//
//                uri = data.getData();
//                ClipData clipData = data.getClipData();
//
//                if(clipData != null){
//                    for(int i=0; i<3; i++){
//                        if(i < clipData.getItemCount()){
//                            uri1 = clipData.getItemAt(i).getUri();
//                            switch (i){
//                                case 0:
//                                    imageView_review1.setImageURI(uri1);
//                                    break;
//                                case 1:
//                                    imageView_review2.setImageURI(uri1);
//                                    break;
//                                case 2:
//                                    imageView_review3.setImageURI(uri1);
//                                    break;
//                            }
//                        }
//                    }
//                } else if(uri != null){
//                    imageView_review1.setImageURI(uri);
//                }
//            }
//        }
//    }

    public void uploadImage(byte[] bytes) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            StorageReference mountainImageReference = storageReference.child("review/" + firebaseUser.getUid() + "/reviewImage" + System.currentTimeMillis() + ".jpg");
            UploadTask uploadTask = mountainImageReference.putBytes(bytes);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        Log.d("실패", "실패");
                        throw task.getException();
                    }
                    return mountainImageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        uri = task.getResult();
                        ReviewUpdate(uri);
                        Log.d("성공", "성공" + uri);
                    } else {
                        Log.d("실패2", "실패2");
                    }
                }
            });
        }

    }

    public byte[] bitmapToByteArray( Bitmap bitmap ) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
        bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream) ;
        byte[] byteArray = stream.toByteArray() ;
        return byteArray ;
    }

    private void ReviewUpdate(Uri uri) {

        final String review = ((EditText) findViewById(R.id.edit_review)).getText().toString();

        if (review.length() > 0) {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            ratingBar = findViewById(R.id.ratingBar);
            rating = ratingBar.getRating() + "";
            WriteReviewInfo writeReviewInfo = new WriteReviewInfo(rating,name, review, firebaseUser.getUid(), uri.toString(), new Date());
            uploader(writeReviewInfo);
        } else {
            Toast.makeText(this, "리뷰를 입력해주세요", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploader (WriteReviewInfo writeReviewInfo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("review").add(writeReviewInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}