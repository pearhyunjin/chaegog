package com.example.finalprojectvegan;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NaverMapApiInterface {
    @GET("VeganMapStoreFi.php")
    public Call<NaverMapItem> searchAddress(
            @Header("X-NCP-APIGW-API-KEY-ID") String id,
            @Header("X-NCP-APIGW-API-KEY") String pw,
            @Query("query") String query
    );
}
