package com.example.finalprojectvegan;

import com.google.gson.annotations.SerializedName;

public class BookmarkData {
    @SerializedName("userPK")
    private String userPK;
    @SerializedName("userID")
    private String userID;
    @SerializedName("storeName")
    private String storeName;
    @SerializedName("storeAddr")
    private String storeAddr;
//    @SerializedName("bookMark")
//    private String bookMark;
//    @SerializedName("storeCategory")
//    private String storeCategory;
//    @SerializedName("storeLat")
//    private double storeLat;
//    @SerializedName("storeLnt")
//    private double storeLnt;
//    @SerializedName("storePhonenum")
//    private String storePhonenum;
//    @SerializedName("storeMenu")
//    private String storeMenu;
    @SerializedName("storeImage")
    private String storeImage;
    @SerializedName("storeTime")
    private String storeTime;
    @SerializedName("storeDayOff")
    private String storeDayOff;

    public String getUserPK() {
        return userPK;
    }

    public String getUserID() {
        return userID;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreAddr() {
        return storeAddr;
    }

    public String getStoreImage() {
        return storeImage;
    }

    public String getStoreTime() {
        return storeTime;
    }

    public String getStoreDayoff() {
        return storeDayOff;
    }
}
