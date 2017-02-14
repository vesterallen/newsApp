package com.example.hari.newsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ImagePopulator.Image{

    private String channelSrc="";
    Button button;
    BufferedReader br;
    String finalUrl;
    RequestParams params;
    HttpURLConnection con;
    StringBuilder sb;
    ArticleUtils articleUtils;
    ArrayList<Article> articleArrayList;
    private final String key="44ca146854ed4edbb3e0e7bbf79b0356";
    private final String method="GET";
    private final String baseUrl="https://newsapi.org/v1/articles?";
    ArrayList<Article> op;
    Bitmap image;
    int pos =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        params=new RequestParams(method,baseUrl);
        params.addParams("apiKey",key);


        //************************Finish Button*********************************//
        Button finish = (Button) findViewById(R.id.buttonFinish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //**************************Get News Button*******************************//
        Button getNews = (Button) findViewById(R.id.buttonGet);
        getNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("News Button","OnClick");
                //************************Spinner Value**************************//
                Spinner sp = (Spinner)findViewById(R.id.spinner);
                String channel = sp.getSelectedItem().toString();
                //**********************Check for Selection***********************//
                if(channel.equals("Select")){
                    Toast.makeText(MainActivity.this,"Please select a valid Channel",Toast.LENGTH_LONG).show();
                }
                else{
                    switch(channel){
                        case "BBC":
                            channelSrc = "bbc-news";
                            break;
                        case "CNN":
                            channelSrc = "cnn";
                            break;
                        case "Buzzfeed":
                            channelSrc = "buzzfeed";
                            break;
                        case "ESPN":
                            channelSrc = "espn";
                            break;
                        case "Sky News":
                            channelSrc = "sky-news";
                            break;
                        default:
                            Toast.makeText(MainActivity.this,"Please select a valid Channel",Toast.LENGTH_LONG).show();

                    }
                    params.addParams("source",channelSrc);
                    finalUrl=params.finalUrl();
                    new DoTask().execute(finalUrl);





                }

                /*
                sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("Spinner","OnSelect");
                        String label = parent.getItemAtPosition(position).toString();
                        Log.d("Demo",label);
                        Toast.makeText(MainActivity.this,label,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(parent.getContext(),"Please Select a News Channel",Toast.LENGTH_LONG).show();
                    }
                });
                */
            }
        });

        final TextView tv = (TextView) findViewById(R.id.textViewOutput);
        //int pos =0;

        ImageButton first = (ImageButton) findViewById(R.id.imageButtonFirst);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(op.get(0).toString());
                new ImagePopulator(MainActivity.this).execute(op.get(0).getUrlToImage());
                pos =0;
            }
        });
        ImageButton next = (ImageButton) findViewById(R.id.imageButtonNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos+1<op.size()-1){
                    tv.setText(op.get(++pos).toString());
                    new ImagePopulator(MainActivity.this).execute(op.get(pos).getUrlToImage());
                }
            }
        });
        ImageButton prev = (ImageButton) findViewById(R.id.imageButtonPrev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos-1>0){
                    tv.setText(op.get(--pos).toString());
                    new ImagePopulator(MainActivity.this).execute(op.get(pos).getUrlToImage());
                }
            }
        });
        ImageButton last = (ImageButton) findViewById(R.id.imageButtonLast);
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(op.get(op.size()-1).toString());
                new ImagePopulator(MainActivity.this).execute(op.get(op.size()-1).getUrlToImage());
                pos =op.size()-1;
            }
        });





}

    @Override
    public void getMainImage(Bitmap image) {
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        iv.setImageBitmap(image);
    }

    public class DoTask extends AsyncTask<String, Void, ArrayList<Article>> {

        @Override
        protected ArrayList<Article> doInBackground(String... strings) {
            con=params.getConnection(strings[0]);
            sb=new StringBuilder();
            articleArrayList=new ArrayList<Article>();
            articleUtils=new ArticleUtils();
            try {
                br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line="";
                while((line=br.readLine())!=null){
                    sb.append(line);
                }
                Log.d("JSON",sb.toString());
                articleArrayList=articleUtils.articleJSONParser(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return articleArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Article> s) {
            super.onPostExecute(s);
            op =s;
            for(Article a: s){
                Log.d("Output",a.toString());
            }


            TextView tv = (TextView) findViewById(R.id.textViewOutput);
            tv.setText(op.get(0).toString());

            new ImagePopulator(MainActivity.this).execute(op.get(0).getUrlToImage());

            //image = params.getImage(params.getConnection(s.get(0).getUrlToImage()));
/*
            try {
                image = BitmapFactory.decodeStream(params.getConnection(s.get(0).getUrlToImage()).getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageView iv = (ImageView) findViewById(R.id.imageView);
            iv.setImageBitmap(image);
*/
        }


    }

}
