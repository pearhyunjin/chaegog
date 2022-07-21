package com.example.finalprojectvegan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantItem {
    @SerializedName("restaurant")
    public List<RestaurantData> restaurant;

    @Override
    public String toString(){
        return "TestItem{" +
                "restaurant=" + restaurant +
                '}';
    }
}
