package com.example.carbon_counter_front_end.data.model;

import android.content.Context;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carbon_counter_front_end.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class RequestServerForService {
    IVolleyListener myListener;
    Context context;

    public RequestServerForService(Context c, IVolleyListener l) {
        this.context = c;
        this.myListener = l;
    }

    public void contactServer(String url) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, // IF YOU WANT TO SEND A JSONOBJECT WITH POST THEN PASS IT HERE
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("VOLLEY", "SERVER RESPONSE: " + response);
                        try {
                            myListener.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VOLLEY", "Error: " + error.getMessage());
                //Label stating failed username or password
                myListener.onError();
            }

        }

        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                String credentials = UserInformation.username+":"+UserInformation.password;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                params.put("Authorization", auth);

                return params;
            }
        };

        // Adding request to request queue
        //AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_req);
        Volley.newRequestQueue(context).add(jsonObjReq);
    }

    public void postServer(String url, JSONObject stats) throws JSONException {
        //  double milesDriven = parseDouble(text.toString());
       // String url = "http://10.24.227.38:8080/stats/addDaily";

        //url += "/" + username;
//        "water": "test6@iastate.edu"
//        "power": "test123"
//        "milesDriven": 55.6
//        "meat":
//        "garbage": 34.6 (double)
//        final JSONObject userUpdate = new JSONObject();
//        userUpdate.put("userName", username);
//        userUpdate.put("water", 0.0);
//        userUpdate.put("power", 0.0);
//        userUpdate.put("waste", 0.0);
//        userUpdate.put("meat", 0.0);
//        userUpdate.put("milesDriven", milesDriven);
        //   Toast.makeText(CreateUserActivity.this, jsonParam.toString(), Toast.LENGTH_LONG).show(); For Debugging later


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, stats, // IF YOU WANT TO SEND A JSONOBJECT WITH POST THEN PASS IT HERE
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("VOLLEY", "SERVER RESPONSE: " + response);
                        try {
                            myListener.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VOLLEY", "Error: " + error.getMessage());
                //Label stating failed username or password
                myListener.onError();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                String credentials = UserInformation.username+":"+UserInformation.password;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                params.put("Authorization", auth);

                return params;
            }
        };
        Volley.newRequestQueue(context).add(jsonObjReq);
    }
}



