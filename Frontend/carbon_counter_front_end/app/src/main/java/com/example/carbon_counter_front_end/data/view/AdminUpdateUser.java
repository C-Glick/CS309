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
import com.example.carbon_counter_front_end.data.logic.AdminLogic;
import com.example.carbon_counter_front_end.data.model.IVolleyListener;
import com.example.carbon_counter_front_end.data.model.RequestServerForService;
import com.example.carbon_counter_front_end.data.model.TargetUserInformation;
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
 * Admin Tip Approval page
 * @author Morgan Funk
 */
public class AdminUpdateUser extends AppCompatActivity {
    JSONObject CurrUserInfo = new JSONObject();
    private WebSocketClient ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_user);

        Draft[] drafts = {new Draft_6455()};

        try {
            ws = new WebSocketClient(new URI("ws://coms-309-tc-04.cs.iastate.edu:8080/notify/" + UserInformation.username), (Draft) drafts[0]) {


                @Override
                public void onOpen(ServerHandshake serverHandshake) {

                }

                @Override
                public void onMessage(String s) {
                    AdminUpdateUser.this.runOnUiThread(new Runnable() {
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

        final TextView username = (TextView) findViewById(R.id.inputUsername);
        final TextView UpdatedUsername = (TextView) findViewById(R.id.updatedUsername);
        final TextView UpdatedEmail = (TextView) findViewById(R.id.UpdatedEmail);
        final TextView UpdatedPassword = (TextView) findViewById(R.id.updatedPassword);
        final TextView UpdatedRole = (TextView) findViewById(R.id.updatedRole);
        final TextView SelectedUser = (TextView) findViewById(R.id.TargetUser);


        Button updateUser = (Button) findViewById(R.id.UpdateUser);
        Button DeleteUser = (Button) findViewById(R.id.deleteUser);
        Button SelectUser = (Button) findViewById(R.id.SelectUser);
        Button Back = (Button) findViewById(R.id.Return);

        final AdminLogic adminLogic= new AdminLogic( AdminUpdateUser.this, getApplicationContext());


        JSONObject UpdateInfo = new JSONObject();

        SelectUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                TargetUserInformation.username = username.getText().toString();
                SelectedUser.setText(username.getText().toString());

                adminLogic.setModel(new RequestServerForService(getApplicationContext(), new IVolleyListener() {
                    @Override
                    public void onImageSuccess(Bitmap image) {

                    }

                    @Override
                    public void onSuccessJSONArray(JSONArray response) {

                    }

                    @Override
                    public void onSuccess(JSONObject response) throws JSONException {
                        CurrUserInfo = response;
                    }

                    @Override
                    public void onSuccess(String response) {

                    }

                    @Override
                    public void onError() {

                    }
                }));
                adminLogic.getUser();
                try {
                    UpdatedUsername.setText(CurrUserInfo.getString("username"));
                    UpdatedEmail.setText(CurrUserInfo.getString("email"));
                    UpdatedPassword.setText("");
                    UpdatedRole.setText(CurrUserInfo.getString("role"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        });



        updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    UpdateInfo.put("username", UpdatedUsername.getText().toString());
                    UpdateInfo.put("email", UpdatedEmail.getText().toString());
                    if (UpdatedPassword.getText().toString().equals(""))
                    {
                        //Do nothing
                    }
                    else
                    {
                        UpdateInfo.put("password", UpdatedPassword.getText().toString());
                    }
                    UpdateInfo.put("role", UpdatedRole.getText().toString());


                    adminLogic.setModel(new RequestServerForService(getApplicationContext(), new IVolleyListener() {
                        @Override
                        public void onImageSuccess(Bitmap image) {

                        }

                        @Override
                        public void onSuccessJSONArray(JSONArray response) {

                        }

                        @Override
                        public void onSuccess(JSONObject response) throws JSONException {

                        }

                        @Override
                        public void onSuccess(String response) {

                        }

                        @Override
                        public void onError() {

                        }
                    }));
                    // Toast.makeText(UpdateActivity.this, (CharSequence) userUpdate, LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adminLogic.authenticate(UpdateInfo);
            }

        });

        DeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adminLogic.setModel(new RequestServerForService(getApplicationContext(), new IVolleyListener() {
                    @Override
                    public void onImageSuccess(Bitmap image) {

                    }

                    @Override
                    public void onSuccessJSONArray(JSONArray response) {

                    }

                    @Override
                    public void onSuccess(JSONObject response) throws JSONException {

                    }

                    @Override
                    public void onSuccess(String response) {

                    }

                    @Override
                    public void onError() {

                    }
                }));
                adminLogic.DeleteUser();
            }
        });

        Back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminUpdateUser.this, AdminOverview.class);
                startActivity(i);
            }
        });




    }
}