package com.example.dejan.hackaday.ApiFolder;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dejan.hackaday.Model.Genres;
import com.example.dejan.hackaday.Model.Movie;
import com.example.dejan.hackaday.Moods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dejan on 11/05/2016.
 */
public class MovieApi {

    public static final String api_key = "ffc5db1226835cdf0ecef98b7f863057";
    public static final String test = "https://api.themoviedb.org/3/movie/550?" + "api_key=" + api_key;

    public static String discoverMovie = "https://api.themoviedb.org/3/discover/movie?api_key=" + api_key;

    public static Movie getMovie(Activity activity, final VolleyCallback callback) {

        RequestQueue queue = Volley.newRequestQueue(activity);

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, test, null,
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

        return null;
    }

    public static List<Movie> findMatchingMovies(Moods mood, Activity activity, final VolleyCallback callback) {

        List<Genres> genres = generateGenres(mood);
        String queryGenres = genresToString(genres);
        discoverMovie += "&" + "with_genres=" + queryGenres + "&sort_by=popularity.desc";

        RequestQueue queue = Volley.newRequestQueue(activity);

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, discoverMovie, null,
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

        return null;
    }

    public static List<Genres> generateGenres(Moods mood) {
        List<Genres> genres = new ArrayList<>();
        switch (mood) {
            case ANGRY:
                genres.add(Genres.ACTION);
                genres.add(Genres.TRILER);
                genres.add(Genres.CRYME);
                Log.d("[GenerateMood]", mood.toString());
                break;
            case LOVE:
                Log.d("[GenerateMood]", mood.toString());
                genres.add(Genres.FAMILY);
                genres.add(Genres.ROMANSE);
                break;
            case SAD:
                Log.d("[GenerateMood]", mood.toString());
                genres.add(Genres.MYSTERY);
                genres.add(Genres.DRAMA);
                break;
            case HAPPY:
                genres.add(Genres.ADVENTURE);
                genres.add(Genres.COMEDY);
                genres.add(Genres.ANIMATION);
                Log.d("[GenerateMood]", mood.toString());
                break;
            case BORED:
                genres.add(Genres.TRILER);
                genres.add(Genres.MYSTERY);
                genres.add(Genres.SCIFI);
                Log.d("[GenerateMood]", mood.toString());
                break;
        }

        return genres;
    }

    public static String genresToString(List<Genres> genres) {
        String genresString = "";
        for (int i = 0; i < genres.size() - 1; i++) {
            genresString += genres.get(i).genresId;
            genresString += ",";
        }
        genresString += genres.get(genres.size() - 1).genresId;

        return genresString;
    }

    public static List<Movie> parseJsonMovie(JSONObject jsonMovie) {
        List<Movie> movieList = new ArrayList<Movie>();
        try {
            JSONArray jsonArray = jsonMovie.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Movie m = new Movie();
                m.setTitle(jsonObject.getString("title"));
                m.setVote(jsonObject.getDouble("vote_average"));
                m.setPosterPath(jsonObject.getString("poster_path"));
                movieList.add(m);
            }
        } catch (JSONException e) {
            Log.e("[MovieParser]", "Error parsing movies: " + e.toString());
        }

        return movieList;
    }
}

