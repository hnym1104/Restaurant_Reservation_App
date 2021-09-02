package com.example.yerim;

import androidx.appcompat.app.AlertDialog;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class register extends AppCompatActivity {
    EditText userid, userpw, username, useremail, userpwcheck;
    Button registerButton, userPWCheckBtn, cancelButton, userIDCheckBtn, userEmailCheckBtn;
    Boolean pwSame = false, idCheck = false, emailCheck = false;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("User Register");

        userid = findViewById(R.id.userID);
        userpw = findViewById(R.id.userPW);
        username = findViewById(R.id.userName);
        useremail = findViewById(R.id.userEmail);
        userpwcheck = findViewById(R.id.userPWCheck);
        registerButton = findViewById(R.id.registerButton);
        userPWCheckBtn = findViewById(R.id.userPWCheckButton);
        cancelButton = findViewById(R.id.cancelButton);
        userIDCheckBtn = findViewById(R.id.userIDCheckButton);
        userEmailCheckBtn = findViewById(R.id.userEmailCheckButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {   // if click cancel button, back to login page
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), sub_activity.class);
                startActivity(intent);
            }
        });

        userEmailCheckBtn.setOnClickListener(new View.OnClickListener() {   // Email duplicate button
            @Override
            public void onClick(View v) {
                final String userEmail = useremail.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {   // get response from DB
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {   // if no same Email
                                Toast.makeText(getApplicationContext(), "사용 가능한 Email입니다.", Toast.LENGTH_SHORT).show();
                                emailCheck = true;
                            } else {   // if same Email
                                Toast.makeText(getApplicationContext(), "사용중인 Email입니다.", Toast.LENGTH_SHORT).show();
                                emailCheck = false;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // send request to DB
                EmailCheckRequest emailCheckRequest = new EmailCheckRequest(userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(register.this);
                queue.add(emailCheckRequest);
            }
        });

        userIDCheckBtn.setOnClickListener(new View.OnClickListener() {   // ID duplicate button
            @Override
            public void onClick(View v) {
                final String userID = userid.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {   // get response from DB
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {   // if no same ID
                                Toast.makeText(getApplicationContext(), "사용 가능한 ID입니다.", Toast.LENGTH_SHORT).show();
                                idCheck = true;
                            } else {   // if same ID
                                Toast.makeText(getApplicationContext(), "사용중인 ID입니다.", Toast.LENGTH_SHORT).show();
                                idCheck = false;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // send request to DB
                IDCheckRequest idCheckRequest = new IDCheckRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(register.this);
                queue.add(idCheckRequest);
            }
        });

        userPWCheckBtn.setOnClickListener(new View.OnClickListener() {   // PW same button
            @Override
            public void onClick(View v) {
                if (userpw.getText().toString().equals("") || userpwcheck.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호 입력 칸을 모두 채우십시오.", Toast.LENGTH_SHORT).show();
                    return;   // if there is empty field
                } else {
                    if (userpw.getText().toString().equals(userpwcheck.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치합니다.", Toast.LENGTH_SHORT).show();
                        pwSame = true;   // if same password
                    } else {
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        pwSame = false;   // if no same password
                    }
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {   // register button
            @Override
            public void onClick(View view) {
                final String userID = userid.getText().toString();
                final String userPW = userpw.getText().toString();
                final String userName = username.getText().toString();
                final String userEmail = useremail.getText().toString();
                final String userPWCheck = userpwcheck.getText().toString();

                if (userID.equals("") || userPW.equals("") || userName.equals("") ||
                        userEmail.equals("") || userPWCheck.equals("")) {
                    Toast.makeText(getApplicationContext(), "모든 정보를 입력하십시오.", Toast.LENGTH_SHORT).show();
                    return;
                }   // if there is empty field, print error message

                if(!pwSame || !idCheck || !emailCheck)
                {
                    Toast.makeText(getApplicationContext(), "정보 일치 여부를 확인하십시오.", Toast.LENGTH_SHORT).show();
                    return;
                }   //  if check duplicate not finished, print error message

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {   // get reponse from DB
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {   // insert DB success
                                Toast.makeText(getApplicationContext(), "이용자 회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                intent = new Intent(getApplicationContext(), sub_activity.class);
                                startActivity(intent);
                            } else {   // inset DB false
                                Toast.makeText(getApplicationContext(), "이용자 회원가입이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // send request to DB
                RegisterRequest registerRequest = new RegisterRequest(userID, userPW, userName, userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(register.this);
                queue.add(registerRequest);
            }
        });
    }
}