package com.example.yerim;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EmailCheckRequest_enter extends StringRequest {   // request for email duplicate check for enterprise
    final static private String URL = "http://dpfla4013.dothome.co.kr/enterEmailcheck.php";   // request url
    private Map<String, String> map;

    public EmailCheckRequest_enter(String enterEmail, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("enterEmail", enterEmail);   // match parameter
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
