package com.example.yerim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Reservation extends AppCompatActivity {
    Intent intent;
    String resname, reslocation, resprice, reskind, reshost, reshostname;
    TextView resName, resLocation, resPrice, resKind, resHost;
    String userID, userName;
    Button resBtn, cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        setTitle("Reservation");

        resName = findViewById(R.id.resName);
        resLocation = findViewById(R.id.resLocation);
        resPrice = findViewById(R.id.resPrice);
        resKind = findViewById(R.id.resKind);
        resHost = findViewById(R.id.resHost);
        resBtn = findViewById(R.id.reservationButton);
        cancelBtn = findViewById(R.id.cancelButton);

        intent = getIntent();
        resname = intent.getStringExtra("resName");
        reslocation = intent.getStringExtra("resLocation");
        reskind = intent.getStringExtra("resKind");
        resprice = intent.getStringExtra("resPrice");
        reshost = intent.getStringExtra("resHost");
        userID = intent.getStringExtra("userID");
        userName = intent.getStringExtra("userName");

        resName.setText(resname);
        resLocation.setText(reslocation);
        resKind.setText(reskind);
        resPrice.setText(resprice);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {   // get response from DB
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if(success)
                    {   // get host name success
                        reshostname = jsonObject.getString("enterName");
                        resHost.setText(reshostname + "님의 식당입니다.");
                    }
                    else
                    {   // get host name failed
                        Toast.makeText(getApplicationContext(), "호스트 이름 가져오기 실패.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        // send request to DB
        HostNameRequest hostNameRequest = new HostNameRequest(reshost, responseListener);
        RequestQueue queue = Volley.newRequestQueue( Reservation.this);
        queue.add(hostNameRequest);

        cancelBtn.setOnClickListener(new View.OnClickListener() {   // click cancel buttn
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), String.format("예약을 취소하셨습니다.", userName), Toast.LENGTH_SHORT).show();
                intent = new Intent(Reservation.this, restaurantList.class);

                intent.putExtra("userID", userID);
                intent.putExtra("userName", userName);

                startActivity(intent);   // move to restaurant list for user
            }
        });

        resBtn.setOnClickListener(new View.OnClickListener() {   // reservation button
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {   // get response from DB
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success)
                            {   // insert to DB success
                                String str = userName + "님 " + resname + " 예약이 완료되었습니다.";
                                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), restaurantList.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("userName", userName);
                                startActivity(intent);
                            }
                            else
                            {   // insert to DB failed
                                Toast.makeText(getApplicationContext(), "예약에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // send request to DB
                ReserVationRequest reservationRequest = new ReserVationRequest(userID, resname, responseListener);
                RequestQueue queue = Volley.newRequestQueue( Reservation.this);
                queue.add(reservationRequest);
            }
        });

    }
}