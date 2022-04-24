package com.kolip.numberle.clasic;

public class ClassicStatics {
    private long dayHighScore;
    private long weekHighScore;
    private long monthHighScore;
    private long yearHighScore;

    public ClassicStatics(long dayHighScore, long weekHighScore, long monthHighScore, long yearHighScore) {
        this.dayHighScore = dayHighScore;
        this.weekHighScore = weekHighScore;
        this.monthHighScore = monthHighScore;
        this.yearHighScore = yearHighScore;
    }

    public long getDayHighScore() {
        return dayHighScore;
    }

    public void setDayHighScore(long dayHighScore) {
        this.dayHighScore = dayHighScore;
    }

    public long getWeekHighScore() {
        return weekHighScore;
    }

    public void setWeekHighScore(long weekHighScore) {
        this.weekHighScore = weekHighScore;
    }

    public long getMonthHighScore() {
        return monthHighScore;
    }

    public void setMonthHighScore(long monthHighScore) {
        this.monthHighScore = monthHighScore;
    }

    public long getYearHighScore() {
        return yearHighScore;
    }

    public void setYearHighScore(long yearHighScore) {
        this.yearHighScore = yearHighScore;
    }
}
