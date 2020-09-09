package com.example.carbon_counter_front_end;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextView username = (TextView) findViewById(R.id.username);
        final TextView password = (TextView) findViewById(R.id.password);
        Button loginButton= (Button) findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateUser(username.getText().toString(), password.getText().toString());
            }
        });
    }

    private void authenticateUser(String username, String password){
        System.out.println(username + " " + password);
        //Next, Send HTTP Request to server with the username and password in the body as JSON
        //This request will return either an empty array or the user we are looking for
        //We can use this information to tell user what to do next or to switch activities
    }
}