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
import com.example.carbon_counter_front_end.data.model.UserInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TipCategoryActivity extends AppCompatActivity {
    private String TAG = ViewActivity.class.getSimpleName();
    private String tag_json_get = "json_obj_get";
    private int milesDriven;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_category);


        username = UserInformation.username;
        password = UserInformation.password;

        Button viewEmissions = (Button) findViewById(R.id.buttonEmissions);
        Button viewWater = (Button) findViewById(R.id.buttonWater);
        Button viewWaste = (Button) findViewById(R.id.buttonWaste);
        Button viewEnergy = (Button) findViewById(R.id.buttonEnergy);
        //Add code to pull from server the users stats and but topics in recommended, if any
        getMiles(username);

        //Buttons to activities for their specific type
        if(milesDriven >= 100){
            //set layout with miledriven in the recommended
            setContentView(R.layout.activity_recommended_emissions);
        }



    }


    private void getMiles(final String username) {
        String url = "http://10.24.227.38:8080/stats/today";

        url += "/" + username;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, // IF YOU WANT TO SEND A JSONOBJECT WITH POST THEN PASS IT HERE
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            milesDriven = response.getInt("milesDriven");

                            TextView mDriven = findViewById(R.id.milesDriven);
                            mDriven.setText(milesDriven);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d(TAG, response.toString());

                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                System.out.println(error.getMessage());
                System.out.println(username);
                System.out.println(password);
            }
        }

        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String credentials = username + ":" + password;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                params.put("Authorization", auth);

                return params;
            }
        };


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_get);
    }
}