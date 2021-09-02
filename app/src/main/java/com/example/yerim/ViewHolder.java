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

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView resName, resLocation, resKind, resPrice, resWho, resHost;
    Button reservationBtn;
    Intent intent;
    String userID, userName;

    public ViewHolder(Context context, View itemView, String userID, String userName) {
        super(itemView);
        resName = itemView.findViewById(R.id.resName);
        resLocation = itemView.findViewById(R.id.resLocation);
        resKind = itemView.findViewById(R.id.resKind);
        resPrice = itemView.findViewById(R.id.resPrice);
        resWho = itemView.findViewById(R.id.resWho);
        resHost = itemView.findViewById(R.id.resHost);
        reservationBtn = itemView.findViewById(R.id.reservationButton);   // get each item view
        this.userID = userID;
        this.userName = userName;

        reservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   // click reservation button
                String str1 = userName + "님이 이미 예약하셨습니다.";
                String str2 = resName.getText().toString() + " 예약을 시작합니다.";
                String reswho = resWho.getText().toString();
                if(reswho.equals(userID))   // if already my reservation
                    Toast.makeText(context, str1, Toast.LENGTH_SHORT).show();
                else if(reswho.equals("null")){   // no person who reserve
                    Toast.makeText(context, str2, Toast.LENGTH_SHORT).show();
                    intent = new Intent(context.getApplicationContext(), Reservation.class);
                    intent.putExtra("resName", resName.getText().toString());
                    intent.putExtra("resLocation", resLocation.getText().toString());
                    intent.putExtra("resKind", resKind.getText().toString());
                    intent.putExtra("resPrice", resPrice.getText().toString());
                    intent.putExtra("resHost", resHost.getText().toString());
                    intent.putExtra("userID", userID);
                    intent.putExtra("userName", userName);
                    context.startActivity(intent);   // move to reservation page
                }
                else   // other person
                    Toast.makeText(context, "다른 분에게 예약된 식당입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
