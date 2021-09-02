package com.example.yerim;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class IDCheckRequest extends StringRequest {   // request for check ID duplicate for user
    final static private String URL = "http://dpfla4013.dothome.co.kr/userIDcheck.php";   // request url
    private Map<String, String> map;

    public IDCheckRequest(String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);   // match parameter
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
