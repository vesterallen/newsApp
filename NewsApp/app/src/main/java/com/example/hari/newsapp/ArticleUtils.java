package com.example.hari.newsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Allen on 2/6/17.
 */

public class ArticleUtils {
    static ArrayList<Article> articleJSONParser(String input) throws JSONException{
        ArrayList<Article> articleList=new ArrayList<Article>();
        JSONObject root=new JSONObject(input);
        JSONArray articlesJSONArray=root.getJSONArray("articles");
        for (int i=0;i<articlesJSONArray.length();i++){
            JSONObject articleJSONObject=articlesJSONArray.getJSONObject(i);
            Article article=new Article();
            article.setAuthor(articleJSONObject.getString("author"));
            article.setTitle(articleJSONObject.getString("title"));
            article.setDesc(articleJSONObject.getString("description"));
            article.setPubDate(articleJSONObject.getString("publishedAt"));
            article.setUrlArt(articleJSONObject.getString("url"));
            article.setUrlToImage(articleJSONObject.getString("urlToImage"));
            articleList.add(article);
        }
        return articleList;
    }
}
