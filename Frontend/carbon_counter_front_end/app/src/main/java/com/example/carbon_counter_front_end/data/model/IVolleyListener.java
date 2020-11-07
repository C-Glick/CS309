package com.example.carbon_counter_front_end.data.model;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Interface for defining server responses for various views
 */
public interface IVolleyListener {
    /**
     * To handle image responses, if necessary.
     * @param image image response from server
     */
    public void onImageSuccess(Bitmap image);

    /**
     * To handle JSONArray responses, if necessary.
     * @param response JSONArray response
     */
    public void onSuccessJSONArray(JSONArray response);

    /**
     * To hanle JSONObject responses.
     * @param response JSONObject response
     */
    public void onSuccess(JSONObject response);

    /**
     * To handle server connection errors
     */
    public void onError();
}
