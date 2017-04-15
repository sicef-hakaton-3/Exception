package com.example.dejan.hackaday.ApiFolder;

import org.json.JSONObject;

/**
 * Created by dejan on 11/05/2016.
 */
public interface VolleyCallback {
    void onSuccess(JSONObject response);
    void onError();
}
