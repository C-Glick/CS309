package com.example.carbon_counter_front_end;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Button backButton = (Button) findViewById(R.id.buttonBack);
        Button viewButton = (Button) findViewById(R.id.buttonView2);
        Button submitButton = (Button) findViewById(R.id.buttonSubmit);
        final EditText milesPerWeek = (EditText) findViewById(R.id.milesPerWeek);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UpdateActivity.this, ViewActivity.class);
                startActivity(i);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInfo(milesPerWeek.getText());
            }
        });
    }

    private void updateInfo(Editable text) {
        //Send HTTP post with info
    }
}