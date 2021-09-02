package com.example.yerim;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RestaurantRegisterRequest extends StringRequest {   // request for restaurant register for enterprise
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://dpfla4013.dothome.co.kr/restaurantregister.php";
    private Map<String, String> map;

    public RestaurantRegisterRequest(String resName, String resLocation, String resKind,
                                     String resPrice, String resHost,
                                     Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("resName", resName);
        map.put("resLocation", resLocation);
        map.put("resKind", resKind);
        map.put("resPrice", resPrice);
        map.put("resHost", resHost);   // match parameter
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
