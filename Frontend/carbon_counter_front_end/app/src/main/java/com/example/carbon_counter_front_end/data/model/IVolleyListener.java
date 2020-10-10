package com.example.carbon_counter_front_end.data.model;

import org.json.JSONObject;

public interface IVolleyListener {
    public void onSuccess(JSONObject response);
    public void onError();
}
