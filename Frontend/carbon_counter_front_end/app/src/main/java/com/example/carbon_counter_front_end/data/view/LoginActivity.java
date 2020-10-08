package com.example.carbon_counter_front_end.data.view;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carbon_counter_front_end.R;
import com.example.carbon_counter_front_end.data.model.IVolleyListener;
import com.example.carbon_counter_front_end.data.model.RequestServerForService;
import com.example.carbon_counter_front_end.data.model.UserInformation;
import com.example.carbon_counter_front_end.data.presenter.Presenter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

public class LoginActivity extends AppCompatActivity {
    private TextView failedLogin = (TextView) findViewById(R.id.failedLogin);
    private TextView failedLogin2 = (TextView) findViewById(R.id.failedLogin2);
    private Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new Presenter(LoginActivity.this, getApplicationContext());

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
                presenter.setModel(new RequestServerForService(getApplicationContext(), new IVolleyListener() {
                    @Override
                    public void onSuccess() {
                        gotoMainActivity();
                    }

                    @Override
                    public void onError() {

                    }
                }));
                presenter.authenticate();
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

    public void gotoMainActivity() {
        failedLogin.setText("");
        failedLogin2.setText("");
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }

    public void displayError() {
        failedLogin.setText("Invalid username or password!");
        failedLogin2.setText("Please register or try again!");
    }

}

