package com.kolip.numberle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.kolip.numberle.clasic.BoxStatus;

import java.util.HashMap;
import java.util.function.Consumer;

public class CustomNumPad extends LinearLayout {
    HashMap<String, View> keyMap = new HashMap<>();
    HashMap<String, BoxStatus> keyStatus = new HashMap<>();

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
        keyMap.put("0", findViewById(R.id.num_0));
        keyMap.put("1", findViewById(R.id.num_1));
        keyMap.put("2", findViewById(R.id.num_2));
        keyMap.put("3", findViewById(R.id.num_3));
        keyMap.put("4", findViewById(R.id.num_4));
        keyMap.put("5", findViewById(R.id.num_5));
        keyMap.put("6", findViewById(R.id.num_6));
        keyMap.put("7", findViewById(R.id.num_7));
        keyMap.put("8", findViewById(R.id.num_8));
        keyMap.put("9", findViewById(R.id.num_9));
        keyMap.put("delete", findViewById(R.id.key_delete));
        keyMap.put("enter", findViewById(R.id.key_Enter));


        keyStatus.put("0", BoxStatus.EMPTY_TEXT);
        keyStatus.put("1", BoxStatus.EMPTY_TEXT);
        keyStatus.put("2", BoxStatus.EMPTY_TEXT);
        keyStatus.put("3", BoxStatus.EMPTY_TEXT);
        keyStatus.put("4", BoxStatus.EMPTY_TEXT);
        keyStatus.put("5", BoxStatus.EMPTY_TEXT);
        keyStatus.put("6", BoxStatus.EMPTY_TEXT);
        keyStatus.put("7", BoxStatus.EMPTY_TEXT);
        keyStatus.put("8", BoxStatus.EMPTY_TEXT);
        keyStatus.put("9", BoxStatus.EMPTY_TEXT);


        keyMap.values().forEach(key -> key.setOnClickListener(keyListener::accept));
    }


    public void setKeyListener(Consumer<View> listener) {
        attachListener(listener);
    }

    public void setDisable(String key, boolean disable) {
        if (key.equals("")) return;

        keyMap.get(key).setClickable(!disable);
        keyMap.get(key).setAlpha(disable ? 0.5f : 1.0f);
    }

    public void setStyle(String key, BoxStatus status) {
        if (status == BoxStatus.CORRECT_POSITION) {
            keyMap.get(key).setBackgroundColor(getResources().getColor(R.color.green));
            ((Button) keyMap.get(key)).setTextColor(getResources().getColor(R.color.white));
            keyStatus.put(key, status);
        } else if (status == BoxStatus.WRONG_POSITION && keyStatus.get(key) != BoxStatus.CORRECT_POSITION) {
            keyMap.get(key).setBackgroundColor(getResources().getColor(R.color.yellow));
            ((Button) keyMap.get(key)).setTextColor(getResources().getColor(R.color.white));
            keyStatus.put(key, status);
        } else if (status == BoxStatus.WRONG_CHAR && keyStatus.get(key) != BoxStatus.CORRECT_POSITION &&
                keyStatus.get(key) != BoxStatus.WRONG_POSITION) {
            keyMap.get(key).setBackgroundColor(getResources().getColor(R.color.gray_600));
            ((Button) keyMap.get(key)).setTextColor(getResources().getColor(R.color.white));
            keyStatus.put(key, status);
        }
    }

    public void clear() {
        for (View value : keyMap.values()) {
            if (value.getId() == R.id.key_Enter || value.getId() == R.id.key_delete) continue;

            ((Button) value).setTextColor(getResources().getColor(R.color.black));
            value.setBackgroundColor(getResources().getColor(R.color.light_grey));
        }

        keyStatus.keySet().forEach(key -> keyStatus.put(key, BoxStatus.EMPTY_TEXT));

        setClickable(true);
        setAlpha(1.0f);
    }

    public void setDisableAll(boolean disable) {
        keyMap.values().forEach(v -> {
            v.setClickable(!disable);
            v.setAlpha(1.0f);
        });
    }
}
