package com.example.carbon_counter_front_end.data.logic;

import android.content.Context;

import com.example.carbon_counter_front_end.data.model.RequestServerForService;
import com.example.carbon_counter_front_end.data.view.AddTipActivity;

/**
 * Logic class to take care of the logic for AddTipActivity
 * @author Zachary Current
 */
public class AddTipLogic {
    private RequestServerForService model;
    private AddTipActivity view;
    private Context context;

    /**
     * AddTipLogic Constructor - to take care of the logic for the provided AddTipActivity
     * @param view view of AddTipActivity
     * @param context context of AddTipActivity
     */
    public AddTipLogic(AddTipActivity view, Context context){
        this.view = view;
        this.context = context;
    }

    /**
     * Set the model to take care of the server requests
     * @param m model to handle server requests
     */
    public void setModel(RequestServerForService m) { this.model = m; }

    public void addTip(){

    }
}
