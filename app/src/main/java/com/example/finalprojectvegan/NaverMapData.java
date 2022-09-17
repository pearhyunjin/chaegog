package com.example.finalprojectvegan;

import android.widget.Toast;

import com.google.gson.annotations.SerializedName;

public class NaverMapData {
    @SerializedName("serialNum")
    private int serialNum;
    @SerializedName("storeName") // php 파일과 이름이 같아야 함.
    private String storeName;
    @SerializedName("storeCategory")
    private String storeCategory;
    @SerializedName("storeLat")
    private double storeLat;
    @SerializedName("storeLnt")
    private double storeLnt;
    @SerializedName("storePhonenum")
    private String storePhonenum;
    @SerializedName("storeAddr")
    private String storeAddr;
    @SerializedName("storeMenu")
    private String storeMenu;
    @SerializedName("storeImage")
    private String storeImage;
    @SerializedName("storeTime")
    private String storeTime;
    @SerializedName("storeDayoff")
    private String storeDayoff;
    @SerializedName("storeBookmark")
    private boolean storeBookmark;

    public int getSerialNum() {
        return serialNum;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreCategory() {
        return storeCategory;
    }

    public double getStoreLat() {
        return storeLat;
    }

    public double getStoreLnt() {
        return storeLnt;
    }

    public String getStorePhonenum() {
        return storePhonenum;
    }

    public String getStoreAddr() {
        return storeAddr;
    }

    public String getStoreMenu() {
        return storeMenu;
    }

    public String getStoreImage() {
        return storeImage;
    }

    public String getStoreTime() {
        return storeTime;
    }

    public String getStoreDayoff() {
        return storeDayoff;
    }

    public boolean getStoreBookmark() {
        return storeBookmark;
    }
}
