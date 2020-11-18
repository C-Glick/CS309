package com.example.carbon_counter_front_end.data.view;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carbon_counter_front_end.R;
import com.example.carbon_counter_front_end.data.logic.ViewLogic;
import com.example.carbon_counter_front_end.data.model.IVolleyListener;
import com.example.carbon_counter_front_end.data.model.RequestServerForService;
import com.example.carbon_counter_front_end.data.model.UserInformation;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

/**
 * View user's stats
 * @author Morgan Funk & Zachary Current
 */
public class ViewActivity extends AppCompatActivity {
    private String TAG = ViewActivity.class.getSimpleName();
    private String tag_json_get= "json_obj_get";

    private String milesDriven;
    private String waterUsed;
    private String powerUsed;
    private String MeatConsumed;
    private String WasteProduced;

    private String username;
    private String password;
    private JSONObject UserInfo = new JSONObject();

    private WebSocketClient ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ViewLogic viewLogic = new ViewLogic(ViewActivity.this, getApplicationContext());
        setContentView(R.layout.activity_view);
        username = UserInformation.username;

        Draft[] drafts = {new Draft_6455()};

        try {
            ws = new WebSocketClient(new URI("ws://coms-309-tc-04.cs.iastate.edu:8080/notify/" + UserInformation.username), (Draft) drafts[0]) {


                @Override
                public void onOpen(ServerHandshake serverHandshake) {

                }

                @Override
                public void onMessage(String s) {
                    ViewActivity.this.runOnUiThread(new Runnable() {
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

        // getMiles(username);
        viewLogic.setModel(new RequestServerForService(getApplicationContext(), new IVolleyListener() {
            @Override
            public void onImageSuccess(Bitmap image) {

            }

            @Override
            public void onSuccessJSONArray(JSONArray response) {

            }

            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                 //viewLogic.getStats(response);

                milesDriven = response.getString("milesDriven");
                waterUsed = response.getString("water");
                powerUsed = response.getString("power");
                MeatConsumed = response.getString("meat");
                WasteProduced = response.getString("garbage");

                TextView mDriven = findViewById(R.id.milesDriven);
                mDriven.setText(milesDriven);
                TextView wUsed = findViewById(R.id.water);
                wUsed.setText(waterUsed);
                TextView pUsed = findViewById(R.id.power);
                pUsed.setText(powerUsed);
                TextView wProduced = findViewById(R.id.waste);
                wProduced.setText(WasteProduced);
                TextView mConsumed = findViewById(R.id.meat);
                mConsumed.setText(MeatConsumed);
            }

            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onError() {

            }
        }));

        viewLogic.authenticate();


    }

//
//    private void getMiles(final String username) throws JSONException {
//     //  final TextView failedUsername = (TextView) findViewById(R.id.failedLogin);
//
//
//
//
//    }






}