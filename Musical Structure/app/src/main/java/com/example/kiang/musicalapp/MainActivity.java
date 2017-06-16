package com.example.kiang.musicalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Find the View that shows the numbers category
        TextView songs1 = (TextView) findViewById(R.id.Songs_1);

        // Set a click listener on that View
        songs1.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent songs1Intent = new Intent(MainActivity.this, Songs1Activity.class);
                startActivity(songs1Intent);
            }
        });

        // Find the View that shows the numbers category
        TextView songs2 = (TextView) findViewById(R.id.Songs_2);

        // Set a click listener on that View
        songs2.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent songs2Intent = new Intent(MainActivity.this, Songs2Activity.class);
                startActivity(songs2Intent);
            }
        });
    }
}
