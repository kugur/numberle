package com.kolip.numberle.clasic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kolip.numberle.R;

public class BoxView extends androidx.appcompat.widget.AppCompatTextView {

    private String boxText = "";
    private BoxStatus status = BoxStatus.EMPTY_TEXT;

    public BoxView(@NonNull Context context) {
        super(context);
    }

    public BoxView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setText(boxText);

    }

    public void setBoxText(String boxText) {
        this.boxText = boxText;
        status = boxText.equals("") ? BoxStatus.EMPTY_TEXT : BoxStatus.FILLED_TEXT;
        setText(boxText);
        setStyle();
        invalidate();
        refreshDrawableState();
    }

    public String getText() {
        return String.valueOf(super.getText());
    }

    public void setStatus(BoxStatus status) {
        if (this.status == BoxStatus.CORRECT_POSITION) return;

        this.status = status;
        rotateAnimationAndSetStyle();
    }

    private void setStyle() {
        switch (status) {
            case EMPTY_TEXT:
                setEmptyTextStyle();
                break;
            case FILLED_TEXT:
                setFilledTextStyle();
                break;
            case WRONG_POSITION:
                setWrongPositionStyle();
                break;
            case CORRECT_POSITION:
                setCorrectPositionStyle();
                break;
            default:
                setWrongChar();
        }
    }

    private void rotateAnimationAndSetStyle() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(this, "rotationY", 0f, 90f);
        animation.setDuration(400);
        animation.start();
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setStyle();
                rotateAnimationOpening();
            }
        });
    }

    private void rotateAnimationOpening() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(this, "rotationY", 90f, 0f);
        animation.setDuration(400);
        animation.start();
//        animation.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//            }
//        });
    }

    private void setEmptyTextStyle() {
        setTextColor(getResources().getColor(R.color.black));
        setBackground(getResources().getDrawable(R.drawable.box_background));
    }

    private void setFilledTextStyle() {
        setTextColor(getResources().getColor(R.color.black));
        setBackground(getResources().getDrawable(R.drawable.box_background));
    }

    private void setWrongPositionStyle() {
        setTextColor(getResources().getColor(R.color.white));
        setBackground(null);
        setBackgroundColor(getResources().getColor(R.color.yellow));

    }

    private void setCorrectPositionStyle() {
        setTextColor(getResources().getColor(R.color.white));
        setBackground(null);
        setBackgroundColor(getResources().getColor(R.color.green));
    }

    private void setWrongChar() {
        setTextColor(getResources().getColor(R.color.white));
        setBackground(null);
        setBackgroundColor(getResources().getColor(R.color.grey_background));
    }
}
