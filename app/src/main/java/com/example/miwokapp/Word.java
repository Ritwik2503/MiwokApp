package com.example.miwokapp;

import android.media.AudioManager;

public class Word {
    // english translation for the word
    private String mDefaultTranslation;

    // miwok translation for the word
    private String mMiwokTranslation;

    // adding resource id for the image
    private int mImageResourceId= NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED= -1;

    // adding resource id for the media
    private int mMediaId;

    // adding audio manager
    private AudioManager mAudioManager;
    public Word(String defaultTranslation, String miwokTranslation, int mediaId){
        mDefaultTranslation= defaultTranslation;
        mMiwokTranslation= miwokTranslation;
        mMediaId= mediaId;
        }
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceID, int mediaId){
        mDefaultTranslation= defaultTranslation;
        mMiwokTranslation= miwokTranslation;
        mImageResourceId= imageResourceID;
        mMediaId= mediaId;
    }
    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }
    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }
    public int getImageResourceId(){
        return mImageResourceId;
    }
    public int getMediaId(){
        return mMediaId;
    }
    // adding a function to check weather the layout has image or not
    public boolean hasImage(){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
}
