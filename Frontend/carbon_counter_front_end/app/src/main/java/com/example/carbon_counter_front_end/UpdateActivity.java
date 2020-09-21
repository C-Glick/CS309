package com.example.carbon_counter_front_end;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.carbon_counter_front_end.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class UpdateActivity extends AppCompatActivity {

    private String TAG = UpdateActivity.class.getSimpleName();
    private String tag_json_POST= "json_obj_POST";
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

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
                i.putExtra("username",username);
                startActivity(i);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    updateInfo(milesPerWeek.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateInfo(Editable text) throws JSONException {
            double milesDriven = parseDouble(text.toString());
            String url = "http://10.24.227.38:8080/stats/addDaily";

            //url += "/" + username;
//        "water": "test6@iastate.edu"
//        "power": "test123"
//        "milesDriven": 55.6
//        "meat":
//        "garbage": 34.6 (double)
            final JSONObject userUpdate = new JSONObject();
            userUpdate.put("userName", username);
            userUpdate.put("water", 0.0);
            userUpdate.put("power", 0.0);
            userUpdate.put("milesDriven", milesDriven);
            //   Toast.makeText(CreateUserActivity.this, jsonParam.toString(), Toast.LENGTH_LONG).show(); For Debugging later


            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, userUpdate, // IF YOU WANT TO SEND A JSONOBJECT WITH POST THEN PASS IT HERE
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response.toString());

                            System.out.println(response);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_POST);
    }
}