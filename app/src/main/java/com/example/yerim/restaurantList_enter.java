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

public class restaurantList_enter extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter_enter adapter;
    String resName, resLocation, resKind, resPrice, resWho, resHost;
    Button newReg, myRes;
    Intent intent;
    public String enterName, enterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list_enter);

        setTitle("Restaurant List");

        intent = getIntent();
        enterName = intent.getStringExtra("enterName");
        enterID = intent.getStringExtra("enterID");

        String newreg = "내 식당 등록";
        newReg = findViewById(R.id.newRegister);
        newReg.setText(newreg);

        String myres = "내 식당 보기";
        myRes = findViewById(R.id.myRestaurant);
        myRes.setText(myres);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        String url = "http://dpfla4013.dothome.co.kr/getlist.php";   // request url
        restaurantList_enter.selectDatabase selectDatabase = new restaurantList_enter.selectDatabase(url, null);
        selectDatabase.execute();   // excute asynchronous task

        newReg.setOnClickListener(new View.OnClickListener() {   // click new restaurant register button
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), RestaurantRegister.class);
                intent.putExtra("enterName", enterName);
                intent.putExtra("enterID", enterID);
                startActivity(intent);   // move to new register page
            }
        });

        myRes.setOnClickListener(new View.OnClickListener() {   // click my restaurant button
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), MyRestaurant.class);
                intent.putExtra("enterName", enterName);
                intent.putExtra("enterID", enterID);
                startActivity(intent);   // move to my restaurant page
            }
        });
    }

    class selectDatabase extends AsyncTask<Void, Void, String> {

        private String url1;
        private ContentValues values1;
        String result1;

        public selectDatabase(String url, ContentValues contentValues) {   // send request to DB
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
            doJSONParser(s);  // do parse
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
                restaurant.add(new restaurant(resName, resLocation, resKind, resPrice, resWho, resHost));   // add new to list
            }
            adapter = new Adapter_enter(this, restaurant, enterID, enterName);   // call adapter and make item view and match
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }
}