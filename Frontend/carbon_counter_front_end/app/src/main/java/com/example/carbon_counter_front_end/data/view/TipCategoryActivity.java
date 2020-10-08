package com.example.carbon_counter_front_end.data.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.carbon_counter_front_end.R;

public class TipCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_category);

        Button viewEmissions = (Button) findViewById(R.id.buttonEmissions);
        Button viewWater = (Button) findViewById(R.id.buttonWater);
        Button viewWaste = (Button) findViewById(R.id.buttonWaste);
        Button viewEnergy = (Button) findViewById(R.id.buttonEnergy);

        //Buttons to activities for their specific type


        //Add code to pull from server the users stats and but topics in recommended, if any

    }
}