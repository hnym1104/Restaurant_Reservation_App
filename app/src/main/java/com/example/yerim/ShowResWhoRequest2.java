package com.example.yerim;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ShowResWhoRequest2 extends StringRequest {   // request for get userName
    final static private String URL = "http://dpfla4013.dothome.co.kr/getreswhoname2.php";   // request url
    private Map<String, String> map;

    public ShowResWhoRequest2(String userID, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("userID", userID);   // match parameter
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
