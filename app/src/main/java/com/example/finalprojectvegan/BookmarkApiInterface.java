package com.example.finalprojectvegan;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BookmarkApiInterface {
    @GET("restaurant.php") // Base URL 이후 상세주소
    Call<RestaurantItem>getData();
}
