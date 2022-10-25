package com.example.finalprojectvegan;

import com.example.finalprojectvegan.Model.BookmarkItem;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BookmarkApiInterface {
    @GET("VeganBookmarkList.php") // Base URL 이후 상세주소
    Call<BookmarkItem> getBookmarkData();
}
