package com.example.carbon_counter_front_end.data.logic;

import android.content.Context;

import com.example.carbon_counter_front_end.R;
import com.example.carbon_counter_front_end.data.model.RequestServerForService;
import com.example.carbon_counter_front_end.data.model.UserInformation;
import com.example.carbon_counter_front_end.data.view.TipCategoryActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Stack;


public class TipCategoryLogic {
    private RequestServerForService model;
    private TipCategoryActivity view;
    private Context context;
    private int milesDriven;
    private int water;
    private int power;
    private int meat;
    private int garbage;

    public TipCategoryLogic(TipCategoryActivity view, Context context){
        this.view = view;
        this.context = context;
    }

    public void setModel(RequestServerForService m){ this.model = m; }

    public void contactServer() {
        String url = "http://10.24.227.38:8080/stats/today";

        url += "/" + UserInformation.username;


        model.contactServer(url);
    }

    public void getStats(JSONObject response) {
        try {
            milesDriven = response.getInt("milesDriven");
            water = response.getInt("water");
            power = response.getInt("power");
            meat = response.getInt("meat");
            garbage = response.getInt("garbage");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getMiles() {
        return milesDriven;
    }

    public Stack<Character> setLayout(JSONObject response) {
        getStats(response);

        final int milesIndex = 0;
        final int waterIndex = 1;
        final int powerIndex = 2;
        final int garabageIndex = 3;

        Stack<Character> recommendStack = new Stack<Character>();
        Stack<Character> allStack = new Stack<Character>();



        if(milesDriven > 100){
            recommendStack.push('m');
        } else {
            allStack.push('m');
        }

        if(water > 100){
            recommendStack.push('w');
        } else {
            allStack.push('w');
        }

        if(power > 100){
            recommendStack.push('p');
        } else {
            allStack.push('p');
        }

        if(garbage > 100){
            recommendStack.push('g');
        } else {
            allStack.push('g');
        }

        allStack.push('a');

        for(int i = 0; i < recommendStack.size(); i++){
            allStack.push(recommendStack.pop());
        }

        return allStack;

    }

    public void setRecommendedEmissions() {
        view.setContentView(R.layout.activity_recommended_emissions);
    }
}
