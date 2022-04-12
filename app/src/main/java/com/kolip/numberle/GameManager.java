package com.kolip.numberle;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
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
    private String[] enteredNumber = {"", "", "", ""};
    private String correctNumber;
    private Drawable backgroundGrey;
    private Drawable backgroundBlack;

    @SuppressLint("UseCompatLoadingForDrawables")
    public GameManager(MainActivity parent, BoxView[][] boxes, ResultView[] resultViews,
                       String correctNumber) {
        this.boxes = boxes;
        this.parent = parent;
        this.resultViews = resultViews;
        this.correctNumber = correctNumber.equals("") ? generateCorrectNumber() : correctNumber;

        backgroundBlack = parent.getResources().getDrawable(R.drawable.box_background_dark);
        backgroundGrey = parent.getResources().getDrawable(R.drawable.box_background);
        Log.d("Generated number", correctNumber);
    }

    public void enter() {
        if (currentCol != 3) return;

        NumberResult result = generateResult();
        currentCol = 0;
        currentRow++;
        resultViews[currentRow - 1].setValue(result, (numberResult) -> {
            parent.gameFinished(numberResult);
            setBoxFocus(currentRow, currentCol, true);
            clearEnteredNumber();
        });

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
        setBoxFocus(currentRow, currentCol, false);

        if (currentCol < boxes[0].length - 1) {
            currentCol++;
            setBoxFocus(currentRow, currentCol, true);
        }
    }

    public String delete() {
        String deletedKey = "";

        if (boxes[currentRow][currentCol].getText().equals("") && currentCol > 0) {
            setBoxFocus(currentRow, currentCol, false);
            currentCol--;
        }

        boxes[currentRow][currentCol].setText("");
        setBoxFocus(currentRow, currentCol, true);
        deletedKey = enteredNumber[currentCol];
        enteredNumber[currentCol] = "";
//        if (currentCol > 0) {
//            currentCol--;
//        }

        return deletedKey;
    }

    public String newGame() {
        currentCol = 0;
        currentRow = 0;
        clearEnteredNumber();
        correctNumber = generateCorrectNumber();

        for (int row = 0; row < boxes.length; row++) {
            for (int col = 0; col < 4; col++) {
                boxes[row][col].setText("");
                setBoxFocus(row, col, false);
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
            clearEnteredNumber();
        }
    }

    private void setBoxFocus(int row, int col, boolean focus) {
        if (row > 6) return;

        boxes[row][col].setBackground(focus ? backgroundBlack : backgroundGrey);
    }

    private void clearEnteredNumber() {
        for (int i = 0; i < enteredNumber.length; i++) {
            enteredNumber[i] = "";
        }
    }
}
