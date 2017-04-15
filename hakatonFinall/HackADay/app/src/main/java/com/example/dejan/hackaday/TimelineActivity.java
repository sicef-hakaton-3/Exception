package com.example.dejan.hackaday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.example.dejan.hackaday.R;

public class TimelineActivity extends AppCompatActivity {

    ListView list;

    String[] itemname ={
            "Safari",
            "Camera",
//            "Global",
//            "FireFox",
//            "UC Browser"
    };

    Integer[] imageId = {
            R.drawable.t,
            R.drawable.t
//            R.drawable.book2,
//            R.drawable.game2,
//            R.drawable.music2

    };

    //test for listview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        CustomList adapter=new CustomList(this, itemname, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem= itemname[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });
    }
}