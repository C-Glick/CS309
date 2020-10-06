package com.example.carbon_counter_front_end;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.carbon_counter_front_end.app.AppController;
import android.media.MediaCodec;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;


import com.android.volley.toolbox.StringRequest;
import com.example.carbon_counter_front_end.net_utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends AppCompatActivity {
    private String TAG = LoginActivity.class.getSimpleName();
    private String tag_json_req = "json_obj_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextView username = (TextView) findViewById(R.id.username);
        final TextView password = (TextView) findViewById(R.id.password);
        Button loginButton= (Button) findViewById(R.id.buttonLogin);
        Button registerButton = (Button) findViewById(R.id.buttonRegister);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                authenticateUser(username.getText().toString(), password.getText().toString());
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, CreateUserActivity.class);
                startActivity(i);
            }
        });

    }

    private void authenticateUser(final String username, final String password){
        System.out.println(username + " " + password);

        final TextView failedLogin = (TextView) findViewById(R.id.failedLogin);
        final TextView failedLogin2 = (TextView) findViewById(R.id.failedLogin2);

        String url = "http://10.24.227.38:8080/user";

        url += "/" + username;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, // IF YOU WANT TO SEND A JSONOBJECT WITH POST THEN PASS IT HERE
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            if (response.get("password").equals(password)) {
                                //clear failed login fields
                                failedLogin.setText("");
                                failedLogin2.setText("");

                                //intent to main page
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                i.putExtra("username",username);

                                startActivity(i);

                            } else {
                                //Label stating failed username or password
                                failedLogin.setText("Invalid username or password!");
                                failedLogin2.setText("Please register or try again!");


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()
                    {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(LoginActivity.this, "Invalid Username/Password", LENGTH_SHORT).show();
            }

        }

        ){
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

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_req);
    }
}