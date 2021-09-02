package com.example.yerim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RestaurantRegister extends AppCompatActivity {
    Intent intent;
    EditText resName, resLocation, resKind, resPrice, resHost;
    String resname, reslocation, reskind, resprice, reshost;
    Button regBtn;
    String enterName, enterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_register);
        setTitle("New Restaurant Register");

        intent = getIntent();
        enterName = intent.getStringExtra("enterName");
        enterID = intent.getStringExtra("enterID");

        resName = findViewById(R.id.resName);
        resLocation = findViewById(R.id.resLocation);
        resKind = findViewById(R.id.resKind);
        resPrice = findViewById(R.id.resPrice);
        regBtn = findViewById(R.id.registerButton);

        regBtn.setOnClickListener(new View.OnClickListener() {   // click register button
            @Override
            public void onClick(View view) {
                resname = resName.getText().toString();
                reslocation = resLocation.getText().toString();
                reskind = resKind.getText().toString();
                resprice = resPrice.getText().toString();
                reshost = enterID;

                if(resname.equals("") || reslocation.equals("") || reskind.equals("") ||
                    resprice.equals("")) {
                    Toast.makeText(getApplicationContext(), "모든 정보를 입력해주십시오.", Toast.LENGTH_SHORT ).show();
                    return;
                }   // if empty field, print error

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {   // get response from DB
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success)
                            {   // insert DB success
                                Toast.makeText(getApplicationContext(), "식당 등록이 완료되었습니다.", Toast.LENGTH_SHORT ).show();
                                intent = new Intent(getApplicationContext(), restaurantList_enter.class);
                                intent.putExtra("enterName", enterName);
                                intent.putExtra("enterID", enterID);
                                startActivity(intent);
                            }
                            else
                            {   // insert DB failed
                                Toast.makeText(getApplicationContext(), "식당 등록이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // send request to DB
                RestaurantRegisterRequest restaurantRegisterRequest = new RestaurantRegisterRequest(resname, reslocation, reskind, resprice, reshost, responseListener);
                RequestQueue queue = Volley.newRequestQueue( RestaurantRegister.this);
                queue.add(restaurantRegisterRequest);
            }
        });

    }
}