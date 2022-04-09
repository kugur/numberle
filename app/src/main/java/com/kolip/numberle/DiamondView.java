package com.kolip.numberle;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DiamondView extends ConstraintLayout {
    private String diamondValue;

    public DiamondView(@NonNull Context context) {
        super(context);
        initialize(context, null);
    }

    public DiamondView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public DiamondView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    public DiamondView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.diamond_view, this);
        if (attrs == null) return;

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DiamondView,
                0, 0);

        try {
            diamondValue = a.getString(R.styleable.DiamondView_diamondValue);
        } finally {
            a.recycle();
        }
        ((TextView) findViewById(R.id.diamond_view_text)).setText(diamondValue);
    }
}
