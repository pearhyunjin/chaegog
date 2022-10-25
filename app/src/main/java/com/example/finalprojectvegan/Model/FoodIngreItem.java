package com.example.finalprojectvegan.Model;

import com.example.finalprojectvegan.Model.FoodIngreData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodIngreItem {
    @SerializedName("FoodIngredient")
    public List<FoodIngreData> FoodIngredient;
}
