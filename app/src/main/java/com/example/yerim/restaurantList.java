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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class restaurantList extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter adapter;
    String resName, resLocation, resKind, resPrice, resWho, resHost;
    Intent intent;
    public String userName, userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
        setTitle("Restaurant List");

        intent = getIntent();
        userName = intent.getStringExtra("userName");
        userID = intent.getStringExtra("userID");

        String myRes = userName + "님의 예약 내역 확인하기";
        Button myReservation = findViewById(R.id.myReservation);
        myReservation.setText(myRes);

        // make recycler view for scroll
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        String url = "http://dpfla4013.dothome.co.kr/getlist.php";   // request url
        selectDatabase selectDatabase = new selectDatabase(url, null);
        selectDatabase.execute();   // excute asynchronous task

        myReservation.setOnClickListener(new View.OnClickListener() {   // click my reservation button
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), MyReservation.class);
                intent.putExtra("userName", userName);
                intent.putExtra("userID", userID);
                intent.putExtra("userName", userName);

                startActivity(intent);   // move to my reservation page
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
            result1 = requestHttpURLConnection.request(url1, values1);   // get result from url
            return result1;   // do on onPostExecute
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            doJSONParser(s);   // do on parser
        }
    }

    public void doJSONParser(String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONArray jsonArray = jsonObject.getJSONArray("resList");   // get JSON object
            List<restaurant> restaurant = new ArrayList<>();
            for (int i=0; i < jsonArray.length(); i++) {   // get each object and extract data
                JSONObject output = jsonArray.getJSONObject(i);
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
                restaurant.add(new restaurant(resName, resLocation, resKind, resPrice, resWho, resHost));   // add new information to list
            }
            adapter = new Adapter(this, restaurant, userID, userName);   // call adapter and make item view and match
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }
}