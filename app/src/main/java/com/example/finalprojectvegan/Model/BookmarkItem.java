package com.example.finalprojectvegan.Model;

import com.example.finalprojectvegan.Model.BookmarkData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookmarkItem {
    @SerializedName("USER_BOOKMARK")
    public List<BookmarkData> USER_BOOKMARK;
}
