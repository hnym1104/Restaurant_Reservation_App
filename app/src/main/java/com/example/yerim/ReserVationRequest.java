package com.example.yerim;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReserVationRequest extends StringRequest {   // request for reservation for user

    final static private String URL = "http://dpfla4013.dothome.co.kr/reservation.php";   // request url
    private Map<String, String> map;

    public ReserVationRequest(String resWho, String resName, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("resWho", resWho);
        map.put("resName", resName);   // match parameter
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
