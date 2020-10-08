package com.example.carbon_counter_front_end.data.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.carbon_counter_front_end.R;
import com.example.carbon_counter_front_end.data.model.AppDatabase;
import com.example.carbon_counter_front_end.data.model.UserInformation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        final String username = UserInformation.username;
        final String password = UserInformation.password;
        final String role = UserInformation.role;

        Button viewStats = (Button) findViewById(R.id.buttonView);
        Button updateStats = (Button) findViewById(R.id.buttonUpdate);
        Button viewTips = (Button) findViewById(R.id.buttonViewTip);

        viewStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new intent to view stats page
                Intent i = new Intent(MainActivity.this, ViewActivity.class);
                i.putExtra("username",username);
                i.putExtra("password",password);
                startActivity(i);
            }
        });

        updateStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //New intent to update stats page
                Intent i = new Intent(MainActivity.this, UpdateActivity.class);
                i.putExtra("username",username);
                i.putExtra("password",password);

                startActivity(i);
            }
        });

        viewTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TipCategoryActivity.class);
                i.putExtra("username",username);
                i.putExtra("password",password);
                startActivity(i);
            }
        });
    }
}