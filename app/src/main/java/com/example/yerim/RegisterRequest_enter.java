package com.example.yerim;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest_enter extends StringRequest {   // request for enterprise register

    // 서버 url 연동
    final static private String URL = "http://dpfla4013.dothome.co.kr/register_enter.php";   // request url
    private Map<String, String> map;

    public RegisterRequest_enter(String enterID, String enterPW, String enterName, String enterEmail, String enterNum, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("enterID", enterID);
        map.put("enterPW", enterPW);
        map.put("enterName", enterName);
        map.put("enterEmail", enterEmail);
        map.put("enterNum", enterNum);   // match parameter
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
