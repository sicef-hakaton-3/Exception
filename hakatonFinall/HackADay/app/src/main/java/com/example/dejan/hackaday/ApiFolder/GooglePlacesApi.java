package com.example.dejan.hackaday.ApiFolder;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dejan.hackaday.Model.NearbyPlace;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by aleks on 05-Nov-16.
 */

public class GooglePlacesApi {

    private Location currentLocation;
    private int radius = 50;
    private static LocationListener locationListener;
    private String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private Activity activity;
    private List<NearbyPlace> places = new ArrayList<NearbyPlace>() {
    };
    private static Hashtable<String, String> searchTypes;
    private static String mood;


    public GooglePlacesApi(Activity _activity) {
        activity = _activity;

        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };


    }

    public static LocationListener getLocationListener() {
        return locationListener;
    }

    public void setSearchTypes(Hashtable<String, String> types) {
        searchTypes = types;
    }

    public void setMood(String _mood) {
        mood = _mood;
    }

    public void getNearbyPlaces(final VolleyCallback callback) {
        double lat = 37.4220;//currentLocation.getLatitude();
        double lng = -122.0840;//currentLocation.getLongitude();
        url += "location=" + lat + "," + lng
                + "&radius=" + radius + "&key=AIzaSyBO_jabMJcby8yph7nag8updXl5juEIhRk";// + R.string.google_api_key;
        RequestQueue queue = Volley.newRequestQueue(activity);

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError();
            }
        });

        queue.add(jsonObjReq);
    }


    public static List<NearbyPlace> parse(JSONObject object) {
        try {
            List<NearbyPlace> places = new ArrayList<NearbyPlace>();
            String status = object.getString("status");
            if (status.equals("OK")) {
                JSONArray results = object.getJSONArray("results");
                if (results.length() != 0) {
                    for (int i = 0; i < results.length(); ++i) {
                        NearbyPlace place = new NearbyPlace();
                        JSONObject resultJson = results.getJSONObject(i);

                        if (resultJson.has("types")) {
                            boolean moodCtrl = rightMood(resultJson.getJSONArray("types"));
                            if (moodCtrl) {
                                if (resultJson.has("rating")) {
                                    String rating = resultJson.getString("rating");
                                    place.rating = rating;
                                }
                                if (resultJson.has("name")) {
                                    String name = resultJson.getString("name");
                                    place.name = name;
                                }
                                if (resultJson.has("formatted_address")) {
                                    String address = resultJson.getString("formatted_address");
                                    place.address = address;
                                }
                                if (resultJson.has("website")) {
                                    String web = resultJson.getString("website");
                                    place.website = web;
                                }
                                if (resultJson.has("url")) {
                                    String url = resultJson.getString("url");
                                    place.googlePagesUrl = url;
                                }
                                if (resultJson.has("icon")) {
                                    String icon = resultJson.getString("icon");
                                    place.icon = icon;
                                }
                                places.add(place);
                            }
                        }
                    }
                }
            }
            return places;
        } catch (JSONException e) {
            return null;
        }
    }

    private static boolean rightMood(JSONArray types) {
        int len = types.length();
        for (int i = 0; i < len; ++i) {
            try {
                String type = types.get(i).toString();
                String value = searchTypes.get(mood);
                if (value == "All")
                    return true;
                boolean ctrl = type.equals(value);
                if (ctrl) {
                    return true;
                }
            } catch (JSONException e) {
            }
            ;
        }
        return false;
    }

}
