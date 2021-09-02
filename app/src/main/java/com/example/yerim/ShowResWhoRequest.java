package com.example.yerim;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ShowResWhoRequest extends StringRequest {   // request for get resWho

    final static private String URL = "http://dpfla4013.dothome.co.kr/getreswhoname.php";   // request url
    private Map<String, String> map;

    public ShowResWhoRequest(String resHost, String resName, Response.Listener<String> listener) {

        super(Request.Method.POST, URL, listener, null);
        System.out.println("reqeust1");
        map = new HashMap<>();
        map.put("resHost", resHost);
        map.put("resName", resName);   // match parameter
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
