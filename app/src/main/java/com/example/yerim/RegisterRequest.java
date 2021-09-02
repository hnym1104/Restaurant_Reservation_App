package com.example.yerim;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {   // request for user register

    // 서버 url 연동
    final static private String URL = "http://dpfla4013.dothome.co.kr/register.php";   // request url
    private Map<String, String> map;

    public RegisterRequest(String userID, String userPW, String userName, String userEmail, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userPW", userPW);
        map.put("userName", userName);
        map.put("userEmail", userEmail);   // match parameter
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
