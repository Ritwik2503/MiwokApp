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

public class FamilyMembersActivity extends AppCompatActivity {
    MediaPlayer mMediaPlayer;
    AudioManager mAudioManager;
    MediaPlayer.OnCompletionListener mComplitionListener= new MediaPlayer.OnCompletionListener() {
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
        setContentView(R.layout.activity_family_members);
        final ArrayList<Word> familyMembers= new ArrayList<>();
        familyMembers.add(new Word("Father","әpә",R.drawable.family_father,R.raw.family_father));
        familyMembers.add(new Word("Mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        familyMembers.add(new Word("Son","angsi",R.drawable.family_son,R.raw.family_son));
        familyMembers.add(new Word("Daughter", "tune",R.drawable.family_daughter,R.raw.family_daughter));
        familyMembers.add(new Word("Younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        familyMembers.add(new Word("Younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        familyMembers.add(new Word("Older brother", "taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        familyMembers.add(new Word("Older sister","tete",R.drawable.family_older_brother,R.raw.family_older_sister));
        familyMembers.add(new Word("Grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        familyMembers.add(new Word("Grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));

        // using our custom adapter to display the list
        WordAdapter adapter= new WordAdapter(this, familyMembers,R.color.category_family);
        ListView list= (ListView) findViewById(R.id.list2);
        // setting this adapter
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // we need to get position of each object to play the correct song
                Word word= familyMembers.get(i); // i is position
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    // we have the audio focus
                    mMediaPlayer = MediaPlayer.create(FamilyMembersActivity.this,word.getMediaId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mComplitionListener);
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