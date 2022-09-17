package com.example.finalprojectvegan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

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
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // 객체 선언
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;

    Fragment fragment_homefeed;
    Fragment fragment_recipe;
    Fragment fragment_product;
    Fragment fragment_mypage;
    Fragment fragment_bookmark;
    Fragment fragment_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragment_homefeed = new FragHomeFeed();
        fragment_recipe = new FragHomeRecipe();
        fragment_product = new FragHomeProduct();
        fragment_mypage = new FragMypage();
        fragment_bookmark = new FragBookmark();


        // 초기 플래그먼트 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_homefeed).commitAllowingStateLoss();

        // 상단 탭 레이아웃
        TabLayout tabLayout = findViewById(R.id.layout_tab);
        tabLayout.addTab(tabLayout.newTab().setText("홈"));
        tabLayout.addTab(tabLayout.newTab().setText("레시피"));
        tabLayout.addTab(tabLayout.newTab().setText("제품"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;
                if(position == 0){
                    selected = fragment_homefeed;
                } else if(position == 1){
                    selected = fragment_recipe;
                } else {
                    selected = fragment_product;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        // 바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // userID 전달
        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");


        // 리스너 등록
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Log.i(TAG, "바텀 네비게이션 클릭");

                switch (item.getItemId()){
                    case R.id.homefeed:
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_homefeed).commitAllowingStateLoss();
//                        Intent postintent = new Intent(MainActivity.this, PostActivity.class);
//                        startActivity(postintent);
                        return true;
                    case R.id.map:
                        // 액티비티로 띄움
                        Intent intent = new Intent(MainActivity.this, MapActivity.class);
                        intent.putExtra("userID", userID);
                        startActivity(intent);
                        return true;
                    case R.id.ocr:
                        Intent ocrintent = new Intent(MainActivity.this, OcrActivity.class);
                        startActivity(ocrintent);
//                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_ocr).commitAllowingStateLoss();
                        return true;
                    case R.id.mypage:
                        SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                        editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        editor.apply();
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_mypage).commitAllowingStateLoss();
                        return true;
                    case R.id.bookmark:
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_bookmark).commitAllowingStateLoss();
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