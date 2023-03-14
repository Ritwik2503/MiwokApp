package com.example.miwokapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ColoursActivity extends AppCompatActivity {
    MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // resume playback
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // if the audio focus is lost then we will release the media player
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAudioManager= (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colours);
        final ArrayList<Word> colorsName = new ArrayList<>();
        colorsName.add(new Word("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        colorsName.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        colorsName.add(new Word("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        colorsName.add(new Word("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        colorsName.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        colorsName.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        colorsName.add(new Word("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        colorsName.add(new Word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        WordAdapter adapter = new WordAdapter(this, colorsName, R.color.category_colors);
        ListView list = findViewById(R.id.list3);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // we need to get position of each object to play the correct song
                Word word = colorsName.get(i); // i is position

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    // we have the audio focus
                    mMediaPlayer = MediaPlayer.create(ColoursActivity.this,word.getMediaId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}