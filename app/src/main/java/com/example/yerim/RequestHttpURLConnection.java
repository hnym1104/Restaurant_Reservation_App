package com.example.yerim;

import android.content.ContentValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class RequestHttpURLConnection {

    public String request(String _url, ContentValues _params) {

        HttpURLConnection urlConn = null;
        StringBuffer sbParams = new StringBuffer();

        if (_params == null)
            sbParams.append("");   // if no data to send, empty parameter
        else {   // if data exist, fill parameter
            boolean isAnd = false;
            String key;
            String value;

            for (Map.Entry<String, Object> parameter : _params.valueSet()) {
                key = parameter.getKey();
                value = parameter.getValue().toString();

                if (isAnd)
                    sbParams.append("&");   // set & between two value
                sbParams.append(key).append("=").append(value);
                if (!isAnd)
                    if (_params.size() >= 2)
                        isAnd = true;
            }
        }

        try {
            URL url = new URL(_url);
            urlConn = (HttpURLConnection) url.openConnection();

            urlConn.setRequestMethod("POST");   // method POST
            urlConn.setRequestProperty("Accept-Charset", "UTF-8");   // set char set
            urlConn.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;cahrset=UTF-8");

            String strParams = sbParams.toString();   // store parameter to string
            OutputStream os = urlConn.getOutputStream();
            os.write(strParams.getBytes("UTF-8"));   // print
            os.flush();   // flush buffer
            os.close();   // close buffer

            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK)
                return null;

            // receive result
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));

            String line;
            String page = "";

            while ((line = reader.readLine()) != null) {
                page += line;
            }

            return page;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null)
                urlConn.disconnect();
        }
        return null;
    }
}