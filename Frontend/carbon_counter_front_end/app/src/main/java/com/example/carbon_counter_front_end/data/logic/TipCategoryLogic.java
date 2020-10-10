package com.example.carbon_counter_front_end.data.logic;

import android.content.Context;

import com.example.carbon_counter_front_end.R;
import com.example.carbon_counter_front_end.data.model.RequestServerForService;
import com.example.carbon_counter_front_end.data.model.UserInformation;
import com.example.carbon_counter_front_end.data.view.TipCategoryActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class TipCategoryLogic {
    private RequestServerForService model;
    private TipCategoryActivity view;
    private Context context;
    private int milesDriven;

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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getMiles() {
        return milesDriven;
    }

    public void setLayout(JSONObject response) {
        getStats(response);

        if(milesDriven > 100){
            setRecommendedEmissions();
        }
    }

    public void setRecommendedEmissions() {
        view.setContentView(R.layout.activity_recommended_emissions);
    }
}
