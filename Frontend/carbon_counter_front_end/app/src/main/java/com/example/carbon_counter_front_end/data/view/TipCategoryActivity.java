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
import java.util.Stack;

public class TipCategoryActivity extends AppCompatActivity {
    private String TAG = ViewActivity.class.getSimpleName();
    private String tag_json_get = "json_obj_get";
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_category);

        final TextView recommended = (TextView) findViewById(R.id.textViewRecommended);
        final TextView all = (TextView) findViewById(R.id.textViewAll);
        final Button viewEmissions = (Button) findViewById(R.id.buttonEmissions);
        final Button viewWater = (Button) findViewById(R.id.buttonWater);
        final Button viewWaste = (Button) findViewById(R.id.buttonWaste);
        final Button viewEnergy = (Button) findViewById(R.id.buttonEnergy);

        final TipCategoryLogic tipCategoryLogic = new TipCategoryLogic(this, getApplicationContext());
        tipCategoryLogic.setModel(new RequestServerForService(getApplicationContext(), new IVolleyListener() {
            @Override
            public void onSuccess(JSONObject response) {
                Stack<Character> allStack = tipCategoryLogic.setLayout(response);

                Button last = null;
                int marginTop = 110;
                int allStackSize = allStack.size();

                for(int i = 0; i < allStackSize; i++){
                    if(last != null){
                        marginTop = last.getTop() + 200;
                    }
                    char toCompare = allStack.pop();
                    System.out.println(toCompare);
                    switch (toCompare){
                        case 'm':
                            viewEmissions.setTop(marginTop);
                            viewEmissions.setBottom(viewEmissions.getTop() + 175);
                            last = viewEmissions;
                            break;
                        case 'w':
                            viewWater.setTop(marginTop);
                            viewWater.setBottom(viewWater.getTop() + 175);
                            last = viewWater;
                            break;
                        case 'p':
                            viewEnergy.setTop(marginTop);
                            viewEnergy.setBottom(viewEnergy.getTop() + 175);
                            last = viewEnergy;
                            break;
                        case 'g':
                            viewWaste.setTop(marginTop);
                            viewWaste.setBottom(viewWaste.getTop() + 175);
                            last = viewWaste;
                            break;
                        case 'a':
                            all.setTop(marginTop);
                            last = null;
                            marginTop = all.getTop() + 80;
                            break;
                        default:
                            break;
                    }
                }
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