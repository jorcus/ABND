package com.example.kiang.musicalapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class Songs1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player_full);

        // Find the View that shows the numbers category
        ImageView songs1 = (ImageView) findViewById(R.id.musicplayer);


        // Set a click listener on that View
        songs1.setOnClickListener(new View.OnClickListener() {
            MediaPlayer mediaPlayer = MediaPlayer.create(Songs1Activity.this, R.raw.justthewayyouare);
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }

            }
        });
    }
}
