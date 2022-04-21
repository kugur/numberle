package com.kolip.numberle.clasic;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kolip.numberle.AdManager;
import com.kolip.numberle.CustomNumPad;
import com.kolip.numberle.GameFinishedDialog;
import com.kolip.numberle.GameStatus;
import com.kolip.numberle.LifeCycleManager;
import com.kolip.numberle.R;
import com.kolip.numberle.ScoreView;
import com.kolip.numberle.StatisticDialog;
import com.kolip.numberle.StatisticUtil;
import com.kolip.numberle.WatchAdDialog;

public class ClasicNumberle extends AppCompatActivity {
    private final static String GAME_FINISHED_DIALOG_TAG = "gameFinishedDialog";

    private CustomNumPad numPad;
    private AdManager adManager;
    private GameManager gameManager;
    private LifeCycleManager lifeCycleManager;
    private GameStatus gameStatus;
    private StatisticUtil statisticUtil;
    GameFinishedDialog finishedDialog;
    WatchAdDialog watchAdDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasic_numberle);

        numPad = findViewById(R.id.customNumPad);
        numPad.setKeyListener(this::onKeyClicked);
        init();
    }

    @Override
    protected void onStop() {
        super.onStop();
        lifeCycleManager.setStatus(gameStatus);
        lifeCycleManager.setCorrectWord(gameManager.getCorrectNumber());
        lifeCycleManager.persist();
    }

    private void init() {
//        adManager = new AdManager(this, findViewById(R.id.adBanner));
        watchAdDialog = new WatchAdDialog(adManager, (dimaond) -> onRewardAdsFinished(dimaond));

        ((ScoreView) findViewById(R.id.scoreView)).setOnAddClickListener(() -> {
            watchAdDialog.show(getSupportFragmentManager(), "watch");
        });

        statisticUtil = new StatisticUtil(this, findViewById(R.id.scoreView), "clasic");
        lifeCycleManager = new LifeCycleManager(this, "clasic");
        gameManager = new GameManager(this, getBoxes(), lifeCycleManager.getCorrectWord(), numPad);

        gameStatus = lifeCycleManager.getGameStatus();
        setVisibilities();

        gameManager.initializeNumbers(lifeCycleManager.getEnteredWords());
        ((ScoreView) findViewById(R.id.scoreView)).setScore(statisticUtil.getScore() + "");

        findViewById(R.id.give_lif_on_game).setOnClickListener(this::onGiveLifeClick);
        findViewById(R.id.next_game_button_on_game).setOnClickListener(this::onNextButtonClick);
        findViewById(R.id.statistic_button_on_game).setOnClickListener(this::showStatistic);
        findViewById(R.id.help_dialog_button).setOnClickListener(this::showHelper);
    }

    private void onKeyClicked(View view) {
        if (gameStatus == GameStatus.FINISHED || gameStatus == GameStatus.BEFORE_FINISHED) {
            return;
        }

        if (view.getId() == R.id.key_Enter) {
            if (gameManager.enteredNumber().length() != 5) return;

            lifeCycleManager.addEnteredWord(gameManager.enteredNumber());
            gameManager.enter();
        } else if (view.getId() == R.id.key_delete) {
            gameManager.delete();
        } else {
            if (gameManager.enteredNumber().length() >= 5) return;

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
            setScore();
            statisticUtil.saveStatistic(guessCorrectly);
//            adManager.showFullScreenAd(this);
        }

        finishedDialog = new GameFinishedDialog(this::onGiveLifeClick,
                this::onNextButtonClick, statisticUtil.getStatics());
        setVisibilities();
        if (gameStatus == GameStatus.BEFORE_FINISHED) {
            finishedDialog.setTitle(getResources().getString(R.string.statistic_previous));
            finishedDialog.show(getSupportFragmentManager(), GAME_FINISHED_DIALOG_TAG);
        } else if (gameStatus == GameStatus.FINISHED) {
            finishedDialog.setTitle(getResources().getString(R.string.statistic));
            finishedDialog.showGiveLife(false);
            finishedDialog.setDescription(guessCorrectly ? gameManager.getCorrectNumber() : "");
            finishedDialog.show(getSupportFragmentManager(), GAME_FINISHED_DIALOG_TAG);
        }
        Log.d("GameFinished", "Game finished dialog has been finished.");

    }

    private void setStatusByResult(boolean numberResult) {

        if (numberResult) {
            gameStatus = GameStatus.FINISHED;
        } else if (gameManager.getCurrentRow() == 5) {
            gameStatus = GameStatus.BEFORE_FINISHED;
        } else if (gameStatus == GameStatus.SECOND_CHANGE) {
            gameStatus = GameStatus.FINISHED;
        } else {
            gameStatus = GameStatus.PLAYING;
        }
    }

    private void setScore() {
        int score = 0;
        switch (gameManager.getCurrentRow()) {
            case 0:
                score = 4;
                break;
            case 1:
                score = 3;
                break;
            case 2:
                score = 2;
                break;
            case 3:
                score = 1;
                break;
        }
        statisticUtil.setScore(statisticUtil.getScore() + score);
    }

    private void setVisibilities() {
        switch (gameStatus) {
            case BEFORE_FINISHED:
                findViewById(R.id.give_lif_on_game).setVisibility(View.VISIBLE);
                findViewById(R.id.next_game_button_on_game).setVisibility(View.VISIBLE);
                findViewById(R.id.row_6).setVisibility(View.INVISIBLE);
                break;
            case SECOND_CHANGE:
                findViewById(R.id.give_lif_on_game).setVisibility(View.INVISIBLE);
                findViewById(R.id.next_game_button_on_game).setVisibility(View.INVISIBLE);
                findViewById(R.id.row_6).setVisibility(View.VISIBLE);
                break;
            case FINISHED:
                findViewById(R.id.next_game_button_on_game).setVisibility(View.VISIBLE);
                findViewById(R.id.row_6).setVisibility(View.VISIBLE);
                break;
            default:
                findViewById(R.id.give_lif_on_game).setVisibility(View.INVISIBLE);
                findViewById(R.id.next_game_button_on_game).setVisibility(View.INVISIBLE);
                findViewById(R.id.row_6).setVisibility(View.INVISIBLE);
        }
    }

    private void onRewardAdsFinished(Integer dimaond) {
        statisticUtil.setScore(statisticUtil.getScore() + dimaond);
    }

    private void onGiveLifeClick(View view) {
        int score = statisticUtil.getScore();
        if (score < 2) {
            watchAdDialog.show(getSupportFragmentManager(), "watchDialog");
            return;
        }
        gameStatus = GameStatus.SECOND_CHANGE;
        statisticUtil.setScore(score - 2);
        setVisibilities();
        if (getSupportFragmentManager().findFragmentByTag(GAME_FINISHED_DIALOG_TAG) != null) {
            finishedDialog.dismiss();
        }
    }

    private void onNextButtonClick(View view) {
        if (gameStatus != GameStatus.FINISHED) {
            statisticUtil.saveStatistic(false);
        }
//        adManager.initializeFullScreenAd(this);
        gameStatus = GameStatus.READY;
        setVisibilities();
        lifeCycleManager.clearEnteredWord();
        lifeCycleManager.setCorrectWord(gameManager.newGame());
        if (getSupportFragmentManager().findFragmentByTag(GAME_FINISHED_DIALOG_TAG) != null) {
            finishedDialog.dismiss();
        }
    }

    private void showStatistic(View view) {
        StatisticDialog statisticDialog = new StatisticDialog(statisticUtil.getStatics());
        statisticDialog.show(getSupportFragmentManager(), "statisticDialog");
    }

    private void showHelper(View view) {
        HelpDialog helpDialog = new HelpDialog();
        helpDialog.show(getSupportFragmentManager(), "helpDialog");
    }
}