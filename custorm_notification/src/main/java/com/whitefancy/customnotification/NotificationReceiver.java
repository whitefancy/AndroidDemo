package com.whitefancy.customnotification;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class NotificationReceiver extends BroadcastReceiver {
    int DBEventID=325423;
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent playIntent = new Intent(context.getApplicationContext(), MainActivity.class);

//put together the PendingIntent
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context.getApplicationContext(), 1, playIntent, FLAG_UPDATE_CURRENT);

        //get latest event details
        String notificationTitle = "Manasia event: " ;
        String notificationText = " at Stelea Spatarul 13, Bucuresti";

        //build the notification
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context.getApplicationContext(), App.CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(notificationTitle)
                        .setContentText(notificationText)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //trigger the notification
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context.getApplicationContext());

        //we give each notification the ID of the event it's describing,
        //to ensure they all show up and there are no duplicates
        notificationManager.notify(888, notificationBuilder.build());

    }
}