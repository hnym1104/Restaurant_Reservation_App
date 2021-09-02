package com.example.yerim;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ShowResWho extends Activity {
    TextView resWho;
    String resHost, resName;
    Intent intent;
    Button closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_res_who);

        intent = getIntent();
        resHost = intent.getStringExtra("resHost");
        resName = intent.getStringExtra("resName");

        resWho = findViewById(R.id.resWho);
        closeBtn = findViewById(R.id.closeButton);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {   // get response from DB
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    String reswho = jsonObject.getString("resWho");
                    String str = reswho + "님이 예약하셨습니다.";
                    if (success) {   // get resWho success
                        if (reswho.equals("null"))   // if no resWho
                            resWho.setText("아직 예약자가 없습니다.");
                        else {
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {   // get response from DB
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success");
                                        String username = jsonObject.getString("userName");
                                        String str = username + "님이 예약하셨습니다.";
                                        if (success) {   // get userName success
                                            resWho.setText(str);
                                        } else {   // get userName failed
                                            Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            // send request to DB
                            ShowResWhoRequest2 showResWhoRequest2 = new ShowResWhoRequest2(reswho, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(ShowResWho.this);
                            queue.add(showResWhoRequest2);
                        }
                    } else {   // get resWho failed
                        Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        // send request to DB
        ShowResWhoRequest showResWhoRequest = new ShowResWhoRequest(resHost, resName, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ShowResWho.this);
        queue.add(showResWhoRequest);
    }
}