package com.hieptran.applicationx.model;

import java.util.ArrayList;

/**
 * Created by hiepth on 26/04/2016.
 */
public class Feed {
    String post_name = "-";
    String post_content= "-";
    String post_author= "-";
    String post_date= "-";
    String post_location= "-";
    String post_weather= "-";
    String post_description= "-";
    ArrayList<String> post_attachment;
public Feed() {}
    public Feed(String post_name, String post_content, String post_author, String post_date, String post_location, String post_weather, String post_description, ArrayList<String> post_attachment) {
        this.post_name = post_name;
        this.post_content = post_content;
        this.post_author = post_author;
        this.post_date = post_date;
        this.post_location = post_location;
        this.post_weather = post_weather;
        this.post_description = post_description;
        this.post_attachment = post_attachment;
    }

    public String getPost_name() {
        return post_name;
    }

    public void setPost_name(String post_name) {
        this.post_name = post_name;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public String getPost_author() {
        return post_author;
    }

    public void setPost_author(String post_author) {
        this.post_author = post_author;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getPost_location() {
        return post_location;
    }

    public void setPost_location(String post_location) {
        this.post_location = post_location;
    }

    public String getPost_weather() {
        return post_weather;
    }

    public void setPost_weather(String post_weather) {
        this.post_weather = post_weather;
    }

    public String getPost_description() {
        return post_description;
    }

    public void setPost_description(String post_description) {
        this.post_description = post_description;
    }

    public ArrayList<String> getPost_attachment() {
        return post_attachment;
    }

    public void setPost_attachment(ArrayList<String> post_attachment) {
        this.post_attachment = post_attachment;
    }
}
