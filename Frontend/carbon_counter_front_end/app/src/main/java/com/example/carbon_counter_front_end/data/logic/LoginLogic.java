package com.example.carbon_counter_front_end.data.logic;

import android.content.Context;
import android.widget.TextView;

import com.example.carbon_counter_front_end.data.model.RequestServerForService;
import com.example.carbon_counter_front_end.data.model.UserInformation;
import com.example.carbon_counter_front_end.data.view.LoginActivity;

public class LoginLogic {
    private RequestServerForService model;
    private LoginActivity view;
    private Context context;

    public LoginLogic(LoginActivity view, Context context){
        this.view = view;
        this.context = context;
    }

    public void setModel(RequestServerForService m) { this.model = m;}

    public void authenticate(String username, String password) {
        UserInformation.username = username;
        UserInformation.password = password;

        String url = "http://10.24.227.38:8080/user";
        url += "/" + username;
        System.out.println(url);

        model.contactServer(url);
    }

    public void clearError(TextView failedLogin, TextView failedLogin2) {
        failedLogin.setText("");
        failedLogin2.setText("");
    }

    public void displayError(TextView failedLogin, TextView failedLogin2) {
        failedLogin.setText("Invalid username or password!");
        failedLogin2.setText("Please register or try again!");
    }
}
