package com.kolip.numberle;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GameManager {
    private BoxView[][] boxes;
    private ResultView[] resultViews;
    private MainActivity parent;

    private int currentRow;
    private int currentCol;
    private String[] enteredNumber = new String[4];
    private String correctNumber;

    public GameManager(MainActivity parent, BoxView[][] boxes, ResultView[] resultViews,
                       String correctNumber) {
        this.boxes = boxes;
        this.parent = parent;
        this.resultViews = resultViews;
        this.correctNumber = correctNumber.equals("") ? generateCorrectNumber() : correctNumber;

        Log.d("Generated number", correctNumber);
    }

    public void enter() {
        if (currentCol != 3) return;

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

    public String delete() {
        String deletedKey = "";
        if (boxes[currentRow][currentCol].getText().equals("") && currentCol > 0) {
            currentCol--;
        }

        boxes[currentRow][currentCol].setText("");
        deletedKey = enteredNumber[currentCol];
        enteredNumber[currentCol] = "";

        if (currentCol > 0) {
            currentCol--;
        }

        return deletedKey;
    }

    public String newGame() {
        currentCol = 0;
        currentRow = 0;
        enteredNumber = new String[4];
        correctNumber = generateCorrectNumber();

        for (int row = 0; row < boxes.length; row++) {
            for (int col = 0; col < 4; col++) {
                boxes[row][col].setText("");
            }
        }

        for (int i = 0; i < resultViews.length; i++) {
            resultViews[i].clear();
        }

        return correctNumber;
    }

    public NumberResult generateResult() {
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

    public String generateCorrectNumber() {
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
        return currentRow;
    }

    public String getCorrectNumber() {
        return correctNumber;
    }

    public String enteredNumber() {
        return Arrays.stream(enteredNumber).reduce("", (s1, s2) -> s1 + s2);
    }

    public void initializeNumbers(ArrayList<String> enteredWords) {
        for (int row = 0; row < enteredWords.size(); row++) {
            write(String.valueOf(enteredWords.get(row).charAt(0)));
            write(String.valueOf(enteredWords.get(row).charAt(1)));
            write(String.valueOf(enteredWords.get(row).charAt(2)));
            write(String.valueOf(enteredWords.get(row).charAt(3)));

            resultViews[currentRow].setAllColor(generateResult());
            currentRow++;
            currentCol = 0;
        }
    }
}
