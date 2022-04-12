package com.kolip.numberle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ResultView extends LinearLayout {
    int[] blockIds = {R.id.result_view_box_1,
            R.id.result_view_box_2,
            R.id.result_view_box_3,
            R.id.result_view_box_4};

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

    public void clear() {
        for (int i = 0; i < blockIds.length; i++) {
            findViewById(blockIds[i])
                    .setBackground(getResources().getDrawable(R.drawable.box_background));
        }
    }

    public void setValue(NumberResult result, Consumer<NumberResult> onFinished) {
        ArrayList<Drawable> colorList = generateColorListForResult(result);

        setOneColor(R.id.result_view_box_1, colorList.get(0),
                () -> setOneColor(R.id.result_view_box_2, colorList.get(1),
                        () -> setOneColor(R.id.result_view_box_3, colorList.get(2),
                                () -> setOneColor(R.id.result_view_box_4, colorList.get(3),
                                        () -> onFinished.accept(result)))));
    }

    private void setOneColor(int viewId, Drawable color, Runnable onEnd) {
        Log.d("setOneColor", "color set " + viewId);
        ObjectAnimator animation = ObjectAnimator.ofArgb(findViewById(viewId), "",
                1, 2);
        animation.setDuration(400);
        animation.start();
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                findViewById(viewId).setBackground(color);
                super.onAnimationEnd(animation);
                if (onEnd != null) {
                    onEnd.run();
                }
            }
        });
    }

    private ArrayList<Drawable> generateColorListForResult(NumberResult result) {
        ArrayList<Drawable> colorList = new ArrayList<>();
        for (int i = 0; i < result.getCorrectPositionNumberCount(); i++) {
            colorList.add(getResources().getDrawable(R.drawable.green_box));
        }

        for (int i = 0; i < result.getWrongPositionNumberCount(); i++) {
            colorList.add(getResources().getDrawable(R.drawable.yellow_box));
        }

        for (int i = 0; i < result.getWrongNumber(); i++) {
            colorList.add(getResources().getDrawable(R.drawable.grey_box));
        }
        return colorList;
    }

    public void setAllColor(NumberResult results) {
        ArrayList<Drawable> colorList = generateColorListForResult(results);
        for (int i = 0; i < blockIds.length; i++) {
            findViewById(blockIds[i]).setBackground(colorList.get(i));
        }
    }
}
