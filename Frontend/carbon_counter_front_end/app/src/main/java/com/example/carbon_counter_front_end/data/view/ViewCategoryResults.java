package com.example.carbon_counter_front_end.data.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carbon_counter_front_end.R;
import com.example.carbon_counter_front_end.data.logic.ViewCategoryResultsLogic;
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
 * View Tip page - based on the category you selected on the Tips Category page.
 * @author Zachary Current
 */
public class ViewCategoryResults extends AppCompatActivity {
    private String category;
    private WebSocketClient ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category_results);

        Draft[] drafts = {new Draft_6455()};

        try {
            ws = new WebSocketClient(new URI("ws://coms-309-tc-04.cs.iastate.edu:8080/notify/" + UserInformation.username), (Draft) drafts[0]) {


                @Override
                public void onOpen(ServerHandshake serverHandshake) {

                }

                @Override
                public void onMessage(String s) {
                    ViewCategoryResults.this.runOnUiThread(new Runnable() {
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

        Intent temp = getIntent();
        category = temp.getStringExtra("category");

        final TextView subject = (TextView) findViewById(R.id.textViewSubject);
        final TextView description = (TextView) findViewById(R.id.textViewDescription);
        Button next = (Button) findViewById(R.id.buttonNext);
        Button prev = (Button) findViewById(R.id.buttonPrev);
        Button delete = (Button) findViewById(R.id.buttonDelete);
        Button back = (Button) findViewById(R.id.buttonCatResultsBack);

        if(UserInformation.role.equals("USER")){
            delete.setVisibility(View.GONE);
        }

        final ViewCategoryResultsLogic resultsLogic = new ViewCategoryResultsLogic(this, getApplicationContext());
        resultsLogic.setModel(new RequestServerForService(getApplicationContext(), new IVolleyListener() {
            @Override
            public void onImageSuccess(Bitmap image) {

            }

            @Override
            public void onSuccessJSONArray(JSONArray response) {
                System.out.println(response);
                resultsLogic.setTips(response);
                String subjectInfo = resultsLogic.getSubject();
                String descriptionInfo = resultsLogic.getDescription();

                subject.setText(subjectInfo);
                description.setText(descriptionInfo);
            }

            @Override
            public void onSuccess(JSONObject response) {

            }

            @Override
            public void onSuccess(String response) {
                Intent i = new Intent(ViewCategoryResults.this, ViewCategoryResults.class);
                i.putExtra("category", category);
                startActivity(i);
            }

            @Override
            public void onError() {

            }
        }));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Call logic for the tip to be displayed
                resultsLogic.setNextTip();
                String subjectInfo = resultsLogic.getSubject();
                String descriptionInfo = resultsLogic.getDescription();

                subject.setText(subjectInfo);
                description.setText(descriptionInfo);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Call logic for the tip to be displayed
                resultsLogic.setPrevTip();
                String subjectInfo = resultsLogic.getSubject();
                String descriptionInfo = resultsLogic.getDescription();

                subject.setText(subjectInfo);
                description.setText(descriptionInfo);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultsLogic.deleteTip();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewCategoryResults.this, TipCategoryActivity.class);
                startActivity(i);
            }
        });

        resultsLogic.getTips(category);

    }
}