package com.kolip.numberle.clasic;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class CustomChronometer extends AppCompatTextView {
    long startTime;
    private boolean running;
    private long elapseTime;

    public CustomChronometer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomChronometer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        startTime = SystemClock.elapsedRealtime();
        updateText(startTime);
    }

    public void start() {
        startTime = SystemClock.elapsedRealtime();
        running = true;
        updateRunning();
    }

    public void stop() {
        running = false;
        updateRunning();
    }

    public void clear() {
        setText("00:00.0");
    }

    public long getTime() {
        return elapseTime;
    }

    private void updateRunning() {
        if (running) {
            updateText(SystemClock.elapsedRealtime());
            postDelayed(mTickRunnable, 100);
        } else {
            removeCallbacks(mTickRunnable);
        }
    }

    private synchronized void updateText(long now) {
        long milSeconds = now - startTime;
        this.elapseTime = milSeconds;
        String text = formatElapsedTime(milSeconds);
        setText(text);
    }

    private final Runnable mTickRunnable = new Runnable() {
        @Override
        public void run() {
            updateText(SystemClock.elapsedRealtime());
            postDelayed(mTickRunnable, 100);
        }
    };

    public static String formatElapsedTime(long elapsedSeconds) {
        // Break the elapsed seconds into hours, minutes, and seconds.
        long hours = 0;
        long minutes = 0;
        long seconds = 0;
        long miliSecond = 0;

        if (elapsedSeconds >= 3600000) {
            hours = elapsedSeconds / 3600000;
            elapsedSeconds -= hours * 3600000;
        }
        if (elapsedSeconds >= 60000) {
            minutes = elapsedSeconds / 60000;
            elapsedSeconds -= minutes * 60000;
        }
        if (elapsedSeconds >= 1000) {
            seconds = elapsedSeconds / 1000;
            elapsedSeconds -= seconds * 1000;
        }
        miliSecond = elapsedSeconds;

        if (hours > 0) {
            return hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":"
                    + (seconds < 10 ? "0" : "") + seconds + "." + miliSecond / 100;
        } else {
            return (minutes < 10 ? "0" : "") + minutes + ":"
                    + (seconds < 10 ? "0" : "") + seconds + "." + miliSecond / 100;
        }
    }

}
