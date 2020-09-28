package com.example.carbon_counter_front_end;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.carbon_counter_front_end.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

public class ViewActivity extends AppCompatActivity {
    private String TAG = ViewActivity.class.getSimpleName();
    private String tag_json_get= "json_obj_get";
    private String milesDriven;
    private String username;
    private JSONArray UserInfo = new JSONArray();
    private JSONObject CurrentData = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        try {
            getMiles(username);
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


    private void getMiles(final String username) throws JSONException {
     //  final TextView failedUsername = (TextView) findViewById(R.id.failedLogin);

        String url = "http://10.24.227.38:8080/stats";

        url += "/" + username;

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                url, null, // IF YOU WANT TO SEND A JSONOBJECT WITH POST THEN PASS IT HERE
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                       // Toast.makeText(ViewActivity.this, "We in the response thing", LENGTH_SHORT).show();
                        UserInfo = response;

                            try {
                                CurrentData = UserInfo.getJSONObject(UserInfo.length()-1);
                                milesDriven = CurrentData.getString("milesDriven");

                                TextView mDriven = findViewById(R.id.milesDriven);
                                mDriven.setText(milesDriven);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        Log.d(TAG, response.toString());
                        //Label stating failed username or password
                        // failedUsername.setText("Invalid username ");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                System.out.println(error.getMessage());
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_get);
    }






}