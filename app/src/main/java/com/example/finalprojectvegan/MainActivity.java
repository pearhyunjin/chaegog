package com.example.finalprojectvegan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // 객체 선언
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;

    Fragment fragment_homefeed;
    Fragment fragment_map;
    Fragment fragment_ocr;
    Fragment fragment_mypage;
    Fragment fragment_bookmark;
    Fragment fragment_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // BoredDeveloper 보고 작성한 내용
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser == null) {
            Intent intent = new Intent(MainActivity.this, RegisterStep1Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference documentReference = db.collection("users").document(firebaseUser.getUid());
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot != null) {
                            if (documentSnapshot.exists()) {
                                Log.d("tag", "DocumentSnapshot data : " + documentSnapshot.getData());
                            } else {
                                Log.d("tag", "No such document");
                            }
                        }
                    } else {
                        Log.d("tag", "get failed with", task.getException());
                    }
                }
            });
//            for (UserInfo profile : firebaseUser.getProviderData()) {
//                String name = profile.getDisplayName();
//                if (neame == null) {
//                    Intent intent = new Intent(MainActivity.this, RegisterStep1Activity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                }
//            }
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragment_homefeed = new FragHomeFeed();
//        fragment_map = new FragMap();
//        fragment_ocr = new FragOcr();
        fragment_mypage = new FragMypage();
        fragment_bookmark = new FragBookmark();
        fragment_search = new UserSearchFragment();

        FloatingActionButton fb = findViewById(R.id.flatingbtn);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent postintent = new Intent(MainActivity.this, PostActivity.class);
                startActivity(postintent);
            }
        });


//        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // 초기 플래그먼트 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_homefeed).commitAllowingStateLoss();

        // 바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        // 리스너 등록
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Log.i(TAG, "바텀 네비게이션 클릭");

                switch (item.getItemId()){
                    case R.id.homefeed:
//                        Log.i(TAG, "home 들어옴");
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_homefeed).commitAllowingStateLoss();
//                        Intent postintent = new Intent(MainActivity.this, PostActivity.class);
//                        startActivity(postintent);
                        return true;
                    case R.id.map:
                        // 액티비티로 띄움
                        Intent intent = new Intent(MainActivity.this, MapActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.ocr:
//                        Log.i(TAG, "group 들어옴");
                        Intent ocrintent = new Intent(MainActivity.this, OcrActivity.class);
                        startActivity(ocrintent);
//                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_ocr).commitAllowingStateLoss();
                        return true;
                    case R.id.bookmark:
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_bookmark).commitAllowingStateLoss();
                        return true;
                    case R.id.mypage:
//                        Log.i(TAG, "hotel 들어옴");
                        SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                        editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        editor.apply();

                        Intent mypageintent = new Intent(MainActivity.this, MypageActivity.class);
                        startActivity(mypageintent);

//                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_mypage).commitAllowingStateLoss();
                        return true;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(getApplicationContext(), "검색창 클릭됨", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_search).commitAllowingStateLoss();
                return true;
        }
        return true;
    }
}