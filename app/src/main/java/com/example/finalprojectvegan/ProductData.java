package com.example.finalprojectvegan;

import com.google.gson.annotations.SerializedName;

public class ProductData {
    @SerializedName("serialNum") // php 파일과 이름 같게
    private int serialNum;
    @SerializedName("productName")
    private String productName;
    @SerializedName("productCompany")
    private String productCompany;
    @SerializedName("productImage")
    private String productImage;

    public String getProductName() {
        return productName;
    }

    public String getProductCompany() {
        return productCompany;
    }

    public String getProductImage() {
        return productImage;
    }
}
