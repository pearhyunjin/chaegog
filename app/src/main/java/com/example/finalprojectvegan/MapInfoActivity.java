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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
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
    getMapInfoDetailCategory, getMapInfoDetailMenu, getMapInfoDetailDayOff;
    ImageView getMapInfoDetailImage;
    CheckBox getMapInfoBookmark;
    String name, addr, time, dayoff, category, menu, pk, id, image;
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
        getMapInfoDetailImage = findViewById(R.id.map_info_detail_image);
        getMapInfoDetailDayOff = findViewById(R.id.map_info_detail_dayoff);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        addr = intent.getStringExtra("addr");
        time = intent.getStringExtra("time");
        dayoff = intent.getStringExtra("dayOff");
        category = intent.getStringExtra("category");
        menu = intent.getStringExtra("menu");
        bookmark = intent.getBooleanExtra("bookmark", bookmark);
        id = intent.getStringExtra("userID");
        pk = intent.getStringExtra("userPk");
        image = intent.getStringExtra("image");

//        fragBookmark1 = new FragBookmark1();
//        Bundle bundle = new Bundle();
//        bundle.putString("id", id);
//        fragBookmark1.setArguments(bundle);

        getMapInfoDetailName.setText(name);
        getMapInfoDetailAddr.setText(addr);
        getMapInfoDetailTime.setText(time);
        getMapInfoDetailDayOff.setText(dayoff);
        getMapInfoDetailCategory.setText(category);
        getMapInfoDetailMenu.setText(menu);
        Glide.with(this)
                .load(image)
                .apply(new RequestOptions().transform(new CenterCrop(),
                        new RoundedCorners(10)))
                .into(getMapInfoDetailImage);

        getMapInfoBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userPK = pk;
                String userID = id;
                String storeName = name;
                String storeAddr = addr;
                String storeImage =  image;
                String storeTime = time;
                String storeDayOff = dayoff;

                if (getMapInfoBookmark.isChecked()) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    Toast.makeText(getApplicationContext(), "북마크 추가", Toast.LENGTH_SHORT).show();
                                    Log.d("bookmark", userPK);
                                    Log.d("bookmark", userID);
                                    Log.d("bookmark", storeName);
                                    Log.d("bookmark", storeAddr);
                                    Log.d("bookmark", storeImage);
                                    Log.d("bookmark", storeTime);
                                    Log.d("bookmark", storeDayOff);
                                } else {
                                    Toast.makeText(getApplicationContext(), "북마크 실패", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    BookmarkRequest bookmarkRequest = new BookmarkRequest(userPK, userID, storeName, storeAddr, storeImage, storeTime, storeDayOff, responseListener);
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