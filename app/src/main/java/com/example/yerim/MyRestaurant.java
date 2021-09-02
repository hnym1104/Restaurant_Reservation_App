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

public class MyRestaurant extends AppCompatActivity {
    RecyclerView recyclerView;
    Adpater_enter_myRes adapter;
    String resName, resLocation, resKind, resPrice, resWho, resHost;
    Intent intent;
    String enterName, enterID;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_restaurant);
        setTitle("My Restaurant");

        intent = getIntent();
        enterName = intent.getStringExtra("enterName");
        enterID = intent.getStringExtra("enterID");   // extract information
        backBtn = findViewById(R.id.backButton);

        // make recycler view for scroll
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        String url = "http://dpfla4013.dothome.co.kr/getlist.php";   // request url
        MyRestaurant.selectDatabase selectDatabase = new MyRestaurant.selectDatabase(url, null);
        selectDatabase.execute();   // execute asynchronous task

        backBtn.setOnClickListener(new View.OnClickListener(){   // if click back button
            @Override
            public void onClick(View v) {
                intent = new Intent(MyRestaurant.this, restaurantList_enter.class);

                intent.putExtra("enterID", enterID);
                intent.putExtra("enterName", enterName);   // send information

                startActivity(intent);   // back to list page
            }
        });
    }

    class selectDatabase extends AsyncTask<Void, Void, String> {   // to send request for DB

        private String url1;
        private ContentValues values1;
        String result1;

        public selectDatabase(String url, ContentValues contentValues) {
            this.url1 = url;
            this.values1 = contentValues;
        }

        @Override
        protected String doInBackground(Void... params) {
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result1 = requestHttpURLConnection.request(url1, values1);   // get reuslt from url
            return result1;   // do on onPostExecute
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
                if(output.getString("resHost").equals(enterID)) {
                    try {
                        resName = output.getString("resName");
                    } catch (NullPointerException e) {
                        resName = "";
                    }
                    try {
                        resLocation = output.getString("resLocation");
                    } catch(NullPointerException e) {
                        resLocation = "";
                    }
                    try {
                        resKind = output.getString("resKind");
                    } catch(NullPointerException e) {
                        resKind = "";
                    }
                    try {
                        resPrice = output.getString("resPrice");
                    } catch (NullPointerException e) {
                        resPrice ="";
                    }
                    try {
                        resWho = output.getString("resWho");
                    }catch (NullPointerException e) {
                        resWho = "";
                    }
                    try {
                        resHost = output.getString("resHost");
                    } catch (NullPointerException e) {
                        resHost = "";
                    }
                    restaurant.add(new restaurant(resName, resLocation, resKind, resPrice, resWho, resHost));   // add data to list
                }
            }
            if(restaurant.size() != 0) {
                adapter = new Adpater_enter_myRes(this, restaurant, enterID, enterName);   // call adapter and make item view and match
                recyclerView.setAdapter(adapter);   // to show data from adapter item view
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}