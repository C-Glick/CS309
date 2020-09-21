package com.example.carbon_counter_front_end;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Intent temp = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String Username = temp.getStringExtra("username");

        
        Button viewStats = (Button) findViewById(R.id.buttonView);
        Button updateStats = (Button) findViewById(R.id.buttonUpdate);

        viewStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new intent to view stats page
                Intent i = new Intent(MainActivity.this, ViewActivity.class);
                i.putExtra("username",Username);
                startActivity(i);
            }
        });

        updateStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //New intent to update stats page
                Intent i = new Intent(MainActivity.this, UpdateActivity.class);
                i.putExtra("username",Username);
                startActivity(i);
            }
        });
    }
}