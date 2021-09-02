package com.example.yerim;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CancelReservationRequest extends StringRequest {   // request for cancel reservation for user
    final static private String URL = "http://dpfla4013.dothome.co.kr/cancelreservation.php";   // request url
    private Map<String, String> map;

    public CancelReservationRequest(String resName, String resHost, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("resName", resName);
        map.put("resHost", resHost);   // match parameter
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
