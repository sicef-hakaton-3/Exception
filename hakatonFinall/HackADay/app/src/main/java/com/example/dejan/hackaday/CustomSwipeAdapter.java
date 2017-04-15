package com.example.dejan.hackaday;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.dejan.hackaday.Model.Movie;
import com.example.dejan.hackaday.Model.MySingleton;
import com.example.dejan.hackaday.Model.NearbyPlace;
import com.example.dejan.hackaday.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by aleks on 06-Nov-16.
 */

public class CustomSwipeAdapter extends PagerAdapter {
    //private int[] res = {R.drawable.angry, R.drawable.bored,R.drawable.happy,R.drawable.loved,R.drawable.sad,};

    private Hashtable<Integer, String> resources = new Hashtable<Integer, String>();
    private Hashtable<String, String> images = new Hashtable<String, String>();
    private boolean controlMovie = false;
    private int len;
    private Context ctx;
    private LayoutInflater layoutInflater;
    public static String PREFERENCES_NAME = "IMAGE";

    public CustomSwipeAdapter(Context ctx, List<NearbyPlace> places) {
        this.ctx = ctx;
        for (int i = 0; i < places.size(); ++i) {
            NearbyPlace place = places.get(i);
            resources.put(new Integer(i), place.name);
            if (place.icon != null) {
                images.put(place.name, place.icon);
            } else {
                images.put(place.name, null);
            }
        }
        len = resources.size();
    }

    public static String selectedPlace = "";
    public static String selectedMovie = "";


    public CustomSwipeAdapter(Context ctx, List<Movie> movies, boolean img) {
        this.ctx = ctx;
        for (int i = 0; i < movies.size(); ++i) {
            Movie movie = movies.get(i);
            resources.put(new Integer(i), movie.getTitle());
            if (movie.getPosterPath() != null) {
                String moviePosterUrl = "http://image.tmdb.org/t/p/w185";
                moviePosterUrl += movie.getPosterPath();
                images.put(movie.getTitle(), moviePosterUrl);
            } else {
                images.put(movie.getTitle(), null);
            }
        }
        controlMovie = true;
        len = resources.size();

    }

    @Override
    public int getCount() {
        return len;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        try {
            layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View item_view = layoutInflater.inflate(R.layout.swipe_layout, container, false);
            assert item_view != null;

            NetworkImageView imageView = (NetworkImageView) item_view.findViewById(R.id.image_view);

            TextView textView = (TextView) item_view.findViewById(R.id.image_count);
            String name = resources.get(new Integer(position));
            if (images.get(name).equals("")) {
                if (controlMovie) {
                    imageView.setImageResource(R.drawable.movie);
                } else {
                    imageView.setImageResource(R.drawable.place);
                }
            } else {
                ImageLoader loader = MySingleton.getInstance(ctx).getImageLoader();
                imageView.setImageUrl(images.get(name), loader);

                if (controlMovie)
                    selectedMovie = images.get(name);
                else
                    selectedPlace = images.get(name);
            }
            textView.setText(name);
            container.addView(item_view);
            return item_view;//super.instantiateItem(container, position);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        try {
            container.removeView((RelativeLayout) object);
        } catch (Exception e) {
        }
    }

    public void setImages() {

    }
}
