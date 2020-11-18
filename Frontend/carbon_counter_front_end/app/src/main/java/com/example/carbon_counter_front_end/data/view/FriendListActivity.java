package com.example.carbon_counter_front_end.data.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.carbon_counter_front_end.R;

public class FriendListActivity extends AppCompatActivity {

    private ListView listview;
    private String[] list;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        listview=(ListView)findViewById(R.id.lv);
//        list = getResources().getStringArray(R.array.list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listview.setAdapter(adapter);
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
//            {
//                Intent intent = new Intent(FriendListActivity.this, Main2Activity.class);
//                intent.putExtra("Friends",listview.getItemAtPosition(i).toString());
//                startActivity(intent);
//            }
//        });

    }
}