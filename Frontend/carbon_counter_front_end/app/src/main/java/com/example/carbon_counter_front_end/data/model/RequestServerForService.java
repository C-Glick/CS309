package com.example.carbon_counter_front_end.data.model;

import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.carbon_counter_front_end.app.AppController;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestServerForService {
    private String TAG;
    private String tag_json_req;

    public RequestServerForService(String tag, String tag_req) {
        this.TAG = tag;
        this.tag_json_req = tag_req;
    }

    public byte[] authenticateUser(final String username, final String password) {
        String url = "http://10.24.227.38:8080/user";

        url += "/" + username;

        final Boolean[] authenticated = new Boolean[1];

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, // IF YOU WANT TO SEND A JSONOBJECT WITH POST THEN PASS IT HERE
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        authenticated[0] = true;
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //Label stating failed username or password
                authenticated[0] = false;
            }

        }

        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                String credentials = username+":"+password;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                params.put("Authorization", auth);

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_req);

        return jsonObjReq.getBody();
    }
}

