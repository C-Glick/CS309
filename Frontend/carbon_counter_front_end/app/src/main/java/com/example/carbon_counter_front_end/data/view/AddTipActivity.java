package com.example.carbon_counter_front_end.data.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carbon_counter_front_end.R;
import com.example.carbon_counter_front_end.data.logic.AddTipLogic;
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
 * Activity to add a tip to the server
 * @author Zachary Current
 */
public class AddTipActivity extends AppCompatActivity {
    private WebSocketClient ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tip);

        Draft[] drafts = {new Draft_6455()};

        try {
            ws = new WebSocketClient(new URI("ws://coms-309-tc-04.cs.iastate.edu:8080/notify/" + UserInformation.username), (Draft) drafts[0]) {


                @Override
                public void onOpen(ServerHandshake serverHandshake) {

                }

                @Override
                public void onMessage(String s) {
                    //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    AddTipActivity.this.runOnUiThread(new Runnable() {
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

            ws.connect();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        Button submit = (Button) findViewById(R.id.buttonSubmitTip);
        Button back = (Button) findViewById(R.id.buttonBackTip);
        final EditText subject = (EditText) findViewById(R.id.editTextSubject);
        final EditText description = (EditText) findViewById(R.id.editTextDescription);

        final AddTipLogic addTipLogic = new AddTipLogic(this, getApplicationContext());
        addTipLogic.setModel(new RequestServerForService(getApplicationContext(), new IVolleyListener() {

            @Override
            public void onImageSuccess(Bitmap image) {

            }

            @Override
            public void onSuccessJSONArray(JSONArray response) {

            }

            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                ws.send(subject.getText().toString() + " has been added for approval!");
                Intent i = new Intent(AddTipActivity.this, MainActivity.class);
                startActivity(i);
            }

            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onError() {

            }
        }));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddTipActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = "";
                if(spinner.getSelectedItem().toString().equals("Emissions")){
                    category = "CARBON";
                } else if(spinner.getSelectedItem().toString().equals("Water")){
                    category = "WATER";
                } else if(spinner.getSelectedItem().toString().equals("Energy")){
                    category = "ENERGY";
                } else {
                    category = "GARBAGE";
                }
                addTipLogic.addTip(category, description.getText().toString(), subject.getText().toString());
            }
        });
    }
}