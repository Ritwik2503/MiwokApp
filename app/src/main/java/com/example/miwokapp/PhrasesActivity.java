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

public class PhrasesActivity extends AppCompatActivity {
    MediaPlayer mMediaPlayer;
    AudioManager mAudioManager;

    MediaPlayer.OnCompletionListener mCompletionListener= new MediaPlayer.OnCompletionListener() {
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
        mAudioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);
        final ArrayList<Word> phrasesList = new ArrayList<>();
        phrasesList.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        phrasesList.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        phrasesList.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        phrasesList.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        phrasesList.add(new Word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        phrasesList.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        phrasesList.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        phrasesList.add(new Word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        phrasesList.add(new Word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        phrasesList.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));

        WordAdapter adapter = new WordAdapter(this, phrasesList, R.color.category_phrases);
        ListView list = findViewById(R.id.list4);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // we need to get position of each object to play the correct song
                Word word = phrasesList.get(i); // i is position
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    // we have the audio focus
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this,word.getMediaId());
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
