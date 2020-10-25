package com.example.carbon_counter_front_end.data.logic;

import android.content.Context;

import com.example.carbon_counter_front_end.data.model.RequestServerForService;
import com.example.carbon_counter_front_end.data.view.ViewCategoryResults;

public class ViewCategoryResultsLogic {
    private RequestServerForService model;
    private ViewCategoryResults view;
    private Context context;

    public ViewCategoryResultsLogic(ViewCategoryResults view, Context context){
        this.view = view;
        this.context = context;
    }

    public void setModel(RequestServerForService m){ this.model = m; }

    public void getTips(String category){
        String url = "http://10.24.227.38:8080/tips";

        url += "/" + category;

        model.contactServer(url);
    }
}
