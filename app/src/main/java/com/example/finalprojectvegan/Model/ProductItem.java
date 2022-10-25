package com.example.finalprojectvegan.Model;

import com.example.finalprojectvegan.Model.ProductData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductItem {
    @SerializedName("PRODUCT")
    public List<ProductData> PRODUCT;
}
