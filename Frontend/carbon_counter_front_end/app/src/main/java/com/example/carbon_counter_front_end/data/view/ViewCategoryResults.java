package com.example.carbon_counter_front_end.data.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carbon_counter_front_end.R;
import com.example.carbon_counter_front_end.data.logic.ViewCategoryResultsLogic;
import com.example.carbon_counter_front_end.data.model.IVolleyListener;
import com.example.carbon_counter_front_end.data.model.RequestServerForService;

import org.json.JSONObject;

public class ViewCategoryResults extends AppCompatActivity {
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category_results);

        Intent temp = getIntent();
        category = temp.getStringExtra("category");

        TextView subject = (TextView) findViewById(R.id.editTextSubject);
        TextView description = (TextView) findViewById(R.id.textViewDescription);
        Button next = (Button) findViewById(R.id.buttonNext);
        Button prev = (Button) findViewById(R.id.buttonPrev);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Call logic for the tip to be displayed
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Call logic for the tip to be displayed
            }
        });

        ViewCategoryResultsLogic resultsLogic = new ViewCategoryResultsLogic(this, getApplicationContext());
        resultsLogic.setModel(new RequestServerForService(getApplicationContext(), new IVolleyListener() {
            @Override
            public void onSuccess(JSONObject response) {
                //Take response and form hash-map and send back to logic

                //Call logic for the tip to be displayed

            }

            @Override
            public void onError() {

            }
        }));

        resultsLogic.getTips(category);

    }
}