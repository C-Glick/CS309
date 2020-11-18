package com.example.carbon_counter_front_end.data.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carbon_counter_front_end.R;
import com.example.carbon_counter_front_end.data.model.UserInformation;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Admin Overview page
 * @author Morgan Funk
 */
public class AdminOverview extends AppCompatActivity {

    private WebSocketClient ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_overview);

        Draft[] drafts = {new Draft_6455()};

        try {
            ws = new WebSocketClient(new URI("ws://coms-309-tc-04.cs.iastate.edu:8080/notify/" + UserInformation.username), (Draft) drafts[0]) {


                @Override
                public void onOpen(ServerHandshake serverHandshake) {

                }

                @Override
                public void onMessage(String s) {
                    AdminOverview.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                        }
                    });
                    System.out.println(s);
                }

                @Override
                public void onClose(int i, String s, boolean b) {

                }

                @Override
                public void onError(Exception e) {
                    System.out.println(e.getMessage());
                }
            };
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        ws.connect();

        Button manageUser = (Button) findViewById(R.id.ManageUser);
        //Button tipApproval = (Button) findViewById(R.id.tipApprovalbutton);
        Button tip = (Button) findViewById(R.id.tipbutton);
        Button home = (Button) findViewById(R.id.buttonHome);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminOverview.this, MainActivity.class);
                startActivity(i);
            }
        });

        tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminOverview.this, TipApprovalActivity.class);
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