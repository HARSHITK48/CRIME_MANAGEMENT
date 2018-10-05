package com.example.apple.crime_management;

import android.app.Application;

import io.realm.Realm;

public class CFMSApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Realm.init(this);
    }
}
