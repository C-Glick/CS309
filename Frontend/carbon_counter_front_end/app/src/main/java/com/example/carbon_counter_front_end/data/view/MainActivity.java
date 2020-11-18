package com.example.carbon_counter_front_end.data.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carbon_counter_front_end.R;
import com.example.carbon_counter_front_end.data.logic.MainActivityLogic;
import com.example.carbon_counter_front_end.data.model.IVolleyListener;
import com.example.carbon_counter_front_end.data.model.RequestServerForService;
import com.example.carbon_counter_front_end.data.model.UserInformation;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;

/**
 * Main menu for Carbon Counter
 * @author Zachary Current
 */
public class MainActivity extends AppCompatActivity {
    private WebSocketClient ws;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Draft[] drafts = {new Draft_6455()};

        try {
            ws = new WebSocketClient(new URI("ws://coms-309-tc-04.cs.iastate.edu:8080/notify/" + UserInformation.username), (Draft) drafts[0]) {


                @Override
                public void onOpen(ServerHandshake serverHandshake) {

                }

                @Override
                public void onMessage(String s) {
                    MainActivity.this.runOnUiThread(new Runnable() {
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


        final String username = UserInformation.username;
        final String password = UserInformation.password;
        final String role = UserInformation.role;

        Button viewStats = (Button) findViewById(R.id.buttonView);
        Button updateStats = (Button) findViewById(R.id.buttonUpdate);
        Button viewTips = (Button) findViewById(R.id.buttonViewTip);
        Button addTip = (Button) findViewById(R.id.buttonAddTip);
        Button Admin = (Button) findViewById(R.id.AdminLogin);
        Button logout = (Button) findViewById(R.id.buttonLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInformation.role = "";
                UserInformation.password = "";
                UserInformation.username = "";
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        if(!UserInformation.role.equals("ADMIN")){
            System.out.println(UserInformation.role);
            Admin.setVisibility(View.GONE);
        }

        final MainActivityLogic mainLogic = new MainActivityLogic(this, this.getApplicationContext());

        final ImageView display = (ImageView) findViewById(R.id.imageView);

        final TextView newsTitle = (TextView) findViewById(R.id.newsTitle);

        Button nextNews = (Button) findViewById(R.id.buttonNewsNext);
        Button prevNews = (Button) findViewById(R.id.buttonNewsPrev);
        Button goNews = (Button) findViewById(R.id.buttonGo);

        nextNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLogic.setNextImage();
            }
        });

        prevNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLogic.setPrevImage();
            }
        });

        goNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = mainLogic.getUri();
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        Admin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(mainLogic.Permissions(UserInformation.role))
                {
                    Intent i = new Intent(MainActivity.this, AdminOverview.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Insufficient Permissions", Toast.LENGTH_SHORT).show();
                }


            }

        });

        mainLogic.setModel(new RequestServerForService(this.getApplicationContext(), new IVolleyListener() {
            @Override
            public void onImageSuccess(Bitmap image) {
                display.setImageBitmap(image);

                String title = mainLogic.getTitle();
                newsTitle.setText(title);
            }

            @Override
            public void onSuccessJSONArray(JSONArray response) {
                mainLogic.setMyNews(response);
            }

            @Override
            public void onSuccess(JSONObject response) {

            }

            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onError() {
                System.out.println("error");
            }
        }));

        viewStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new intent to view stats page
                Intent i = new Intent(MainActivity.this, ViewActivity.class);
                startActivity(i);
            }
        });

        updateStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //New intent to update stats page
                Intent i = new Intent(MainActivity.this, UpdateActivity.class);
                startActivity(i);
            }
        });

        viewTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TipCategoryActivity.class);
                startActivity(i);
            }
        });

        addTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                if(UserInformation.role == "CREATOR" || UserInformation.role == "ADMIN"){

                } else {

                }
                 */
                Intent i = new Intent(MainActivity.this, AddTipActivity.class);
                startActivity(i);
            }
        });

        mainLogic.getNews();
    }
}