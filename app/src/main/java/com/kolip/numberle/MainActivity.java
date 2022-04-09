package com.kolip.numberle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    GameManager gameManager;
    GameStatus gameStatus;
    GameStatus previousStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((CustomNumPad) findViewById(R.id.customNumPad)).setKeyListener(v -> onKeyClicked(v));
        init();
    }

    private void init() {
        gameManager = new GameManager(this, getBoxes(), getResultViews());
        gameStatus = GameStatus.READY;
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
        if (gameStatus == GameStatus.FINISHED || gameStatus == GameStatus.BEFORE_FINISHED ||
                gameStatus == GameStatus.IN_PROGRESS) {
            return;
        }

        if (view.getId() == R.id.key_Enter) {
            previousStatus = gameStatus;
            gameStatus = GameStatus.IN_PROGRESS;
            gameManager.enter();
        } else if (view.getId() == R.id.key_delete) {
            gameManager.delete();
        } else {
            gameManager.write(String.valueOf(((TextView) view).getText()));
        }
    }

    public void gameFinished(NumberResult numberResult) {
        setStatus(numberResult);
        if (gameStatus == GameStatus.BEFORE_FINISHED) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Before Finished dialog");
            alertDialogBuilder.create().show();
        }
        Log.d("GameFinished", "Game finished dialog has been finished.");

    }

    private void setStatus(NumberResult numberResult) {
        gameStatus = previousStatus;

        if (numberResult.getCorrectPositionNumberCount() == 4) {
            gameStatus = GameStatus.FINISHED;
        } else if (gameManager.getCurrentRow() == 6) {
            gameStatus = GameStatus.BEFORE_FINISHED;
        } else if (gameStatus == GameStatus.SECOND_CHANGE) {
            gameStatus = GameStatus.FINISHED;
        } else {
            gameStatus = GameStatus.PLAYING;
        }
    }
}