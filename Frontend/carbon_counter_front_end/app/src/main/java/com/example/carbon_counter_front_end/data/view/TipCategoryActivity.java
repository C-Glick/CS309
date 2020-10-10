package com.example.carbon_counter_front_end.data.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.carbon_counter_front_end.R;
import com.example.carbon_counter_front_end.app.AppController;
import com.example.carbon_counter_front_end.data.logic.LoginLogic;
import com.example.carbon_counter_front_end.data.logic.TipCategoryLogic;
import com.example.carbon_counter_front_end.data.model.IVolleyListener;
import com.example.carbon_counter_front_end.data.model.RequestServerForService;
import com.example.carbon_counter_front_end.data.model.UserInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TipCategoryActivity extends AppCompatActivity {
    private String TAG = ViewActivity.class.getSimpleName();
    private String tag_json_get = "json_obj_get";
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_category);


        Button viewEmissions = (Button) findViewById(R.id.buttonEmissions);
        Button viewWater = (Button) findViewById(R.id.buttonWater);
        Button viewWaste = (Button) findViewById(R.id.buttonWaste);
        Button viewEnergy = (Button) findViewById(R.id.buttonEnergy);

        final TipCategoryLogic tipCategoryLogic = new TipCategoryLogic(this, getApplicationContext());
        tipCategoryLogic.setModel(new RequestServerForService(getApplicationContext(), new IVolleyListener() {
            @Override
            public void onSuccess(JSONObject response) {
                tipCategoryLogic.setLayout(response);
            }

            @Override
            public void onError() {

            }
        }));
        //Add code to pull from server the users stats and but topics in recommended, if any
        tipCategoryLogic.contactServer();
        //Buttons to activities for their specific type


    }
}