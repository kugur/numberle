package com.kolip.numberle.timer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.kolip.numberle.MainList;
import com.kolip.numberle.R;

public class NotificationReceiver extends BroadcastReceiver {
    Context parent;
    private String CHANNEL_ID = "com.kolip.notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("NotificationReceiver", "receiver has been called.");
        parent = context;
        createNotificationChannel();
        sendNotification(101);
    }

    public void sendNotification(int notificationId) {
// Create an explicit intent for an Activity in your app
        Intent intent = new Intent(parent, MainList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(parent, 0, intent,
                PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(parent, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_drawing_2)
                .setContentTitle("NumberLE")
                .setContentText("Guess the NumberLE Today!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(parent);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "NumberLE";
            String description = "Numberle Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = parent.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
