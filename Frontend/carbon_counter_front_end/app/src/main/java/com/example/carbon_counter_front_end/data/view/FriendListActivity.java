package com.example.carbon_counter_front_end.data.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.example.carbon_counter_front_end.R;
import com.example.carbon_counter_front_end.data.logic.FriendListLogic;
import com.example.carbon_counter_front_end.data.logic.FriendRequestsLogic;
import com.example.carbon_counter_front_end.data.model.IVolleyListener;
import com.example.carbon_counter_front_end.data.model.RequestServerForService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FriendListActivity extends AppCompatActivity {


    JSONArray Friends = new JSONArray();

    List<String> Friendlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

       // Button Back = (Button) findViewById(R.id.backbutton);
        Button AddFriend = (Button) findViewById(R.id.button5);
        Button requests = (Button) findViewById(R.id.requestsbutton);

        final FriendListLogic FLLogic= new FriendListLogic( FriendListActivity.this, getApplicationContext());
        FLLogic.setModel(new RequestServerForService(getApplicationContext(), new IVolleyListener() {
            @Override
            public void onImageSuccess(Bitmap image) {

            }

            @Override
            public void onSuccessJSONArray(JSONArray response) {

                List<String> list = new ArrayList<>();


                for(int i =0; i<response.length(); i++)
                {
                    try {
                        list.add(response.getJSONObject(i).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Friendlist= list;
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
        FLLogic.friends();


        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listview,Friendlist);

        ListView listView = (ListView) findViewById(R.id.window_List);
        listView.setAdapter(adapter);

        AddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FriendListActivity.this, FriendPageActivity.class );
                startActivity(i);

            }
        });

        requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FriendListActivity.this, FreindRequestsActivity.class );
                startActivity(i);

            }
        });




    }

}
