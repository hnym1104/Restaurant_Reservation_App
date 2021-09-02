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

public class register_enter extends AppCompatActivity {
    EditText enterid, enterpw, entername, enteremail, enternum, enterpwcheck;
    Button registerButton, enterPWCheckBtn, cancelBtn, enterIDCheckBtn, enterEmailCheckBtn, enterNumCheckBtn;
    Boolean pwSame = false, idCheck = false, emailCheck = false, numCheck = false;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_enter);
        setTitle("Enterprise Register");

        enterid = findViewById(R.id.enterID);
        enterpw = findViewById(R.id.enterPW);
        entername = findViewById(R.id.enterName);
        enteremail = findViewById(R.id.enterEmail);
        enternum = findViewById(R.id.enterNum);
        enterpwcheck = findViewById(R.id.enterPWCheck);
        enterPWCheckBtn = findViewById(R.id.enterPWCheckButton);
        registerButton = findViewById(R.id.registerButton);
        enterIDCheckBtn = findViewById(R.id.enterIDCheckButton);
        enterEmailCheckBtn = findViewById(R.id.enterEmailCheckButton);
        enterNumCheckBtn = findViewById(R.id.enterNumCheckButton);
        cancelBtn = findViewById(R.id.cancelButton);

        cancelBtn.setOnClickListener(new View.OnClickListener() {  // if click cancel button, back to login page
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), sub_activity.class);
                startActivity(intent);
            }
        });

        enterIDCheckBtn.setOnClickListener(new View.OnClickListener() {   // ID duplicate button
            @Override
            public void onClick(View v) {
                final String enterID = enterid.getText().toString();
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
                IDCheckRequest_enter idCheckRequest_enter = new IDCheckRequest_enter(enterID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(register_enter.this);
                queue.add(idCheckRequest_enter);
            }
        });

        enterEmailCheckBtn.setOnClickListener(new View.OnClickListener() {   // Email duplicate button
            @Override
            public void onClick(View v) {
                final String enterEmail = enteremail.getText().toString();
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
                EmailCheckRequest_enter emailCheckRequest_enter = new EmailCheckRequest_enter(enterEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(register_enter.this);
                queue.add(emailCheckRequest_enter);
            }
        });

        enterNumCheckBtn.setOnClickListener(new View.OnClickListener() {   // enterprise number duplicate check
            @Override
            public void onClick(View v) {
                final String enterNum = enternum.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {   // get response from DB
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {   // if no same enterprise number
                                Toast.makeText(getApplicationContext(), "사용 가능한 사업자번호입니다.", Toast.LENGTH_SHORT).show();
                                numCheck = true;
                            } else {   // if same enterprise number
                                Toast.makeText(getApplicationContext(), "사용중인 사업자번호입니다.", Toast.LENGTH_SHORT).show();
                                numCheck = false;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // send request to DB
                NumCheckRequest numCheckRequest = new NumCheckRequest(enterNum, responseListener);
                RequestQueue queue = Volley.newRequestQueue(register_enter.this);
                queue.add(numCheckRequest);

            }
        });

        enterPWCheckBtn.setOnClickListener(new View.OnClickListener() {   // PW same button
            @Override
            public void onClick(View v) {
                if (enterpw.getText().toString().equals("") || enterpwcheck.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호 입력 칸을 모두 채우십시오.", Toast.LENGTH_SHORT).show();
                    return;   // if there is empty field
                } else {
                    if (enterpw.getText().toString().equals(enterpwcheck.getText().toString())) {
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
                final String enterID = enterid.getText().toString();
                final String enterPW = enterpw.getText().toString();
                final String enterName = entername.getText().toString();
                final String enterEmail = enteremail.getText().toString();
                final String enterNum = enternum.getText().toString();
                final String enterPWCheck = enterpwcheck.getText().toString();

                if (enterID.equals("") || enterPW.equals("") || enterName.equals("") ||
                        enterEmail.equals("") || enterNum.equals("") || enterPWCheck.equals("")) {
                    Toast.makeText(getApplicationContext(), "모든 정보를 입력하십시오.", Toast.LENGTH_SHORT).show();
                    return;
                }   // if empty field, print error message

                if (!pwSame || !idCheck || !emailCheck || !numCheck) {
                    Toast.makeText(getApplicationContext(), "정보 일치 여부를 확인하십시오.", Toast.LENGTH_SHORT).show();
                    return;
                }   // if duplicate check not finished, print error message

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {   // get response from DB
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {   //  insert DB success
                                Toast.makeText(getApplicationContext(), "사업자 회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                intent = new Intent(getApplicationContext(), sub_activity.class);
                                startActivity(intent);
                            } else {   // inset DB failed
                                Toast.makeText(getApplicationContext(), "사업자 회원가입이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // send request to DB
                RegisterRequest_enter registerRequest_enter = new RegisterRequest_enter(enterID, enterPW,
                        enterName, enterEmail, enterNum, responseListener);
                RequestQueue queue = Volley.newRequestQueue(register_enter.this);
                queue.add(registerRequest_enter);
            }
        });
    }
}