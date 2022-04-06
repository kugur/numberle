package com.kolip.numberle;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BoxView extends androidx.appcompat.widget.AppCompatTextView {

    private String theNumber;

    public BoxView(@NonNull Context context) {
        super(context);
    }

    public BoxView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BoxView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTheNumber(String number) {
        this.theNumber = number;
    }

}
