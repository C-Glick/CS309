package com.example.carbon_counter_front_end.data.logic;

import android.content.Context;

import com.example.carbon_counter_front_end.data.model.RequestServerForService;
import com.example.carbon_counter_front_end.data.view.ViewCategoryResults;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewCategoryResultsLogic {
    private RequestServerForService model;
    private ViewCategoryResults view;
    private Context context;
    private ArrayList<JSONObject> myTips;
    private int tipsIndex;

    public ViewCategoryResultsLogic(ViewCategoryResults view, Context context){
        this.view = view;
        this.context = context;
        this.myTips = new ArrayList<>();
        this.tipsIndex = 0;
    }

    public void setModel(RequestServerForService m){ this.model = m; }

    public void getTips(String category){
        String url = "http://10.24.227.38:8080/tips";

        url += "/" + category;

        model.contactServerArray(url);
    }

    public void setTips(JSONArray response){
        for(int i = 0; i < response.length(); i++){
            try {
                myTips.add((JSONObject) response.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setNextTip(){
        if(tipsIndex == myTips.size() - 1){
            tipsIndex = 0;
        } else {
            tipsIndex++;
        }
    }

    public void setPrevTip(){
        if(tipsIndex == 0){
            tipsIndex = myTips.size() - 1;
        } else {
            tipsIndex--;
        }
    }

    public String getSubject(){
        String subject = "";

        try {
            subject = myTips.get(tipsIndex).getString("subject");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return subject;

    }

    public String getDescription(){
        String description = "";

        try {
            description = myTips.get(tipsIndex).getString("body");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return description;
    }
}
