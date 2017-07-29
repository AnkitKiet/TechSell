package com.techsell.activities;

import android.app.Application;

import com.firebase.client.Firebase;


/**
 * Created by Ankit on 10/09/16.
 */
public class Techsell extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}