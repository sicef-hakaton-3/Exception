package com.example.dejan.hackaday;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dejan.hackaday.R;

/**
 * Created by Archiorg on 11/6/2016.
 */

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;

    public CustomList(Activity context, String[] itemname, Integer[] imgid) {
        super(context, R.layout.list_view, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_view, null,true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        //ImageView imageView2 = (ImageView) rowView.findViewById(R.id.img2);

        imageView.setImageResource(imgid[position]);
        //imageView2.setImageResource(imgid[position]);

        return rowView;

    };
}

