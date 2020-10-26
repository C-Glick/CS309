package com.example.carbon_counter_front_end.data.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.carbon_counter_front_end.R;
import com.example.carbon_counter_front_end.app.AppController;
import com.example.carbon_counter_front_end.data.model.User;
import com.example.carbon_counter_front_end.data.model.UserInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class UpdateActivity extends AppCompatActivity {

    private String TAG = UpdateActivity.class.getSimpleName();
    private String tag_json_POST= "json_obj_POST";
    private String username;
    private String password;
    private LiveData<List<User>> myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
/*
        UserRepository mUserRepository = new UserRepository(getApplication());
        myUser = mUserRepository.getAllUsers();

        username = myUser.getValue().get(0).username;
        password = myUser.getValue().get(0).password;
*/
        username = UserInformation.username;
        password = UserInformation.password;

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
            userUpdate.put("username", username);
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
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    System.out.println(error.getMessage());
                    System.out.println(username);
                    System.out.println(password);
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                    String credentials = username+":"+password;
                    String auth = "Basic "
                            + Base64.encodeToString(credentials.getBytes(),
                            Base64.NO_WRAP);
                    params.put("Authorization", auth);

                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_POST);
    }
}