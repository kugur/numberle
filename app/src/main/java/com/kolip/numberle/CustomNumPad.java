package com.kolip.numberle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.function.Consumer;

public class CustomNumPad extends LinearLayout {

    public CustomNumPad(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomNumPad(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CustomNumPad(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_num_pad, this, true);
    }

    private void attachListener(Consumer<View> keyListener) {
        ArrayList<View> keys = new ArrayList<>();
        keys.add(findViewById(R.id.num_0));
        keys.add(findViewById(R.id.num_1));
        keys.add(findViewById(R.id.num_2));
        keys.add(findViewById(R.id.num_3));
        keys.add(findViewById(R.id.num_4));
        keys.add(findViewById(R.id.num_5));
        keys.add(findViewById(R.id.num_6));
        keys.add(findViewById(R.id.num_7));
        keys.add(findViewById(R.id.num_8));
        keys.add(findViewById(R.id.num_9));
        keys.add(findViewById(R.id.key_delete));
        keys.add(findViewById(R.id.key_Enter));

        keys.forEach(key -> key.setOnClickListener(v -> keyListener.accept(v)));
    }

    public void setKeyListener(Consumer<View> listener) {
        attachListener(listener);
    }
}
