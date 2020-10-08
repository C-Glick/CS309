package com.example.carbon_counter_front_end.data.presenter;

import android.content.Context;
import android.view.View;

import com.example.carbon_counter_front_end.data.model.IVolleyListener;
import com.example.carbon_counter_front_end.data.model.RequestServerForService;
import com.example.carbon_counter_front_end.data.model.UserInformation;
import com.example.carbon_counter_front_end.data.view.LoginActivity;

public class LoginPresenter {
    private RequestServerForService model;
    private LoginActivity view;
    private Context context;

    public LoginPresenter(LoginActivity view, Context context){
        this.view = view;
        this.context = context;
    }

    public void setModel(RequestServerForService m) { this.model = m;}

    public void authenticate() {
        model.authenticateUser();
    }
}
