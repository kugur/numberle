package com.kolip.numberle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class StatisticUtil {
    private static final String STATISTIC_CONTEXT_NAME = "statistic_numberle";
    private static final String TOTAL_GAME = "totalGame";
    private static final String TOTAL_GUESS_CORRECTLY = "totalSuccess";
    private static final String STRIKE_COUNT = "strikeCount";
    private static final String MAX_STRIKE_COUNT = "maxStrikeCount";
    private static final String SCORE_DIAMOND = "diamondScore";

    private int totalGame;
    private int totalGuessCorrectly;
    private int strikeCount;
    private int maxStrikeCount;

    private SharedPreferences.Editor editor;
    ScoreView scoreView;

    private SharedPreferences sharedPreferences;

    public StatisticUtil(Activity gameActivity, ScoreView scoreView) {
        sharedPreferences = gameActivity.getSharedPreferences(STATISTIC_CONTEXT_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        this.scoreView = scoreView;
        initializeValues();
    }

    private void initializeValues() {
        totalGame = sharedPreferences.getInt(TOTAL_GAME, 0);
        totalGuessCorrectly = sharedPreferences.getInt(TOTAL_GUESS_CORRECTLY, 0);
        strikeCount = sharedPreferences.getInt(STRIKE_COUNT, 0);
        maxStrikeCount = sharedPreferences.getInt(MAX_STRIKE_COUNT, 0);
    }

    public void saveStatistic(boolean guessCorrectly) {

        if (guessCorrectly) {
            strikeCount++;
            totalGuessCorrectly++;
            maxStrikeCount = Math.max(strikeCount, maxStrikeCount);
        } else {
            strikeCount = 0;
        }
        editor.putInt(TOTAL_GAME, ++totalGame);
        editor.putInt(TOTAL_GUESS_CORRECTLY, totalGuessCorrectly);
        editor.putInt(STRIKE_COUNT, strikeCount);
        editor.putInt(MAX_STRIKE_COUNT, maxStrikeCount);

        editor.commit();
    }

    public Statitics getStatics() {
        return new Statitics(totalGame,
                totalGuessCorrectly > 0 ? (int) (100 * totalGuessCorrectly / totalGame) : 0,
                strikeCount, maxStrikeCount);
    }

    public void setScore(int score) {
        editor.putInt(SCORE_DIAMOND, score);
        editor.commit();
        scoreView.setScore("" + score);
    }

    public int getScore() {
        return sharedPreferences.getInt(SCORE_DIAMOND, 0);
    }
}
