package com.kolip.numberle;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class GameManager {
    private BoxView[][] boxes;
    private ResultView[] resultViews;
    private MainActivity parent;

    private int currentRow;
    private int currentCol;
    private String[] enteredNumber = new String[4];
    private String correctNumber;

    public GameManager(MainActivity parent, BoxView[][] boxes, ResultView[] resultViews) {
        this.boxes = boxes;
        this.parent = parent;
        this.resultViews = resultViews;

        correctNumber = generateCorrectNumber();
        Log.d("Generated number", correctNumber);
    }

    public void enter() {
        NumberResult result = generateResult();
        currentCol = 0;
        currentRow++;
        resultViews[currentRow - 1].setValue(result, parent::gameFinished);
        Log.d("Number Result", result.toString());
    }

    public void write(String text) {
        if (!"".equals(boxes[currentRow][currentCol].getText()) &&
                currentCol == boxes[0].length - 1) {
            return;
        }

        if (!"".equals(boxes[currentRow][currentCol].getText()) &&
                currentCol < boxes[0].length - 1) {
            currentCol++;
        }

        boxes[currentRow][currentCol].setText(text);
        enteredNumber[currentCol] = text;

        if (currentCol < boxes[0].length - 1) {
            currentCol++;
        }


    }

    public void delete() {
        if (boxes[currentRow][currentCol].getText().equals("") && currentCol > 0) {
            currentCol--;
        }

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

    private String generateCorrectNumber() {
        String generatedNumber = "";
        ArrayList<String> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numbers.add(String.valueOf(i));
        }

        Collections.shuffle(numbers);

        for (int i = 0; i < 4; i++) {
            generatedNumber += numbers.get(i);
        }

        return generatedNumber;
    }

    public int getCurrentRow() {
        return  currentRow;
    }
}
