package com.example.dejan.hackaday;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.dejan.hackaday.ApiFolder.MovieApi;
import com.example.dejan.hackaday.ApiFolder.VolleyCallback;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private Button test;
    private ImageView btnAngry, btnSad, btnHappy, btnBored, btnLoved;
    public Context context;
    public static String PREFERENCES_NAME = "MOOD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                Log.w("Exception", thread + " throws exception " + throwable);
            }
        });

        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        btnAngry = (ImageView) findViewById(R.id.btn_angry);
        btnSad = (ImageView) findViewById(R.id.btn_sad);
        btnHappy = (ImageView) findViewById(R.id.btn_happy);
        btnBored = (ImageView) findViewById(R.id.btn_bored);
        btnLoved = (ImageView) findViewById(R.id.btn_loved);

        btnAngry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateSolution(Moods.ANGRY);
            }
        });
        btnSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateSolution(Moods.SAD);
            }
        });
        btnHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateSolution(Moods.HAPPY);
            }
        });
        btnLoved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateSolution(Moods.LOVE);
            }
        });
        btnBored.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateSolution(Moods.BORED);
            }
        });

        String url = "http://image.tmdb.org/t/p/w185/811DjJTon9gD6hZ8nCjSitaIXFQ.jpg";

    }

    public void generateSolution(com.example.dejan.hackaday.Moods mood) {

        SharedPreferences prefs = getSharedPreferences(MainActivity.PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("mood", mood.toString());
        editor.apply();

        Intent i = new Intent(MainActivity.this, CategoryActivity.class);
        startActivity(i);
        if(true)
            return;
        MovieApi.generateGenres(mood);
        //findMatchingMovies
        MovieApi.findMatchingMovies(mood, MainActivity.this, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {

                //    MovieApi.parseJsonMovie(response);
                //     MovieApi.getMoviePoster(MainActivity.this, "asd");

                // Activate select category activity
                Intent i = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(i);
            }

            @Override
            public void onError() {
                Log.d("[GenerateMood]", "Error generateSolution");
            }
        });
    }
}