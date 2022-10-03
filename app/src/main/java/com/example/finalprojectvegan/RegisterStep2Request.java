package com.example.finalprojectvegan;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterStep2Request extends StringRequest {

    final static private String URL = "http://baehosting.dothome.co.kr/VeganRegister2Fi.php";
    private Map<String, String> map;

    public RegisterStep2Request(String userVeganCategory, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userVeganCategory", userVeganCategory);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
