package com.example.carbon_counter_front_end.data.model;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.room.Room;

import com.android.volley.toolbox.StringRequest;

public class InsertDataThread implements Runnable {
    private String password;
    private String username;
    private Context context;

    public InsertDataThread(String username, String password, Context context){
        this.password = password;
        this.username = username;
        this.context = context;
    }

    @Override
    public void run(){
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "user-database").build();

        db.userDao().insert(new User(username, password, null));
    }
}
