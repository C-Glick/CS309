package com.example.carbon_counter_front_end.data.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carbon_counter_front_end.R;
import com.example.carbon_counter_front_end.data.logic.MainActivityLogic;
import com.example.carbon_counter_front_end.data.model.IVolleyListener;
import com.example.carbon_counter_front_end.data.model.RequestServerForService;
import com.example.carbon_counter_front_end.data.model.UserInformation;

import org.json.JSONArray;
import org.json.JSONObject;

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
        MainActivityLogic mainLogic = new MainActivityLogic(this, this.getApplicationContext());

        final ImageView display = (ImageView) findViewById(R.id.imageView);


        mainLogic.setModel(new RequestServerForService(this.getApplicationContext(), new IVolleyListener() {
            @Override
            public void onImageSuccess(Bitmap image) {
                display.setImageBitmap(image);
            }

            @Override
            public void onSuccessJSONArray(JSONArray response) {
                System.out.println(response);
            }

            @Override
            public void onSuccess(JSONObject response) {

                System.out.println(response);
            }

            @Override
            public void onError() {
                System.out.println("error");
            }
        }));

        viewStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new intent to view stats page
                Intent i = new Intent(MainActivity.this, ViewActivity.class);
                startActivity(i);
            }
        });

        updateStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //New intent to update stats page
                Intent i = new Intent(MainActivity.this, UpdateActivity.class);
                startActivity(i);
            }
        });

        viewTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TipCategoryActivity.class);
                startActivity(i);
            }
        });

        mainLogic.getNews();
    }
}