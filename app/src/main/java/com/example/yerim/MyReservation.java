package com.example.yerim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyReservation extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter_myRes adapter;
    String resName, resLocation, resKind, resPrice, resWho, resHost;
    Intent intent;
    String userName, userID;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservation);
        setTitle("My Reservation");

        intent = getIntent();
        userName = intent.getStringExtra("userName");
        userID = intent.getStringExtra("userID");   // extract information
        backBtn = findViewById(R.id.backButton);

        // make recycler view for scroll
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        String url = "http://dpfla4013.dothome.co.kr/getlist.php";   // request url
        MyReservation.selectDatabase selectDatabase = new MyReservation.selectDatabase(url, null);
        selectDatabase.execute();   // execute asynchronous task

        backBtn.setOnClickListener(new View.OnClickListener() {    // if click back button
            @Override
            public void onClick(View v) {
                intent = new Intent(MyReservation.this, restaurantList.class);

                intent.putExtra("userID", userID);
                intent.putExtra("userName", userName);   // send information

                startActivity(intent);   // move to list page
            }
        });
    }

    class selectDatabase extends AsyncTask<Void, Void, String> {   // to send request for DB

        private String url1;
        private ContentValues values1;
        String result1; // 요청 결과를 저장할 변수.

        public selectDatabase(String url, ContentValues contentValues) {
            this.url1 = url;
            this.values1 = contentValues;
        }

        @Override
        protected String doInBackground(Void... params) {
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result1 = requestHttpURLConnection.request(url1, values1);   // get result from url
            return result1;   // do on onPostExcute
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            doJSONParser(s);   // start parser
        }
    }

    public void doJSONParser(String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONArray jsonArray = jsonObject.getJSONArray("resList");   // get JSON object
            List<restaurant> restaurant = new ArrayList<>();
            for (int i=0; i < jsonArray.length(); i++) {   // get each Object and extract data
                JSONObject output = jsonArray.getJSONObject(i);
                if(output.getString("resWho").equals(userID)) {
                    resName = output.getString("resName");
                    resLocation = output.getString("resLocation");
                    resKind = output.getString("resKind");
                    resPrice = output.getString("resPrice");
                    resWho = output.getString("resWho");
                    resHost = output.getString("resHost");
                    restaurant.add(new restaurant(resName, resLocation, resKind, resPrice, resWho, resHost));   // add data to list
                }
            }
            if(restaurant.size() != 0) {
                adapter = new Adapter_myRes(this, restaurant, userID, userName);   // call adapter and make item view and match
                recyclerView.setAdapter(adapter);   // to show data from adapter item view
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}