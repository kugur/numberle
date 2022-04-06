package com.kolip.numberle;

import android.util.Log;

public class GameManager {
    private BoxView[][] boxes;

    private int currentRow;
    private int currentCol;
    private String[] enteredNumber = new String[4];
    private String correctNumber = "1234";

    public GameManager(BoxView[][] boxes) {
        this.boxes = boxes;
    }

    public void enter() {
        NumberResult result = generateResult();
        Log.d("Number Result", result.toString());
        setResult(result);
        currentCol = 0;
        currentRow++;

    }

    public void write(String text) {
        if (!"".equals(boxes[currentRow][currentCol].getText()) &&
                currentCol == boxes[0].length - 1) {
            return;
        }

        boxes[currentRow][currentCol].setText(text);
        enteredNumber[currentCol] = text;

        if (currentCol < boxes[0].length - 1) {
            currentCol++;
        }

    }

    public void delete() {
        boxes[currentRow][currentCol].setText("");
        enteredNumber[currentCol] = "";

        if (currentCol > 0) {
            currentCol--;
        }
    }

    private NumberResult generateResult() {
        int correctPositionNumberCount = 0;
        int wrongPositionNumberCount = 0;
        int wrongNumberCount = 0;

        for (int i = 0; i < enteredNumber.length; i++) {
            if (enteredNumber[i].equals(String.valueOf(correctNumber.charAt(i)))) {
                correctPositionNumberCount++;
            } else if (correctNumber.contains(enteredNumber[i])) {
                wrongPositionNumberCount++;
            } else {
                wrongNumberCount++;
            }
        }

        return new NumberResult(correctPositionNumberCount, wrongPositionNumberCount,
                wrongNumberCount);
    }

    private void setResult(NumberResult result) {
        //TODO(Ugur) result should be set to resultViewc
    }
}
