package com.kolip.numberle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.function.Consumer;

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

    public void setValue(NumberResult result, Consumer<NumberResult> onFinished) {
        ArrayList<Integer> colorList = new ArrayList<>();
        for (int i = 0; i < result.getCorrectPositionNumberCount(); i++) {
            colorList.add(Color.GREEN);
        }

        for (int i = 0; i < result.getWrongPositionNumberCount(); i++) {
            colorList.add(Color.YELLOW);
        }

        for (int i = 0; i < result.getWrongNumber(); i++) {
            colorList.add(Color.GRAY);
        }

        setOneColor(R.id.result_view_box_1, colorList.get(0),
                () -> setOneColor(R.id.result_view_box_2, colorList.get(1),
                        () -> setOneColor(R.id.result_view_box_3, colorList.get(2),
                                () -> setOneColor(R.id.result_view_box_4, colorList.get(3),
                                        () -> onFinished.accept(result)))));
    }

    private void setOneColor(int viewId, int color, Runnable onEnd) {
        Log.d("setOneColor", "color set " + viewId);
        ObjectAnimator animation = ObjectAnimator.ofArgb(findViewById(viewId), "backgroundColor",
                getResources().getColor(R.color.background_color_start), color);
        animation.setDuration(400);
        animation.start();
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (onEnd != null) {
                    onEnd.run();
                }
            }
        });
    }
}
