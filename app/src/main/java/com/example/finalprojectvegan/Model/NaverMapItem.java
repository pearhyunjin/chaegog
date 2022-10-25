package com.example.finalprojectvegan.Model;

import com.example.finalprojectvegan.Model.NaverMapData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NaverMapItem {
    @SerializedName("MAPSTOREINFO")
    public List<NaverMapData> MAPSTOREINFO;
}
