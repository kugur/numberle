package com.kolip.numberle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class ResultView extends LinearLayout {
    public ResultView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeLayout(context);
    }

    public ResultView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeLayout(context);
    }

    public ResultView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                      int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeLayout(context);
    }

    private void initializeLayout(Context context) {
        View.inflate(context, R.layout.result_view_layout, this);
    }
}
