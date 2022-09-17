package com.example.finalprojectvegan;

import com.google.gson.annotations.SerializedName;

public class RecipeData {
    @SerializedName("serialNum") // php 파일과 이름 같게
    private int serialNum;
    @SerializedName("recipeName")
    private String recipeName;
    @SerializedName("recipeType")
    private String recipeType;
}
