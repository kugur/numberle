package com.kolip.numberle;

public class NumberResult {
    private int correctPositionNumberCount;
    private int wrongPositionNumberCount;
    private int wrongNumber;

    public NumberResult(int correctPositionNumberCount, int wrongPositionNumberCount,
                        int wrongNumber) {
        this.correctPositionNumberCount = correctPositionNumberCount;
        this.wrongPositionNumberCount = wrongPositionNumberCount;
        this.wrongNumber = wrongNumber;
    }

    public int getCorrectPositionNumberCount() {
        return correctPositionNumberCount;
    }

    public void setCorrectPositionNumberCount(int correctPositionNumberCount) {
        this.correctPositionNumberCount = correctPositionNumberCount;
    }

    public int getWrongPositionNumberCount() {
        return wrongPositionNumberCount;
    }

    public void setWrongPositionNumberCount(int wrongPositionNumberCount) {
        this.wrongPositionNumberCount = wrongPositionNumberCount;
    }

    public int getWrongNumber() {
        return wrongNumber;
    }

    public void setWrongNumber(int wrongNumber) {
        this.wrongNumber = wrongNumber;
    }

    @Override
    public String toString() {
        return "NumberResult{" +
                "correctPositionNumberCount=" + correctPositionNumberCount +
                ", wrongPositionNumberCount=" + wrongPositionNumberCount +
                ", wrongNumber=" + wrongNumber +
                '}';
    }
}
