package com.example.finalprojectvegan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.lakue.lakuepopupactivity.PopupResult;

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
        Btn_uploadImagePost = findViewById(R.id.Btn_uploadImagePost);

        Btn_uploadPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadImage(bytes);
//                loadImage();
            }
        });

        Btn_uploadImagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY && resultCode == RESULT_OK) {
            uri = data.getData();
            setImage(uri);
        }
    }

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

        StorageReference mountainImageReference = storageReference.child("posts/" + firebaseUser.getUid() + "/postImage" + System.currentTimeMillis() + ".jpg");
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
                    postUpdate(uri);
                    Log.d("성공", "성공" + uri);
                } else {
                    Log.d("실패2", "실패2");
                }
            }
        });
    }

//    public void loadImage() {
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
//        StorageReference storageReference = firebaseStorage.getReference();
//        StorageReference pathReference = storageReference.child("users");
//        if (pathReference == null) {
//            Toast.makeText(this, "저장소에 사진이 없습니다.", Toast.LENGTH_SHORT).show();
//        } else {
//            StorageReference submitProfile = storageReference.child("posts/" + firebaseUser.getUid() + "/postImage" + System.currentTimeMillis() + ".jpg");
//            submitProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
////                    Glide.with(WritePostActivity.this).load(uri).centerCrop().override(300).into(imageView_uploadPost);
//                    Log.e("downloadurl", "downloadurl : " + uri);
//                    Toast.makeText(WritePostActivity.this, "uri : " + uri, Toast.LENGTH_SHORT).show();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(WritePostActivity.this, "실패", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }

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