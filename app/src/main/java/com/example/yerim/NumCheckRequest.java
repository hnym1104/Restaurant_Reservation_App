package com.example.yerim;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class NumCheckRequest extends StringRequest {   // request for check duplicate enterprise number for enterprise
    final static private String URL = "http://dpfla4013.dothome.co.kr/enterNumcheck.php";
    private Map<String, String> map;

    public NumCheckRequest(String enterNum, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("enterNum", enterNum);   // match parameter
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
