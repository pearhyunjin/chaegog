package com.example.finalprojectvegan;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.mlkit.vision.common.InputImage;
import com.lakue.lakuepopupactivity.PopupActivity;
import com.lakue.lakuepopupactivity.PopupGravity;
import com.lakue.lakuepopupactivity.PopupResult;
import com.lakue.lakuepopupactivity.PopupType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

public class WritePostActivity extends AppCompatActivity {

    private static final int GALLERY = 101;

    Button Btn_uploadPost, Btn_uploadImagePost;

    ImageView imageView_uploadPost;

    ProgressDialog dialog;

    Uri uri;

    Bitmap bitmap;
    byte[] bytes;


    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        imageView_uploadPost = findViewById(R.id.imageView_uploadPost);

        Btn_uploadPost = findViewById(R.id.Btn_uploadPost);

        Btn_uploadPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadImage(bytes);
                Toast.makeText(WritePostActivity.this, "게시물이 업로드 되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imageView_uploadPost.setOnClickListener(new View.OnClickListener() {
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
//                        Log.d(TAG, "권한 설정 완료");
                    } else {
//                        Log.d(TAG, "권한 설정 요청");
                        ActivityCompat.requestPermissions(WritePostActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                }

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == GALLERY && resultCode == RESULT_OK) {
//            uri = data.getData();
//            setImage(uri);
//        }

//        if (resultCode == RESULT_OK) {
//            //데이터 받기
//            if (requestCode == 2) {
//                PopupResult result = (PopupResult) data.getSerializableExtra("result");
//                if (result == PopupResult.LEFT) {
//                    // 작성 코드
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    activityResultPicture.launch(intent);
//
//                } else if (result == PopupResult.RIGHT) {
//                    // 작성 코드
//                    Intent intent = new Intent(Intent.ACTION_PICK);
//                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//                    intent.setType("image/*");
//                    startActivityForResult(intent, GALLERY);
//                }
////                else if (requestCode == 0 ) {
////                    String returnValue = data.getStringExtra("some key");
//////                    Log.d("로그 : ", "profilePath : " + profilePath);
////                }
//            }
//            else if (requestCode == 0 ) {
//                String returnValue = data.getStringExtra("some key");
////                    Log.d("로그 : ", "profilePath : " + profilePath);
//            }
//        }
//
//        // 갤러리
//        else if(requestCode == GALLERY && resultCode == RESULT_OK) {
//            Uri uri = data.getData();
//            setImage(uri);
//
//        }

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
                        imageView_uploadPost.setImageBitmap(bitmap);

                        bytes = bitmapToByteArray(bitmap);
//                        image = InputImage.fromBitmap(bitmap, 0);
                    }
                }
            }
    );

//    public static String uri2path(Context context, Uri contentUri) {
//        String[] proj = { MediaStore.Images.Media.DATA };
//
//        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
//        cursor.moveToNext();
//        @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
//        Uri uri = Uri.fromFile(new File(path));
//
//        cursor.close();
//        return path;
//    }

    private void setImage(Uri uri) {
        try{
            InputStream in = getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(in);
//            imageView_uploadPost.setImageBitmap(bitmap);
            Glide.with(WritePostActivity.this).load(uri).override(500).into(imageView_uploadPost);

//            image = InputImage.fromBitmap(bitmap, 0);
            Log.e("uri", "uri : " + uri);

            bytes = bitmapToByteArray(bitmap);

//            uploadImage(bytes);
//
//            loadImage();

//            byte[] bytes = bitmapToByteArray(bitmap);
//
//            uploadImage(bytes);

//            dialog = new ProgressDialog(MypageActivity.this); //프로그레스 대화상자 객체 생성
//            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //프로그레스 대화상자 스타일 원형으로 설정
//            dialog.setMessage("제출 중입니다."); //프로그레스 대화상자 메시지 설정
//            dialog.show(); //프로그레스 대화상자 띄우기

//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable(){
//                @Override
//                public void run() {
//                    dialog.dismiss(); // 3초 시간지연 후 프로그레스 대화상자 닫기
//                    Toast.makeText(WritePostActivity.this, "이미지 저장.", Toast.LENGTH_LONG).show();
//                    loadImage();
//                }
//            }, 5000);

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void uploadImage(byte[] bytes) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            StorageReference imageReference = storageReference.child("posts/" + firebaseUser.getUid() + "/postImage" + System.currentTimeMillis() + ".jpg");
            UploadTask uploadTask = imageReference.putBytes(bytes);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        Log.d("실패", "실패");
                        throw task.getException();
                    }
                    return imageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        uri = task.getResult();
                        postUpdate(uri);
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

    private void postUpdate(Uri uri) {

        final String title = ((EditText) findViewById(R.id.edit_post_title)).getText().toString();
        final String contents = ((EditText) findViewById(R.id.edit_post_contents)).getText().toString();

        if (title.length() > 0 && contents.length() > 0) {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            WritePostInfo writePostInfo = new WritePostInfo(title, contents, firebaseUser.getUid(), uri.toString(), new Date());
            uploader(writePostInfo);
        } else {
            Toast.makeText(this, "게시글 내용을 입력해주세요", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploader (WritePostInfo writePostInfo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").add(writePostInfo)
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