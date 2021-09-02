package com.example.yerim;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {   // request for login for user

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://dpfla4013.dothome.co.kr/login.php";   // request url
    private Map<String, String> map;

    public LoginRequest(String userID, String userPW, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userPW", userPW);   // match parameter
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
