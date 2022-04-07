package com.kolip.numberle;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((CustomNumPad) findViewById(R.id.customNumPad)).setKeyListener(v -> onKeyClicked(v));
        init();
    }

    private void init() {
        gameManager = new GameManager(this, getBoxes(), getResultViews());
    }

    private ResultView[] getResultViews() {
        ResultView[] resultViews = {
                findViewById(R.id.row_1_result),
                findViewById(R.id.row_2_result),
                findViewById(R.id.row_3_result),
                findViewById(R.id.row_4_result),
                findViewById(R.id.row_5_result),
                findViewById(R.id.row_6_result)
        };
        return resultViews;
    }

    private BoxView[][] getBoxes() {
        BoxView[][] boxes = {
                {findViewById(R.id.row_1_col_1),
                        findViewById(R.id.row_1_col_2),
                        findViewById(R.id.row_1_col_3),
                        findViewById(R.id.row_1_col_4)},
                {
                        findViewById(R.id.row_2_col_1),
                        findViewById(R.id.row_2_col_2),
                        findViewById(R.id.row_2_col_3),
                        findViewById(R.id.row_2_col_4)},
                {
                        findViewById(R.id.row_3_col_1),
                        findViewById(R.id.row_3_col_2),
                        findViewById(R.id.row_3_col_3),
                        findViewById(R.id.row_3_col_4)},
                {
                        findViewById(R.id.row_4_col_1),
                        findViewById(R.id.row_4_col_2),
                        findViewById(R.id.row_4_col_3),
                        findViewById(R.id.row_4_col_4)},
                {
                        findViewById(R.id.row_5_col_1),
                        findViewById(R.id.row_5_col_2),
                        findViewById(R.id.row_5_col_3),
                        findViewById(R.id.row_5_col_4)},
                {
                        findViewById(R.id.row_6_col_1),
                        findViewById(R.id.row_6_col_2),
                        findViewById(R.id.row_6_col_3),
                        findViewById(R.id.row_6_col_4)}
        };

        return boxes;
    }

    private void onKeyClicked(View view) {
        if (view.getId() == R.id.key_Enter) {
            gameManager.enter();
        } else if (view.getId() == R.id.key_delete) {
            gameManager.delete();
        } else {
            gameManager.write(String.valueOf(((TextView) view).getText()));
        }
    }
}