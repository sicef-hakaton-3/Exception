package com.example.dejan.hackaday;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.dejan.hackaday.ApiFolder.MovieApi;
import com.example.dejan.hackaday.CustomSwipeAdapter;
import com.example.dejan.hackaday.Model.Movie;
import com.example.dejan.hackaday.R;
import com.example.dejan.hackaday.ApiFolder.GooglePlacesApi;
import com.example.dejan.hackaday.ApiFolder.VolleyCallback;
import com.example.dejan.hackaday.Model.NearbyPlace;

import org.json.JSONObject;

import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class ResultsActivity extends AppCompatActivity {

    ViewPager viewPager;
    CustomSwipeAdapter adapter;
    private Hashtable<String, String> searchTypes = new Hashtable<>();
    private List<NearbyPlace> places;
    public String mood;
    public boolean placePressed, musicPressed, moviePressed, bookPressed, gamePressed;
    public ImageView btn_hmd;
    Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        mood = getSharedPreferences(MainActivity.PREFERENCES_NAME, 0).getString("mood", "");

        btn_hmd = (ImageView) findViewById(R.id.btn_hmd);
        btn_hmd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                SharedPreferences prefs = getSharedPreferences(MainActivity.PREFERENCES_NAME, 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("selectedPlace", CustomSwipeAdapter.selectedPlace);
                editor.putString("selectedMovie", CustomSwipeAdapter.selectedMovie);
                editor.apply();

                Intent intent = new Intent(ResultsActivity.this, TimelineActivity.class);
                startActivity(intent);
            }
        });

        placePressed = getIntent().getBooleanExtra("placePressed", false);
        musicPressed = getIntent().getBooleanExtra("musicPressed", false);
        moviePressed = getIntent().getBooleanExtra("moviePressed", false);
        bookPressed = getIntent().getBooleanExtra("bookPressed", false);
        gamePressed = getIntent().getBooleanExtra("gamePressed", false);

        if (placePressed) {
            SearchPlaces();
        } else {
            LinearLayout layout = (LinearLayout) findViewById(R.id.places_layout_pager);
            layout.setVisibility(View.INVISIBLE);
        }

        if (musicPressed) {

        }
        if (moviePressed) {
            MovieApi.generateGenres(Moods.getMood(mood));
            MovieApi.findMatchingMovies(Moods.getMood(mood), ResultsActivity.this, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {

                    try {
                        List<Movie> movies = MovieApi.parseJsonMovie(response);

                        viewPager = (ViewPager) findViewById(R.id.movies_view_pager);
                        adapter = new CustomSwipeAdapter(ResultsActivity.this, movies, true);
                        viewPager.setAdapter(adapter);
                    } catch (Exception ex) {
                    }

                }

                @Override
                public void onError() {
                    Log.d("[GenerateMood]", "Error generateSolution");
                }
            });
        } else {
            LinearLayout layout = (LinearLayout) findViewById(R.id.movies_layout_pager);
            layout.setVisibility(View.INVISIBLE);
        }
    }

    public void SearchPlaces() {
        try {
            searchTypes.put(Moods.HAPPY.toString(), "All");
            searchTypes.put(Moods.SAD.toString(), "zoo");
            searchTypes.put(Moods.BORED.toString(), "gym");
            searchTypes.put(Moods.ANGRY.toString(), "establishment");
            searchTypes.put(Moods.LOVE.toString(), "restaurant");

            final GooglePlacesApi googlePlacesApi = new GooglePlacesApi(ResultsActivity.this);
            // TODO: Pozivanje za filmove
            googlePlacesApi.setMood(mood);
            googlePlacesApi.setSearchTypes(searchTypes);
            googlePlacesApi.getNearbyPlaces(new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        places = googlePlacesApi.parse(response);

                        viewPager = (ViewPager) findViewById(R.id.places_view_pager);
                        adapter = new CustomSwipeAdapter(ResultsActivity.this, places);
                        viewPager.setAdapter(adapter);
//                        showImages();
                    } catch (Exception ex) {
                    }
                }

                @Override
                public void onError() {

                }
            });

        } catch (Exception e) {
        }
    }

    private void showImages() {

    }


}
