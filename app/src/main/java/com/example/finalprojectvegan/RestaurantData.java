package com.example.finalprojectvegan;

import com.google.gson.annotations.SerializedName;

public class RestaurantData {
    @SerializedName("id")
    private int id;
    @SerializedName("storeName") // php 파일과 이름이 같아야 함.
    private String storeName;
    @SerializedName("storeAddress")
    private String storeAddress;
    @SerializedName("storeImageUrl")
    private String storeImageUrl;
    @SerializedName("storeLat")
    private double storeLat;
    @SerializedName("storeLnt")
    private double storeLnt;

    public String getName() {
        return storeName;
    }

    public String getAddress() {
        return storeAddress;
    }

    public String getImageUrl() {
        return storeImageUrl;
    }

    public double getStoreLat(){
        return  storeLat;
    }

    public double getStoreLnt(){
        return  storeLnt;
    }
}
