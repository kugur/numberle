package com.kolip.numberle.clasic;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.kolip.numberle.AdManager;
import com.kolip.numberle.CustomNumPad;
import com.kolip.numberle.GameFinishedDialog;
import com.kolip.numberle.GameStatus;
import com.kolip.numberle.ScheduleNotification;
import com.kolip.numberle.R;
import com.kolip.numberle.StatisticUtil;
import com.kolip.numberle.WatchAdDialog;

public class ClasicNumberle extends AppCompatActivity {
    private final static String GAME_FINISHED_DIALOG_TAG = "gameFinishedDialog";

    private CustomNumPad numPad;
    private AdManager adManager;
    private GameManager gameManager;
    private GameStatus gameStatus;
    private StatisticUtil statisticUtil;
    private GameFinishedDialog finishedDialog;
    private WatchAdDialog watchAdDialog;
    private CustomChronometer chronometer;
    private ScheduleNotification notificationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasic_numberle);

        numPad = findViewById(R.id.customNumPad);
        numPad.setKeyListener(this::onKeyClicked);
        chronometer = findViewById(R.id.duration_view);
        chronometer.clear();
        init();
    }

    private void init() {
        adManager = new AdManager(this, findViewById(R.id.adBanner));
        gameStatus = GameStatus.READY;
        statisticUtil = new StatisticUtil(this, null, "clasic");
        gameManager = new GameManager(this, getBoxes(), "", numPad);
        setVisibilities();
        notificationService = new ScheduleNotification(this);

        findViewById(R.id.next_game_button_on_game).setOnClickListener(v -> onNextButtonClick());
        findViewById(R.id.statistic_button_on_game).setOnClickListener(this::showStatistic);
        findViewById(R.id.help_dialog_button).setOnClickListener(this::showHelper);
        ClassicStatics classicStatics = statisticUtil.getClassicStatics();

        Log.d("Statics ", "day " + CustomChronometer.formatElapsedTime(classicStatics.getDayHighScore()) +
                "week " + CustomChronometer.formatElapsedTime(classicStatics.getWeekHighScore()) +
                "month " + CustomChronometer.formatElapsedTime(classicStatics.getMonthHighScore()) +
                "year " + CustomChronometer.formatElapsedTime(classicStatics.getYearHighScore()));
    }

    int i = 0;

    private void onKeyClicked(View view) {
//        notificationService.sendNotification(22);
        if (gameStatus == GameStatus.FINISHED || gameStatus == GameStatus.BEFORE_FINISHED) {
            return;
        }

        if (gameStatus == GameStatus.READY) {
            chronometer.start();
        }

        if (view.getId() == R.id.key_Enter) {

            ClassicStatics statitics = new ClassicStatics(i++, 1L, 1L, 1L);
            if (gameManager.enteredNumber().length() != 5) return;

            gameManager.enter();
        } else if (view.getId() == R.id.key_delete) {
            gameManager.delete();
        } else {
            if (gameManager.enteredNumber().length() >= 5) return;

            gameStatus = GameStatus.PLAYING;
            gameManager.write(String.valueOf(((TextView) view).getText()));
        }
    }

    private BoxView[][] getBoxes() {
        BoxView[][] boxes = {
                {findViewById(R.id.row_1_box_1),
                        findViewById(R.id.row_1_box_2),
                        findViewById(R.id.row_1_box_3),
                        findViewById(R.id.row_1_box_4),
                        findViewById(R.id.row_1_box_5)},
                {
                        findViewById(R.id.row_2_box_1),
                        findViewById(R.id.row_2_box_2),
                        findViewById(R.id.row_2_box_3),
                        findViewById(R.id.row_2_box_4),
                        findViewById(R.id.row_2_box_5)},
                {
                        findViewById(R.id.row_3_box_1),
                        findViewById(R.id.row_3_box_2),
                        findViewById(R.id.row_3_box_3),
                        findViewById(R.id.row_3_box_4),
                        findViewById(R.id.row_3_box_5)},
                {
                        findViewById(R.id.row_4_box_1),
                        findViewById(R.id.row_4_box_2),
                        findViewById(R.id.row_4_box_3),
                        findViewById(R.id.row_4_box_4),
                        findViewById(R.id.row_4_box_5)},
                {
                        findViewById(R.id.row_5_box_1),
                        findViewById(R.id.row_5_box_2),
                        findViewById(R.id.row_5_box_3),
                        findViewById(R.id.row_5_box_4),
                        findViewById(R.id.row_5_box_5)},
                {
                        findViewById(R.id.row_6_box_1),
                        findViewById(R.id.row_6_box_2),
                        findViewById(R.id.row_6_box_3),
                        findViewById(R.id.row_6_box_4),
                        findViewById(R.id.row_6_box_5)}
        };

        return boxes;
    }

    public void gameFinished(boolean guessCorrectly) {
        setStatusByResult(guessCorrectly);
        if (gameStatus == GameStatus.FINISHED) {
            statisticUtil.saveStatistic(guessCorrectly);
            adManager.showFullScreenAd(this);
            ClassicStatisticDialog statisticDialog = ClassicStatisticDialog.newInstance(statisticUtil.getClassicStatics(),
                    true, this::onNextButtonClick,
                    guessCorrectly ? chronometer.getTime() : 0L);
            statisticDialog.show(getSupportFragmentManager(), GAME_FINISHED_DIALOG_TAG);
        }

        setVisibilities();
        Log.d("GameFinished", "Game finished dialog has been finished.");
    }

    private void setStatusByResult(boolean numberResult) {

        if (numberResult || gameManager.getCurrentRow() == 5) {
            gameStatus = GameStatus.FINISHED;
            chronometer.stop();
            if (numberResult) {
                statisticUtil.setClassicScore(chronometer.getTime());
            }
        } else {
            gameStatus = GameStatus.PLAYING;
        }
    }

    private void setVisibilities() {
        switch (gameStatus) {
            case FINISHED:
                findViewById(R.id.next_game_button_on_game).setVisibility(View.VISIBLE);
                break;
            default:
                findViewById(R.id.next_game_button_on_game).setVisibility(View.INVISIBLE);
        }
    }

    private void onNextButtonClick() {
        if (gameStatus != GameStatus.FINISHED) {
            statisticUtil.saveStatistic(false);
        }
//        adManager.initializeFullScreenAd(this);
        gameStatus = GameStatus.READY;
        setVisibilities();
        gameManager.newGame();
        chronometer.clear();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(GAME_FINISHED_DIALOG_TAG);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    private void showStatistic(View view) {
        ClassicStatisticDialog dialog = ClassicStatisticDialog.newInstance(statisticUtil.getClassicStatics(),
                false, null, 0L);
        dialog.show(getSupportFragmentManager(), "statisticDialog");
    }

    private void showHelper(View view) {
        HelpDialog helpDialog = new HelpDialog();
        helpDialog.show(getSupportFragmentManager(), "helpDialog");
    }
}