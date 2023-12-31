package com.example.finalprojectvegan;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BookmarkRequest extends StringRequest {
    final static private String URL = "http://baehosting.dothome.co.kr/VeganUserBookmark.php";
    private Map<String, String> map;

    public BookmarkRequest(String userPK, String userID, String storeName, String storeAddr, String storeImage, String storeTime, String storeDayOff, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userPK", userPK);
        map.put("userID", userID);
        map.put("storeName", storeName);
        map.put("storeAddr", storeAddr);
        map.put("storeImage", storeImage);
        map.put("storeTime", storeTime);
        map.put("storeDayOff", storeDayOff);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
