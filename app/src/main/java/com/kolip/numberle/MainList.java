package com.kolip.numberle;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.kolip.numberle.clasic.ClasicNumberle;

public class MainList extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        findViewById(R.id.challanging_wordle).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.clasic_wordle).setOnClickListener(v -> {
            Intent intent = new Intent(this, ClasicNumberle.class);
            startActivity(intent);
        });
    }
}