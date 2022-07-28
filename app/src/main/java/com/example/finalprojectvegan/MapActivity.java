package com.example.finalprojectvegan;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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

        String id;
        String pw;
        String addr;
        String query;

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_map);

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

            // 지도에 마커 표시
//            Marker marker = new Marker();
//            marker.setPosition(new LatLng(37.5670135, 126.9783740));
//            marker.setMap(naverMap);

            id = "79m1y0thya";
            pw = "SAlrMPqRmVSqVXEUEQ0wIUSBep3J0E6bSZZIhOF9";
            try {
                query = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode=" + URLEncoder.encode(addr,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            NaverMapApiInterface naverMapApiInterface = NaverMapRequest.getClient().create(NaverMapApiInterface.class);
            Call<NaverMapItem> call = naverMapApiInterface.searchAddress(id, pw, query);
            call.enqueue(new Callback<NaverMapItem>() {
                @Override
                public void onResponse(Call<NaverMapItem> call, Response<NaverMapItem> response) {
                    naverMapList = response.body();
                    naverMapInfo = naverMapList.mapStoreInfo;
                    addr = naverMapInfo.get(0).getStoreAddr();

                }

                @Override
                public void onFailure(Call<NaverMapItem> call, Throwable t) {
                    Log.d(TAG, t.toString());
                }

            });


            // NaverMap 객체를 받아 NaverMap 객체에 위치 소스 지정
            mNaverMap = naverMap;
            mNaverMap.setLocationSource(mLocationSource);

            // 권한 확인, onRequestPermissionsResult 콜백 메서드 호출
            ActivityCompat.requestPermissions(this, PERMISSION, PERMISSION_REQUEST_CODE);
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            // request code와 권한획득 여부 확인
            if (requestCode == PERMISSION_REQUEST_CODE) {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
                }
            }
        }
    }