package com.example.carbon_counter_front_end.data.logic;

import android.content.Context;

import com.example.carbon_counter_front_end.data.model.RequestServerForService;
import com.example.carbon_counter_front_end.data.view.MainActivity;

public class MainActivityLogic {
    private RequestServerForService model;
    private MainActivity view;
    private Context context;

    public MainActivityLogic(MainActivity view, Context context){
        this.view = view;
        this.context = context;
    }

    public void setModel(RequestServerForService m) { this.model = m;}

    public void getNews(){
        String url = "http://10.24.227.38:8080/news/all";

        model.contactServerArray(url);

        url = "http://10.24.227.38:8080/image/trump_biden_debate_climate_change.jpg";
        model.contactServerImage(url);
    }
}
