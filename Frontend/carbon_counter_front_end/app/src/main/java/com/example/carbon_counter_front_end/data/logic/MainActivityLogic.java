package com.example.carbon_counter_front_end.data.logic;

import android.content.Context;
import android.net.Uri;

import com.example.carbon_counter_front_end.data.model.RequestServerForService;
import com.example.carbon_counter_front_end.data.view.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivityLogic {
    private RequestServerForService model;
    private MainActivity view;
    private Context context;
    private ArrayList<JSONObject> myNews;
    private int newsIndex;

    public MainActivityLogic(MainActivity view, Context context){
        this.view = view;
        this.context = context;
        this.newsIndex = 0;
        this.myNews = new ArrayList<>();
    }

    public void setModel(RequestServerForService m) { this.model = m;}

    public void getNews(){
        String url = "http://10.24.227.38:8080/news/all";

        model.contactServerArray(url);
    }

    public void setMyNews(JSONArray response){
        for(int i = 0; i < response.length(); i++){
            try {
                myNews.add((JSONObject) response.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        getImage();
    }

    public int getNewsIndex(){
        return newsIndex;
    }

    public void addNews(JSONObject news){
        myNews.add(news);
    }

    public void getImage(){
        try {
            String image = myNews.get(newsIndex).getString("imageTitle");
            String url = "http://10.24.227.38:8080/image/" + image;

            model.contactServerImage(url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setNextImage(){
        if(newsIndex == myNews.size() - 1){
            newsIndex = 0;
        } else {
            newsIndex++;
        }

        getImage();
    }

    public void setPrevImage(){
        if(newsIndex == 0){
            newsIndex = myNews.size() - 1;
        } else {
            newsIndex--;
        }

        getImage();
    }

    public Uri getUri(){
        Uri returnUri = null;
        try {
            returnUri = Uri.parse(myNews.get(newsIndex).getString("link"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return returnUri;
    }

    public String getTitle(){
        String title = "";

        try {
            title = myNews.get(newsIndex).getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return title;
    }
}
