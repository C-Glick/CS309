package com.example.carbon_counter_front_end.data.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.carbon_counter_front_end.R;

public class viewCategoryResults extends AppCompatActivity {
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category_results);

        Intent temp = getIntent();
        category = temp.getStringExtra("category");

        if(category.equals("emissions")){

        }

    }
}