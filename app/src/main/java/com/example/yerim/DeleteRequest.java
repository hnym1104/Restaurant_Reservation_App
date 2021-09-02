package com.example.yerim;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLEngineResult;

public class DeleteRequest extends StringRequest {   // request for delete restaurant for enterprise
    final static private String URL = "http://dpfla4013.dothome.co.kr/deletereservation.php";   // request url
    private Map<String, String> map;

    public DeleteRequest(String resName, String resHost, Response.Listener<String> listener) {
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
