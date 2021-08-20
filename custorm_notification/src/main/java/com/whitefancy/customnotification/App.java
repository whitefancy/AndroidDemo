package com.whitefancy.customnotification;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
public class App extends Application {
    public static final String CHANNEL_ID = "exampleChannel";
    @Override
    public void onCreate() {
        super.onCreate();
    }

}
