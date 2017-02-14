package com.example.hari.newsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by Allen on 2/6/17.
 */

public class RequestParams {
    Bitmap image;
    String method, baseUrl;
    HttpURLConnection connection;
    String recUrl;

    public RequestParams(String method, String baseUrl) {
        this.method = method;
        this.baseUrl = baseUrl;
    }

    HashMap<String, String> params=new HashMap<String, String>();
    public void addParams(String key, String value){
        params.put(key,value);
    }
    public String getEncodedParams(){
        StringBuilder sb=new StringBuilder();
        for (String key : params.keySet() ){
            try {
                String value = URLEncoder.encode(params.get(key),"UTF-8");
                if(sb.length()>0){
                    sb.append("&");
                }
                sb.append(key+"="+value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    public HttpURLConnection getConnection(String receivedUrl){
        try {
            URL url=new URL(receivedUrl);
            connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(this.method);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;

    }
    public String finalUrl(){
        String url=baseUrl+getEncodedParams();
        Log.d("FinalUrl",url);
        return url;
    }
    public Bitmap getImage(HttpURLConnection con){
        try {
            image = BitmapFactory.decodeStream(con.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
