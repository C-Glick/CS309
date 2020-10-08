package com.example.carbon_counter_front_end.data.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.carbon_counter_front_end.R;
import com.example.carbon_counter_front_end.app.AppController;
import com.example.carbon_counter_front_end.data.model.AppDatabase;
import com.example.carbon_counter_front_end.data.model.InsertDataThread;
import com.example.carbon_counter_front_end.data.model.LoggedInUser;
import com.example.carbon_counter_front_end.data.model.RequestServerForService;
import com.example.carbon_counter_front_end.data.model.User;
import com.example.carbon_counter_front_end.data.model.UserInformation;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private String TAG = LoginActivity.class.getSimpleName();
    private String tag_json_req = "json_obj_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextView username = (TextView) findViewById(R.id.username);
        final TextView password = (TextView) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.buttonLogin);
        Button registerButton = (Button) findViewById(R.id.buttonRegister);
        final TextView failedLogin = (TextView) findViewById(R.id.failedLogin);
        final TextView failedLogin2 = (TextView) findViewById(R.id.failedLogin2);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*InsertDataThread insertDataThread = new InsertDataThread(username.getText().toString(), password.getText().toString(), getApplicationContext());

                Thread insertData = new Thread(insertDataThread);

                insertData.start();*/
                UserInformation.username = username.getText().toString();
                UserInformation.password = password.getText().toString();

                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
             /*   RequestServerForService r = new RequestServerForService(TAG, tag_json_req);
                byte[] authenticated = r.authenticateUser(username.getText().toString(), password.getText().toString());
                System.out.println(authenticated);
                if (authenticated.getClass("status") == 200) {
                    failedLogin.setText("");
                    failedLogin2.setText("");

                    //intent to main page
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("username", username.getText().toString());
                    i.putExtra("password", password.getText().toString());

                    startActivity(i);
                } else {
                    failedLogin.setText("Invalid username or password!");
                    failedLogin2.setText("Please register or try again!");
                }*/
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
}

