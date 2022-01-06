package com.whitefancy.demo.home.items;

import android.app.Application;

import androidx.room.Room;

import com.whitefancy.demo.home.items.roomDB.AppDatabase;

public class MyApplication extends Application {
    public static AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "local").build();
    }
}
