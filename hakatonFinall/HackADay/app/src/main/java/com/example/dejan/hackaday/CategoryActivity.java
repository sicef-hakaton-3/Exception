package com.example.dejan.hackaday;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.dejan.hackaday.R;

public class CategoryActivity extends AppCompatActivity {

    public ImageView placeButton, musicButton, movieButton, bookButton, gameButton;
    public boolean placePressed, musicPressed, moviePressed, bookPressed, gamePressed;
    public ImageView nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        nextBtn = (ImageView) findViewById(R.id.btn_next);

        placeButton = (ImageView) findViewById(R.id.categoryPlaces);
        musicButton = (ImageView) findViewById(R.id.categoryMusic);
        movieButton = (ImageView) findViewById(R.id.categoryMovie);
        bookButton = (ImageView) findViewById(R.id.categoryBooks);
        gameButton = (ImageView) findViewById(R.id.categoryGames);

        placePressed = musicPressed = moviePressed = bookPressed = gamePressed = false;

  //      placeButton.setImageResource(R.drawable.games);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, ResultsActivity.class);
                intent.putExtra("placePressed", placePressed);
                intent.putExtra("musicPressed", musicPressed);
                intent.putExtra("moviePressed", moviePressed);
                intent.putExtra("bookPressed", bookPressed);
                intent.putExtra("gamePressed", gamePressed);
                startActivity(intent);
            }
        });

        placeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placePressed = !placePressed;
                if (placePressed){
                    placeButton.setImageResource(R.drawable.placesp);
                }
                else
                    placeButton.setImageResource(R.drawable.places);
            }
        });
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicPressed = !musicPressed;
                if (musicPressed){
                    musicButton.setImageResource(R.drawable.musicp);
                }
                else
                    musicButton.setImageResource(R.drawable.music);

            }
        });
        movieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moviePressed = !moviePressed;
                if (moviePressed){
                    movieButton.setImageResource(R.drawable.moviesp);
                }
                else
                    movieButton.setImageResource(R.drawable.movies);

            }
        });
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookPressed = !bookPressed;
                if (bookPressed){
                    bookButton.setImageResource(R.drawable.booksp);
                }
                else
                    bookButton.setImageResource(R.drawable.books);

            }
        });
        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamePressed = !gamePressed;
                if (gamePressed){
                    gameButton.setImageResource(R.drawable.gamesp);
                }
                else
                    gameButton.setImageResource(R.drawable.games);

            }
        });
    }


}
