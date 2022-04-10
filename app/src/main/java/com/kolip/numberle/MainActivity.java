package com.kolip.numberle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private final static String GAME_FINISHED_DIALOG_TAG = "gameFinishedDialog";
    private final int RESULT_ANIME_DURATION = 400;

    GameManager gameManager;
    GameStatus gameStatus;
    GameStatus previousStatus;
    StatisticUtil statisticUtil;
    GameFinishedDialog finishedDialog;
    LifeCycleManager lifeCycleManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((CustomNumPad) findViewById(R.id.customNumPad)).setKeyListener(this::onKeyClicked);
        init();
    }

    @Override
    protected void onStop() {
        super.onStop();
        lifeCycleManager.setStatus(gameStatus);
        if (gameStatus == GameStatus.IN_PROGRESS) {
            lifeCycleManager.setPreviousGameStatus(previousStatus);
        }
        lifeCycleManager.setCorrectWord(gameManager.getCorrectNumber());
        lifeCycleManager.persist();
    }

    private void init() {
        statisticUtil = new StatisticUtil(this, findViewById(R.id.scoreView));
        lifeCycleManager = new LifeCycleManager(this);
        gameManager = new GameManager(this, getBoxes(), getResultViews(),
                lifeCycleManager.getCorrectWord());

        gameStatus = lifeCycleManager.getGameStatus();
        setVisibilities();

        gameManager.initializeNumbers(lifeCycleManager.getEnteredWords());
        ((ScoreView) findViewById(R.id.scoreView)).setScore(statisticUtil.getScore() + "");

        findViewById(R.id.give_lif_on_game).setOnClickListener(this::onGiveLifeClick);
        findViewById(R.id.next_game_button_on_game).setOnClickListener(this::onNextButtonClick);
        findViewById(R.id.statistic_button_on_game).setOnClickListener(this::showStatistic);
        findViewById(R.id.help_dialog_button).setOnClickListener(this::showHelper);

        if (gameStatus == GameStatus.IN_PROGRESS) {
            previousStatus = lifeCycleManager.getPreviousGameStatus();
            gameFinished(gameManager.generateResult());
        }
    }

    private ResultView[] getResultViews() {
        ResultView[] resultViews = {
                findViewById(R.id.row_1_result),
                findViewById(R.id.row_2_result),
                findViewById(R.id.row_3_result),
                findViewById(R.id.row_4_result),
                findViewById(R.id.row_5_result),
                findViewById(R.id.row_6_result),
                findViewById(R.id.row_7_result)
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
                        findViewById(R.id.row_6_col_4)},
                {
                        findViewById(R.id.row_7_col_1),
                        findViewById(R.id.row_7_col_2),
                        findViewById(R.id.row_7_col_3),
                        findViewById(R.id.row_7_col_4)}
        };

        return boxes;
    }

    private void onKeyClicked(View view) {
        if (gameStatus == GameStatus.FINISHED || gameStatus == GameStatus.BEFORE_FINISHED ||
                gameStatus == GameStatus.IN_PROGRESS) {
            return;
        }

        if (view.getId() == R.id.key_Enter) {
            if (gameManager.enteredNumber().length() != 4) return;

            lifeCycleManager.addEnteredWord(gameManager.enteredNumber());
            previousStatus = gameStatus;
            gameStatus = GameStatus.IN_PROGRESS;
            gameManager.enter();
        } else if (view.getId() == R.id.key_delete) {
            gameManager.delete();
        } else {
            gameManager.write(String.valueOf(((TextView) view).getText()));
        }
    }

    /**
     * Will be Called when
     *
     * @param numberResult
     */
    public void gameFinished(NumberResult numberResult) {
        setStatusByResult(numberResult);
        if (gameStatus == GameStatus.FINISHED) {
            setScore();
            statisticUtil.saveStatistic(numberResult.getCorrectPositionNumberCount() == 4);
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
            finishedDialog.show(getSupportFragmentManager(), GAME_FINISHED_DIALOG_TAG);
        }
        Log.d("GameFinished", "Game finished dialog has been finished.");
    }

    private void setVisibilities() {
        switch (gameStatus) {
            case BEFORE_FINISHED:
                findViewById(R.id.give_lif_on_game).setVisibility(View.VISIBLE);
                findViewById(R.id.next_game_button_on_game).setVisibility(View.VISIBLE);
                findViewById(R.id.row_7).setVisibility(View.INVISIBLE);
                break;
            case SECOND_CHANGE:
                findViewById(R.id.give_lif_on_game).setVisibility(View.INVISIBLE);
                findViewById(R.id.next_game_button_on_game).setVisibility(View.INVISIBLE);
                findViewById(R.id.row_7).setVisibility(View.VISIBLE);
                break;
            case FINISHED:
                findViewById(R.id.next_game_button_on_game).setVisibility(View.VISIBLE);
                findViewById(R.id.row_7).setVisibility(View.VISIBLE);
                break;
            default:
                findViewById(R.id.give_lif_on_game).setVisibility(View.INVISIBLE);
                findViewById(R.id.next_game_button_on_game).setVisibility(View.INVISIBLE);
                findViewById(R.id.row_7).setVisibility(View.INVISIBLE);
        }
    }

    private void onNextButtonClick(View view) {
        if (gameStatus != GameStatus.FINISHED) {
            statisticUtil.saveStatistic(false);
        }
        gameStatus = GameStatus.READY;
        setVisibilities();
        lifeCycleManager.clearEnteredWord();
        lifeCycleManager.setCorrectWord(gameManager.newGame());
        if (getSupportFragmentManager().findFragmentByTag(GAME_FINISHED_DIALOG_TAG) != null) {
            finishedDialog.dismiss();
        }
    }

    private void onGiveLifeClick(View view) {
        int score = statisticUtil.getScore();
        if (score < 2) {
            //TODO(ugur) show reward ads
            return;
        }
        gameStatus = GameStatus.SECOND_CHANGE;
        statisticUtil.setScore(score - 2);
        setVisibilities();
        if (getSupportFragmentManager().findFragmentByTag(GAME_FINISHED_DIALOG_TAG) != null) {
            finishedDialog.dismiss();
        }
    }

    private void setStatusByResult(NumberResult numberResult) {
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

    private void showStatistic(View view) {
        StatisticDialog statisticDialog = new StatisticDialog(statisticUtil.getStatics());
        statisticDialog.show(getSupportFragmentManager(), "statisticDialog");
    }

    private void showHelper(View view) {
        HelpDialog helpDialog = new HelpDialog();
        helpDialog.show(getSupportFragmentManager(), "helpDialog");
    }
}