package com.example.yerim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class sub_activity extends AppCompatActivity {
    Intent intent;
    int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        setTitle("Login");

        RadioGroup registerSel = findViewById(R.id.registerSelect);
        Button userLoginBtn = findViewById(R.id.userLoginButton);
        Button enterLoginBtn = findViewById(R.id.enterLoginButton);
        Button registerBtn = findViewById(R.id.registerButton);
        EditText userid = findViewById(R.id.userID);
        EditText userpw = findViewById(R.id.userPW);

        registerSel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.registerMember)
                    check = 1;
                else if(checkedId == R.id.registerEnter)
                    check = 2;
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {   // choose user or enterprise
            @Override
            public void onClick(View v) {
                if(check == 1)
                    intent  = new Intent(getApplicationContext(), register.class);
                else if(check == 2)
                    intent = new Intent(getApplicationContext(), register_enter.class);
                else
                    Toast.makeText( getApplicationContext(), "회원가입 종류를 선택하십시오.", Toast.LENGTH_SHORT ).show();

                startActivity(intent);
            }
        });

        userLoginBtn.setOnClickListener(new View.OnClickListener() {   // user login button
            @Override
            public void onClick(View v) {
                final String userID = userid.getText().toString();
                final String userPW = userpw.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {   // get response from DB
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(success) {   // insert DB success
                                String userID = jsonObject.getString("userID");
                                String userName = jsonObject.getString("userName");

                                Toast.makeText(getApplicationContext(), String.format("%s님 환영합니다.", userName), Toast.LENGTH_SHORT).show();
                                intent = new Intent(sub_activity.this, restaurantList.class);

                                intent.putExtra("userID", userID);
                                intent.putExtra("userName", userName);

                                startActivity(intent);   // move to restaurant list page
                            }
                            else
                            {   // insert DB failed
                                Toast.makeText( getApplicationContext(), "이용자 ID/PW를 확인하십시오.", Toast.LENGTH_SHORT ).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // send request to DB
                LoginRequest loginRequest = new LoginRequest(userID, userPW, responseListener);
                RequestQueue queue = Volley.newRequestQueue( sub_activity.this );
                queue.add(loginRequest);
            }
        });

        enterLoginBtn.setOnClickListener(new View.OnClickListener() {   // enterprise login button
            @Override
            public void onClick(View v) {
                final String enterID = userid.getText().toString();
                final String enterPW = userpw.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {   // get response from DB
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(success) {   // insert DB success
                                String enterID = jsonObject.getString("enterID");
                                String enterName = jsonObject.getString("enterName");
                                String enterNum = jsonObject.getString("enterNum");

                                Toast.makeText(getApplicationContext(), String.format("%s님 환영합니다.", enterName), Toast.LENGTH_SHORT).show();
                                intent = new Intent(sub_activity.this, restaurantList_enter.class);

                                intent.putExtra("enterID", enterID);
                                intent.putExtra("enterName", enterName);
                                intent.putExtra("enterNum", enterNum);

                                startActivity(intent);   // move to restaurant list page
                            }
                            else
                            {   // insert DB failed
                                Toast.makeText( getApplicationContext(), "사업자 ID/PW를 확인하십시오.", Toast.LENGTH_SHORT ).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // send request to DB
                LoginRequest_enter loginRequest_enter = new LoginRequest_enter(enterID, enterPW, responseListener);
                RequestQueue queue = Volley.newRequestQueue( sub_activity.this );
                queue.add(loginRequest_enter);
            }

        });
    }
}
