package com.example.finalprojectvegan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.RoomDatabase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MapInfoActivity extends AppCompatActivity {
//    public static Context context;
//    public String id;

    TextView getMapInfoDetailName, getMapInfoDetailAddr, getMapInfoDetailTime,
    getMapInfoDetailCategory, getMapInfoDetailMenu;
    CheckBox getMapInfoBookmark;
    String name, addr, time, category, menu, pk, id;
    boolean bookmark;
    FirebaseAuth firebaseAuth;
    FragBookmark1 fragBookmark1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_info);

        firebaseAuth = FirebaseAuth.getInstance();

        getMapInfoDetailName = findViewById(R.id.map_info_detail_name);
        getMapInfoDetailAddr = findViewById(R.id.map_info_detail_addr);
        getMapInfoDetailTime = findViewById(R.id.map_info_detail_time);
        getMapInfoDetailCategory = findViewById(R.id.map_info_detail_category);
        getMapInfoDetailMenu = findViewById(R.id.map_info_detail_menu);
        getMapInfoBookmark = findViewById(R.id.favorite_checkbox);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        addr = intent.getStringExtra("addr");
        time = intent.getStringExtra("time");
        category = intent.getStringExtra("category");
        menu = intent.getStringExtra("menu");
        bookmark = intent.getBooleanExtra("bookmark", bookmark);
        id = intent.getStringExtra("userID");
        pk = intent.getStringExtra("userPk");

        fragBookmark1 = new FragBookmark1();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragBookmark1.setArguments(bundle);

        getMapInfoDetailName.setText(name);
        getMapInfoDetailAddr.setText(addr);
        getMapInfoDetailTime.setText(time);
        getMapInfoDetailCategory.setText(category);
        getMapInfoDetailMenu.setText(menu);

        getMapInfoBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userPk = pk;
                String userID = id;
                String storeName = name;
                String storeAddr = addr;

                if (getMapInfoBookmark.isChecked()) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    Toast.makeText(getApplicationContext(), "북마크 추가", Toast.LENGTH_SHORT).show();
                                    Log.d("bookmark", userPk);
                                    Log.d("bookmark", userID);
                                    Log.d("bookmark", storeName);
                                    Log.d("bookmark", storeAddr);
                                } else {
                                    Toast.makeText(getApplicationContext(), "북마크 실패", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    BookmarkRequest bookmarkRequest = new BookmarkRequest(userPk, userID, storeName, storeAddr, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MapInfoActivity.this);
                    queue.add(bookmarkRequest);
                } else {
                    Toast.makeText(MapInfoActivity.this, "북마크 삭제", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        Intent intent = getIntent();
//        name = intent.getStringExtra("name");
//        addr = intent.getStringExtra("addr");
//        time = intent.getStringExtra("time");
//        category = intent.getStringExtra("category");
//        menu = intent.getStringExtra("menu");
//        bookmark = intent.getBooleanExtra("bookmark", bookmark);
//        id = intent.getStringExtra("userID");
//        pk = intent.getStringExtra("userPk");
//
//        getMapInfoDetailName.setText(name);
//        getMapInfoDetailAddr.setText(addr);
//        getMapInfoDetailTime.setText(time);
//        getMapInfoDetailCategory.setText(category);
//        getMapInfoDetailMenu.setText(menu);
//
//        getMapInfoBookmark.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String userPk = pk;
//                String userID = id;
//                String storeName = name;
//                String storeAddr = addr;
//
//                if (getMapInfoBookmark.isChecked()) {
//                    Response.Listener<String> responseListener = new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            try {
//                                JSONObject jsonObject = new JSONObject(response);
//                                boolean success = jsonObject.getBoolean("success");
//                                if (success) {
//                                    Toast.makeText(getApplicationContext(), "북마크 추가", Toast.LENGTH_SHORT).show();
//                                    Log.d("bookmark", userPk);
//                                    Log.d("bookmark", userID);
//                                    Log.d("bookmark", storeName);
//                                    Log.d("bookmark", storeAddr);
//                                } else {
//                                    Toast.makeText(getApplicationContext(), "북마크 실패", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    };
//
//                    BookmarkRequest bookmarkRequest = new BookmarkRequest(userPk, userID, storeName, storeAddr, responseListener);
//                    RequestQueue queue = Volley.newRequestQueue(MapInfoActivity.this);
//                    queue.add(bookmarkRequest);
//                } else {
//                    Toast.makeText(MapInfoActivity.this, "북마크 삭제", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        });
//
//
//    }
}