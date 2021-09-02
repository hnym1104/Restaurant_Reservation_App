package com.example.yerim;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewHolder_enter_myRes extends RecyclerView.ViewHolder {
    TextView resName, resLocation, resKind, resPrice, resWho, resHost;
    Button checkResWhoBtn, delBtn;
    Intent intent;
    String userID, userName, enterName;

    public ViewHolder_enter_myRes(Context context, View itemView, String userID, String userName) {
        super(itemView);
        resName = itemView.findViewById(R.id.resName);
        resLocation = itemView.findViewById(R.id.resLocation);
        resKind = itemView.findViewById(R.id.resKind);
        resPrice = itemView.findViewById(R.id.resPrice);
        resWho = itemView.findViewById(R.id.resWho);
        resHost = itemView.findViewById(R.id.resHost);
        checkResWhoBtn = itemView.findViewById(R.id.checkResWho);
        delBtn = itemView.findViewById(R.id.deleteButton);   // get each item view

        this.userID = userID;
        this.userName = userName;

        checkResWhoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   // click show resWho button
                intent = new Intent(context.getApplicationContext(), ShowResWho.class);
                intent.putExtra("resHost", resHost.getText().toString());
                intent.putExtra("resName", resName.getText().toString());
                context.startActivity(intent);   // go to show resWho popup page
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener(){   // click delete button
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {   // get response from DB
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            String str = resName.getText().toString() + " 삭제가 완료되었습니다.";
                            if (success) {   // if delete success
                                Toast.makeText(context.getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                                intent = new Intent(context.getApplicationContext(), restaurantList_enter.class);
                                intent.putExtra("enterID", resHost.getText().toString());
                                intent.putExtra("enterName", userName);

                                context.startActivity(intent);   // move to restaurant list page
                            } else {   // delete failed
                                Toast.makeText(context.getApplicationContext(), "삭제 실패", Toast.LENGTH_SHORT).show();
                                
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // send request to DB
                DeleteRequest deleteRequest = new DeleteRequest(resName.getText().toString(), resHost.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
                queue.add(deleteRequest);
            }
        });
    }
}
