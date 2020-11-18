package com.example.carbon_counter_front_end.data.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.carbon_counter_front_end.R;
import com.example.carbon_counter_front_end.data.logic.AdminLogic;
import com.example.carbon_counter_front_end.data.logic.FriendRequestsLogic;
import com.example.carbon_counter_front_end.data.model.IVolleyListener;
import com.example.carbon_counter_front_end.data.model.RequestServerForService;
import com.example.carbon_counter_front_end.data.model.TargetUserInformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class FreindRequestsActivity extends AppCompatActivity {
    private ListView listview;
    private String[] list;
    int i;
    JSONArray friendRequests = new JSONArray();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        Button Back = (Button) findViewById(R.id.backbutton);
        Button Refresh = (Button) findViewById(R.id.refreshbutton);
        Button Accept = (Button) findViewById(R.id.acceptSelected);
        Button Deny = (Button) findViewById(R.id.denyselected);
        TextView SelectedUser = (TextView) findViewById(R.id.textView6);

        final FriendRequestsLogic FRLogic= new FriendRequestsLogic( FreindRequestsActivity.this, getApplicationContext());
        FRLogic.setModel(new RequestServerForService(getApplicationContext(), new IVolleyListener() {
            @Override
            public void onImageSuccess(Bitmap image) {

            }

            @Override
            public void onSuccessJSONArray(JSONArray response) {
                    friendRequests = response;
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
        FRLogic.Allrequests();


        listview=(ListView)findViewById(R.id.lv);
//        String[] list = getResources().getStringArray(R.array.list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.activity_list_item, (List<String>) friendRequests);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {

//                intent.putExtra("Friends",listview.getItemAtPosition(i).toString());
//                startActivity(intent);
              TargetUserInformation.username = listview.getItemAtPosition(i).toString();
                SelectedUser.setText(listview.getItemAtPosition(i).toString());
            }
        });

        Accept.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                FRLogic.setModel(new RequestServerForService(getApplicationContext(), new IVolleyListener() {
                    @Override
                    public void onImageSuccess(Bitmap image) {

                    }

                    @Override
                    public void onSuccessJSONArray(JSONArray response) {
                        friendRequests = response;
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
                FRLogic.AcceptRequest();
            }
        });
        Deny.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                FRLogic.setModel(new RequestServerForService(getApplicationContext(), new IVolleyListener() {
                    @Override
                    public void onImageSuccess(Bitmap image) {

                    }

                    @Override
                    public void onSuccessJSONArray(JSONArray response) {
                        friendRequests = response;
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
                FRLogic.DenyRequest();
            }
        });

        Back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FreindRequestsActivity.this, FriendListActivity.class);
                startActivity(i);
            }
        });

        Refresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                FRLogic.setModel(new RequestServerForService(getApplicationContext(), new IVolleyListener() {
                    @Override
                    public void onImageSuccess(Bitmap image) {

                    }

                    @Override
                    public void onSuccessJSONArray(JSONArray response) {
                        friendRequests = response;
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
                FRLogic.Allrequests();

            }
        });

    }
}