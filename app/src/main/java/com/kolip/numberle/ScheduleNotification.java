package com.kolip.numberle;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.kolip.numberle.timer.NotificationReceiver;

import java.util.Calendar;

public class ScheduleNotification {
    private static String CHANNEL_ID = "com.kolip.notification";
    private Activity parent;

    public ScheduleNotification(Activity parent) {
        this.parent = parent;
        setSchedule();
    }

    private void setSchedule() {
        Calendar calaneder = Calendar.getInstance();
        Log.d("Schedule Notification", calaneder.get(Calendar.HOUR_OF_DAY) + "");
//        calaneder.set(Calendar.HOUR_OF_DAY, 19);
        calaneder.add(Calendar.MINUTE, 2);
        Intent intent = new Intent(parent, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(parent, 100, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager =
                (AlarmManager) parent.getSystemService(Activity.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calaneder.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
                pendingIntent);
    }
}
