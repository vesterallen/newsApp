package com.example.hari.newsapp;

import java.util.Date;

/**
 * Created by Allen on 2/6/17.
 */

public class Article {
    String author;
    String title;
    String desc;
    String urlArt;
    String urlToImage;
    String pubDate;

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrlArt() {
        return urlArt;
    }

    public void setUrlArt(String urlArt) {
        this.urlArt = urlArt;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("News Title ");
        sb.append(this.getTitle()+"\n");
        sb.append("Author: ");
        sb.append(this.getAuthor()+"\n");
        sb.append("Published on: ");
        sb.append(this.getPubDate()+"\n");
        sb.append("Description:\n");
        sb.append(getDesc());
        return sb.toString();
    }
}
