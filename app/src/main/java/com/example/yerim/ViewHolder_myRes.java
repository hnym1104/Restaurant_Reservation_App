package com.example.yerim;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewHolder_myRes extends RecyclerView.ViewHolder {
    TextView resName, resLocation, resKind, resPrice, resWho, resHost;
    Button cancelBtn;
    Intent intent;
    String userID, userName;

    public ViewHolder_myRes(Context context, View itemView, String userID, String userName) {
        super(itemView);
        resName = itemView.findViewById(R.id.resName);
        resLocation = itemView.findViewById(R.id.resLocation);
        resKind = itemView.findViewById(R.id.resKind);
        resPrice = itemView.findViewById(R.id.resPrice);
        resWho = itemView.findViewById(R.id.resWho);
        resHost = itemView.findViewById(R.id.resHost);
        cancelBtn = itemView.findViewById(R.id.cancelButton);   // get each item view
        this.userID = userID;
        this.userName = userName;

        cancelBtn.setOnClickListener(new View.OnClickListener() {   // click cancel button
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success)
                            {   // if reservation cancel success
                                String str = userName + "님 " + resName.getText().toString() + " 예약취소가 완료되었습니다.";
                                Toast.makeText(context.getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context.getApplicationContext(), restaurantList.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("userName", userName);
                                context.startActivity(intent);   // move go restaurant list page
                            }
                            else
                            {   // if reservation cancel failed
                                Toast.makeText(context.getApplicationContext(), "예약취소에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // send request to DB
                CancelReservationRequest cancelReservationRequest = new CancelReservationRequest(resName.getText().toString(), resHost.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
                queue.add(cancelReservationRequest);
            }
        });
    }
}