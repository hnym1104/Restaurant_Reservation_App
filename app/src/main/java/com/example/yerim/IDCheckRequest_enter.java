package com.example.yerim;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class IDCheckRequest_enter extends StringRequest {   // request for check duplicate ID for enterprise
    final static private String URL = "http://dpfla4013.dothome.co.kr/enterIDcheck.php";   // request url
    private Map<String, String> map;

    public IDCheckRequest_enter(String enterID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("enterID", enterID);   // match parameter
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
