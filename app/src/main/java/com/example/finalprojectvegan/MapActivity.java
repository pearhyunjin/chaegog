package com.example.finalprojectvegan;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
        private static final String TAG = "MapActivity";
        private static final int PERMISSION_REQUEST_CODE = 100;
        private static final String[] PERMISSION = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        private FusedLocationSource mLocationSource;
        private NaverMap mNaverMap;

        private NaverMapItem naverMapList;
        private List<NaverMapData> naverMapInfo;

        double lat, lnt;
        String mapInfoName, mapInfoAddr, mapInfoTime, mapInfoDayoff,
                mapInfoImage, mapInfoCategory, mapInfoMenu, userID, userPk;
        boolean mapInfoBookmark;

        TextView getMapInfoName, getMapInfoAddr, getMapInfoTime, getMapInfoDayoff;
        ImageView getMapInfoImage;
        ImageButton mapInfoButton;
        LinearLayout mapInfoLayout;
        CheckBox getMapInfoBookmark;


        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_map);


            // 식당 정보가 출력되는 곳
            getMapInfoName = findViewById(R.id.map_info_name);
            getMapInfoAddr = findViewById(R.id.map_info_addr);
            getMapInfoTime = findViewById(R.id.map_info_time);
            getMapInfoDayoff = findViewById(R.id.map_info_day_off);
            getMapInfoImage = findViewById(R.id.map_info_image);
            getMapInfoBookmark = findViewById(R.id.favorite_checkbox);
            mapInfoButton = findViewById(R.id.map_info_button);

            Intent intent = getIntent();
            userID = intent.getStringExtra("userID");

            FragmentManager fm = getSupportFragmentManager();
            MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map_fragment);
            if (mapFragment == null) {
                mapFragment = MapFragment.newInstance();
                fm.beginTransaction().add(R.id.map_fragment, mapFragment).commit();
            }

            mapFragment.getMapAsync(this);

            mLocationSource =
                    new FusedLocationSource(this, PERMISSION_REQUEST_CODE);
        }

        @Override
        public void onMapReady (@NonNull NaverMap naverMap){
            Log.d(TAG, "onMapReady");

            mapInfoLayout = findViewById(R.id.map_info_layout);
            Fragment fragment_map_info = new MapTabInfo();

            NaverMapApiInterface naverMapApiInterface = NaverMapRequest.getClient().create(NaverMapApiInterface.class);
            Call<NaverMapItem> call = naverMapApiInterface.getMapData();
            call.enqueue(new Callback<NaverMapItem>() {
                             @Override
                             public void onResponse(Call<NaverMapItem> call, Response<NaverMapItem> response) {
                                 naverMapList = response.body();
                                 naverMapInfo = naverMapList.MAPSTOREINFO;

                                 // 마커 여러개 찍기
                                 for(int i=0; i < naverMapInfo.size(); i++){
                                     Marker[] markers = new Marker[naverMapInfo.size()];

                                     markers[i] = new Marker();
                                     lat = naverMapInfo.get(i).getStoreLat();
                                     lnt = naverMapInfo.get(i).getStoreLnt();
                                     markers[i].setPosition(new LatLng(lat, lnt));
                                     markers[i].setCaptionText(naverMapInfo.get(i).getStoreName());
                                     markers[i].setMap(naverMap);

                                     int finalI = i;
                                     markers[i].setOnClickListener(new Overlay.OnClickListener() {
                                         @Override
                                         public boolean onClick(@NonNull Overlay overlay)
                                         {
                                             // DB에서 차례대로 정보 받아오기
                                             mapInfoName = naverMapInfo.get(finalI).getStoreName();
                                             mapInfoAddr = naverMapInfo.get(finalI).getStoreAddr();
                                             mapInfoTime = naverMapInfo.get(finalI).getStoreTime();
                                             mapInfoDayoff = naverMapInfo.get(finalI).getStoreDayOff();
                                             mapInfoImage = naverMapInfo.get(finalI).getStoreImage();
                                             mapInfoCategory = naverMapInfo.get(finalI).getStoreCategory();
                                             mapInfoMenu = naverMapInfo.get(finalI).getStoreMenu();
                                             mapInfoBookmark = naverMapInfo.get(finalI).getStoreBookmark();
                                             userPk = userID + finalI;

                                             mapInfoButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    Intent intent = new Intent(MapActivity.this, MapInfoActivity.class);
                                                    intent.putExtra("name", mapInfoName);
                                                    intent.putExtra("image", mapInfoImage);
                                                    intent.putExtra("addr", mapInfoAddr);
                                                    intent.putExtra("time", mapInfoTime);
                                                    intent.putExtra("dayOff", mapInfoDayoff);
                                                    intent.putExtra("category", mapInfoCategory);
                                                    intent.putExtra("menu", mapInfoMenu);
                                                    intent.putExtra("userID", userID);
                                                    intent.putExtra("userPk", userPk);
                                                    startActivity(intent);

                                                }
                                             });

                                             // 받아온 데이터로 TextView 내용 변경
                                             getMapInfoName.setText(mapInfoName);
                                             getMapInfoAddr.setText(mapInfoAddr);
                                             getMapInfoTime.setText(mapInfoTime);
                                             getMapInfoDayoff.setText(mapInfoDayoff);
                                             startLoadingImage();

                                             // visibility가 gone으로 되어있던 정보창 레이아웃을 visible로 변경
                                             mapInfoLayout.setVisibility(View.VISIBLE);

                                             return false;

                                         }
                                     });

                                 }
                             }

                             @Override
                             public void onFailure(Call<NaverMapItem> call, Throwable t) {

                             }
                         });


            // NaverMap 객체를 받아 NaverMap 객체에 위치 소스 지정
            mNaverMap = naverMap;
            mNaverMap.setLocationSource(mLocationSource);

//            naverMap.addOnLocationChangeListener(location ->
//                    Toast.makeText(this,
//                            location.getLatitude() + ", " + location.getLongitude(),
//                            Toast.LENGTH_SHORT).show());

            UiSettings uiSettings = mNaverMap.getUiSettings();
            uiSettings.setCompassEnabled(true);
            uiSettings.setScaleBarEnabled(true);
            uiSettings.setZoomControlEnabled(true);
            uiSettings.setLocationButtonEnabled(true);

            // 권한 확인, onRequestPermissionsResult 콜백 메서드 호출
            ActivityCompat.requestPermissions(this, PERMISSION, PERMISSION_REQUEST_CODE);
        }

        // 이미지 가져오기
        private void startLoadingImage() {
            Glide.with(this)
                    .load(mapInfoImage)
                    .override(400,400)
                    .apply(new RequestOptions().transform(new CenterCrop(),
                            new RoundedCorners(20)))
                    .into(getMapInfoImage);
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            // request code와 권한획득 여부 확인
//            if (requestCode == PERMISSION_REQUEST_CODE) {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
//                }
//            }
            if (mLocationSource.onRequestPermissionsResult(
                    requestCode, permissions, grantResults)) {
                if (!mLocationSource.isActivated()) { // 권한 거부됨
                    mNaverMap.setLocationTrackingMode(LocationTrackingMode.None);
                }
                return;
            }
            super.onRequestPermissionsResult(
                    requestCode, permissions, grantResults);
        }
    }