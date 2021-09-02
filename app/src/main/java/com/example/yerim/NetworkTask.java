package com.example.yerim;

import android.content.ContentValues;
import android.os.AsyncTask;

public class NetworkTask extends AsyncTask<Void, Void, String> {

    private String url;
    private ContentValues values;

    public NetworkTask(String url, ContentValues values) {

        this.url = url;
        this.values = values;
    }

    @Override
    protected String doInBackground(Void... params) {

        String result;   // value for store result
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result = requestHttpURLConnection.request(url, values);   // get result from url

        return result;
    }

}
