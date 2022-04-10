package com.kolip.numberle;

/**
 * Created by ugur.kolip on 12/02/2022.
 * Model for statics that store to show.
 */
public class Statitics {
    private int totalGame;
    private int successRatio;
    private int strike;
    private int maxStrike;

    public Statitics(int totalGame, int successRatio, int strike, int maxStrike) {
        this.totalGame = totalGame;
        this.successRatio = successRatio;
        this.strike = strike;
        this.maxStrike = maxStrike;
    }

    public String getTotalGame() {
        return "" + totalGame;
    }

    public void setTotalGame(int totalGame) {
        this.totalGame = totalGame;
    }

    public String getSuccessRatio() {
        return "" + successRatio;
    }

    public void setSuccessRatio(int successRatio) {
        this.successRatio = successRatio;
    }

    public String getStrike() {
        return "" + strike;
    }

    public void setStrike(int strike) {
        this.strike = strike;
    }

    public String getMaxStrike() {
        return "" + maxStrike;
    }

    public void setMaxStrike(int maxStrike) {
        this.maxStrike = maxStrike;
    }
}
