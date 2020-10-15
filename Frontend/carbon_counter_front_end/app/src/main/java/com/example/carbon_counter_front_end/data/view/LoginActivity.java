package com.example.carbon_counter_front_end.data.view;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carbon_counter_front_end.R;
import com.example.carbon_counter_front_end.data.model.IVolleyListener;
import com.example.carbon_counter_front_end.data.model.RequestServerForService;
import com.example.carbon_counter_front_end.data.model.UserInformation;
import com.example.carbon_counter_front_end.data.logic.LoginLogic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

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
                UserInformation.username = username.getText().toString();
                UserInformation.password = password.getText().toString();
                loginLogic.setModel(new RequestServerForService(getApplicationContext(), new IVolleyListener() {
                    @Override
                    public void onSuccess(JSONObject response) throws JSONException {
                        loginLogic.clearError(failedLogin, failedLogin2);

                        UserInformation.role = response.getString("role");

                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        if(UserInformation.role.equals("ADMIN")) {
                            i = new Intent(LoginActivity.this, AdminOverview.class);
                        }
                        startActivity(i);
                    }

                    @Override
                    public void onError() {
                        loginLogic.displayError(failedLogin, failedLogin2);
                    }
                }));

                loginLogic.authenticate();
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

