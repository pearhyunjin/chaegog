package com.example.finalprojectvegan;

import com.example.finalprojectvegan.Model.RecipeItem;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeApiInterface {
    @GET("VeganRecipeFi.php") // Base URL 이후 상세주소
    Call<RecipeItem> getRecipeData();
}
