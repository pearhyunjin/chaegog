package com.example.finalprojectvegan;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NaverMapApiInterface {
    @GET("VeganMapStoreFi.php")
    Call<NaverMapItem> getMapData();
}
