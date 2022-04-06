package com.kolip.numberle;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}