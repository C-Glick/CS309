package com.example.carbon_counter_front_end;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.carbon_counter_front_end.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewActivity extends AppCompatActivity {
    private String TAG = ViewActivity.class.getSimpleName();
    private String tag_json_get= "json_obj_get";
    private double milesDriven =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
    }


    private void getMiles(final String username){
     //  final TextView failedUsername = (TextView) findViewById(R.id.failedLogin);


        String url = "http://10.24.227.38:8080/stats/addDaily";

        //url += "/" + username;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, // IF YOU WANT TO SEND A JSONOBJECT WITH POST THEN PASS IT HERE
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            if (response.get("userName").equals(username)) {
                                milesDriven = (double) response.get("milesDriven");

                            } else {
                                //Label stating failed username or password
                               // failedUsername.setText("Invalid username ");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_get);
    }






}