package com.example.carbon_counter_front_end.data.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.carbon_counter_front_end.R;


public class AdminOverview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_overview);


        Button manageUser = (Button) findViewById(R.id.ManageUser);
        //Button tipApproval = (Button) findViewById(R.id.tipApprovalbutton);
        Button tip = (Button) findViewById(R.id.tipbutton);

        tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminOverview.this, AdminTipApprovalActivity.class);
                startActivity(i);
            }
        });

        manageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminOverview.this, AdminUpdateUser.class );
                startActivity(i);
            }
            });

        ;

    }


}