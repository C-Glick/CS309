package com.example.carbon_counter_front_end.data.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carbon_counter_front_end.R;
import com.example.carbon_counter_front_end.data.logic.LoginLogic;
import com.example.carbon_counter_front_end.data.model.IVolleyListener;
import com.example.carbon_counter_front_end.data.model.RequestServerForService;
import com.example.carbon_counter_front_end.data.model.UserInformation;


import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Login Screen for Carbon Counter
 * @author Zachary Current
 */
public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final LoginLogic loginLogic = new LoginLogic(LoginActivity.this, getApplicationContext());
        final TextView failedLogin = (TextView) findViewById(R.id.failedLogin);
        final TextView failedLogin2 = (TextView) findViewById(R.id.failedLogin2);
        final TextView username = (TextView) findViewById(R.id.username);
        final TextView password = (TextView) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.buttonLogin);
        Button registerButton = (Button) findViewById(R.id.buttonRegister);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*InsertDataThread insertDataThread = new InsertDataThread(username.getText().toString(), password.getText().toString(), getApplicationContext());

                Thread insertData = new Thread(insertDataThread);

                insertData.start();*/

                loginLogic.setModel(new RequestServerForService(getApplicationContext(), new IVolleyListener() {
                    @Override
                    public void onImageSuccess(Bitmap image) {

                    }

                    @Override
                    public void onSuccessJSONArray(JSONArray response) {
                        //do nothing
                    }

                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            UserInformation.role = response.getString("role");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        loginLogic.clearError(failedLogin, failedLogin2);
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);



                    }

                    @Override
                    public void onSuccess(String response) {

                    }

                    @Override
                    public void onError() {
                        loginLogic.displayError(failedLogin, failedLogin2);
                    }
                }));

                loginLogic.authenticate(username.getText().toString(), password.getText().toString());
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

