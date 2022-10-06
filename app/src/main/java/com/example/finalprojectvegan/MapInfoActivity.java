package com.example.finalprojectvegan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.RoomDatabase;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MapInfoActivity extends AppCompatActivity {
    private Fragment fragment_info, fragment_menu, fragment_photo, fragment_review;
    private TabLayout tabs;
    String name, addr, time, dayoff, category, menu, pk, id, image,menuFi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_info);

        fragment_info = new MapTabInfo();
        fragment_menu = new MapTabMenu();
        fragment_photo = new MapTabPhoto();
        fragment_review = new MapTabReview();

        tabs = findViewById(R.id.map_info_tabs);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment_info).commitAllowingStateLoss();
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                    if (position == 0)
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment_info).commitAllowingStateLoss();
                    else if (position == 1)
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment_menu).commitAllowingStateLoss();
                    else if (position == 2)
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment_photo).commitAllowingStateLoss();
                    else if (position == 3)
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment_review).commitAllowingStateLoss();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        addr = intent.getStringExtra("addr");
        time = intent.getStringExtra("time");
        dayoff = intent.getStringExtra("dayOff");
        category = intent.getStringExtra("category");
        menu = intent.getStringExtra("menu");
        id = intent.getStringExtra("userID");
        pk = intent.getStringExtra("userPk");
        image = intent.getStringExtra("image");


        String arr[] = menu.split(",");
        for(int i=0; i < arr.length; i++){
            if(menuFi != null){
                menuFi = menuFi + "\n" + arr[i];
            }else{
                menuFi = arr[i];
            }
        }

        Bundle bundle = new Bundle();

        bundle.putString("name", name);
        bundle.putString("image", image);
        bundle.putString("addr", addr);
        bundle.putString("time", time);
        bundle.putString("dayOff", dayoff);
        bundle.putString("category", category);
        bundle.putString("menu", menu);
        bundle.putString("menuFi", menuFi);
        fragment_info.setArguments(bundle);
        fragment_menu.setArguments(bundle);



//        getMapInfoDetailName.setText(name);
//        getMapInfoDetailAddr.setText(addr);
//        getMapInfoDetailTime.setText(time);
//        getMapInfoDetailDayOff.setText(dayoff);
//        getMapInfoDetailCategory.setText(category);
//        getMapInfoDetailMenu.setText(menu);
//        Glide.with(this)
//                .load(image)
//                .apply(new RequestOptions().transform(new CenterCrop(),
//                        new RoundedCorners(10)))
//                .into(getMapInfoDetailImage);
//
//        getMapInfoBookmark.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String userPK = pk;
//                String userID = id;
//                String storeName = name;
//                String storeAddr = addr;
//                String storeImage =  image;
//                String storeTime = time;
//                String storeDayOff = dayoff;
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
//                                    Log.d("bookmark", userPK);
//                                    Log.d("bookmark", userID);
//                                    Log.d("bookmark", storeName);
//                                    Log.d("bookmark", storeAddr);
//                                    Log.d("bookmark", storeImage);
//                                    Log.d("bookmark", storeTime);
//                                    Log.d("bookmark", storeDayOff);
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
//                    BookmarkRequest bookmarkRequest = new BookmarkRequest(userPK, userID, storeName, storeAddr, storeImage, storeTime, storeDayOff, responseListener);
//                    RequestQueue queue = Volley.newRequestQueue(MapInfoActivity.this);
//                    queue.add(bookmarkRequest);
//                } else {
//                    Toast.makeText(MapInfoActivity.this, "북마크 삭제", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        });

    }
}