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

import java.util.List;

public class FriendListActivity extends AppCompatActivity {



    private ListView listview;
    private String[] list;
    int i;
    JSONArray Friends = new JSONArray();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        Button Back = (Button) findViewById(R.id.backbutton);

        listview=(ListView)findViewById(R.id.lv);
        final FriendListLogic FLLogic= new FriendListLogic( FriendListActivity.this, getApplicationContext());
        FLLogic.setModel(new RequestServerForService(getApplicationContext(), new IVolleyListener() {
            @Override
            public void onImageSuccess(Bitmap image) {

            }

            @Override
            public void onSuccessJSONArray(JSONArray response) {
                Friends = response;
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.activity_list_item, (List<String>) Friends);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
//                Intent intent = new Intent(FriendListActivity.this, Main2Activity.class);
//                intent.putExtra("Friends",listview.getItemAtPosition(i).toString());
//                startActivity(intent);
            }
        });

        Back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FriendListActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

}
