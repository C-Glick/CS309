package com.example.carbon_counter_front_end.data.model;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IVolleyListener {
    public void onImageSuccess(Bitmap image);
    public void onSuccessJSONArray(JSONArray response);
    public void onSuccess(JSONObject response);
    public void onError();
}
