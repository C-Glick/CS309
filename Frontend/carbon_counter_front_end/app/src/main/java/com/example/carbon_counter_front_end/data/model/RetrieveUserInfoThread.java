package com.example.carbon_counter_front_end.data.model;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

public class RetrieveUserInfoThread implements Runnable{
    private String username;
    private String password;
    private Context context;

    public RetrieveUserInfoThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {

    }

    public String getUsername() {
        return  username;
    }

    public String getPassword() {
        return password;
    }
}
