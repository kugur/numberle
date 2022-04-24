package com.kolip.numberle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;

public class LifeCycleManager {
    private static final String ENTERED_WORDS = "enteredWords";
    private static final String GAME_STATUS = "gameStatus";
    private static final String PREVIOUS_GAME_STATUS = "previousGameStatus";
    private static final String CORRECT_WORD = "correctWord";

    private ArrayList<String> enteredWords = new ArrayList<>();
    private String gameStatus;
    private String previousStatus;
    private String correctWord;
    private String prefix;

    private SharedPreferences sharedPreferences;

    public LifeCycleManager(Activity gameActivity, String prefix) {
        this.prefix = prefix;
        sharedPreferences = gameActivity.getPreferences(Context.MODE_PRIVATE);

        correctWord = sharedPreferences.getString(prefix + CORRECT_WORD, "");
        enteredWords.addAll(stringToList(sharedPreferences.getString(prefix + ENTERED_WORDS, "")));
        gameStatus = sharedPreferences.getString(prefix + GAME_STATUS, GameStatus.READY.name());
        previousStatus = sharedPreferences.getString(prefix + PREVIOUS_GAME_STATUS, GameStatus.READY.name());
    }

    public void addEnteredWord(String enteredWord) {
        enteredWords.add(enteredWord);
    }

    public ArrayList<String> getEnteredWords() {
        return enteredWords;
    }

    public void clearEnteredWord() {
        enteredWords.clear();
    }

    public void setStatus(GameStatus status) {
        gameStatus = status.name();
    }

    public GameStatus getGameStatus() {
        return gameStatus != null ? GameStatus.valueOf(gameStatus) : GameStatus.READY;
    }

    public void persist() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(prefix + ENTERED_WORDS, listToString(enteredWords));
        editor.putString(prefix + CORRECT_WORD, correctWord);
        editor.putString(prefix + GAME_STATUS, gameStatus);
        editor.putString(prefix + PREVIOUS_GAME_STATUS, previousStatus);
        editor.commit();
    }

    public String getCorrectWord() {
        return correctWord;
    }

    public GameStatus getPreviousGameStatus() {
        return previousStatus != null ? GameStatus.valueOf(previousStatus) : GameStatus.READY;
    }

    public void setPreviousGameStatus(GameStatus previousGameStatus) {
        this.previousStatus = previousGameStatus.name();
    }

    public void setCorrectWord(String correctWord) {
        this.correctWord = correctWord;
    }

    private String listToString(ArrayList<String> list) {
        return list.stream().reduce((l1, l2) -> l1 + ";" + l2).orElse("");
    }

    private ArrayList<String> stringToList(String value) {
        ArrayList<String> list = new ArrayList();
        if (value.equals("")) return list;

        list.addAll(Arrays.asList(value.split(";")));
        return list;
    }
}
