package com.example.finalprojectvegan;

import com.google.gson.annotations.SerializedName;

public class RecipeData {
    @SerializedName("recipePK") // php 파일과 이름 같게
    private int recipePK;
    @SerializedName("recipeName")
    private String recipeName;
    @SerializedName("recipeImage")
    private String recipeImage;
    @SerializedName("recipeType")
    private String recipeType;
    @SerializedName("recipeLevel")
    private String recipeLevel;

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public String getRecipeType() {
        return recipeType;
    }

    public String getRecipeLevel() {
        return recipeLevel;
    }
}
