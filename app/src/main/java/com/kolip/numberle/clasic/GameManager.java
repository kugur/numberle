package com.kolip.numberle.clasic;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.kolip.numberle.CustomNumPad;
import com.kolip.numberle.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameManager {
    private BoxView[][] boxes;
    private ClasicNumberle parent;

    private int currentRow;
    private int currentCol;
    private String[] enteredNumber = {"", "", "", "", ""};
    private String correctNumber;
    private Drawable backgroundGrey;
    private Drawable backgroundBlack;
    private CustomNumPad numPad;

    @SuppressLint("UseCompatLoadingForDrawables")
    public GameManager(ClasicNumberle parent, BoxView[][] boxes, String correctNumber, CustomNumPad numPad) {
        this.boxes = boxes;
        this.parent = parent;
        this.correctNumber = correctNumber.equals("") ? generateCorrectNumber() : correctNumber;
        this.numPad = numPad;

        backgroundBlack = parent.getResources().getDrawable(R.drawable.box_background_dark);
        backgroundGrey = parent.getResources().getDrawable(R.drawable.box_background);
        Log.d("Generated number", correctNumber);
    }

    public void enter() {
        if (currentCol != 4) return;

        boolean result = generateResult();
        parent.gameFinished(result);
        currentCol = 0;
        currentRow++;

        setBoxFocus(currentRow, currentCol, true);
        clearEnteredNumber();
    }

    private boolean generateResult() {
        return validateAndSetColors();
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

        return deletedKey;
    }

    public String newGame() {
        currentCol = 0;
        currentRow = 0;
        clearEnteredNumber();
        numPad.clear();
        correctNumber = generateCorrectNumber();

        for (int rowIndex = 0; rowIndex < 6; rowIndex++) {
            for (int columnIndex = 0; columnIndex < 5; columnIndex++) {
                boxes[rowIndex][columnIndex].setBoxText("");
            }
        }


        return correctNumber;
    }

    public String generateCorrectNumber() {
        String generatedNumber = "";
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            generatedNumber += random.nextInt(9);
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
        numPad.clear();
        for (int row = 0; row < enteredWords.size(); row++) {
            write(String.valueOf(enteredWords.get(row).charAt(0)));
            write(String.valueOf(enteredWords.get(row).charAt(1)));
            write(String.valueOf(enteredWords.get(row).charAt(2)));
            write(String.valueOf(enteredWords.get(row).charAt(3)));
            write(String.valueOf(enteredWords.get(row).charAt(4)));

            validateAndSetColors();
            currentRow++;
            currentCol = 0;

            clearEnteredNumber();
        }
    }

    private void setBoxFocus(int row, int col, boolean focus) {
        if (row > 5) return;

        boxes[row][col].setBackground(focus ? backgroundBlack : backgroundGrey);
    }

    private void clearEnteredNumber() {
        for (int i = 0; i < enteredNumber.length; i++) {
            enteredNumber[i] = "";
        }
    }


    private boolean validateAndSetColors() {
        int enteredWordIndex = 0;
        boolean wordMatched = true;
        String charAtIndexOfCorrectWord;

        BoxStatus[] enteredWordStatus = {BoxStatus.EMPTY_TEXT, BoxStatus.EMPTY_TEXT,
                BoxStatus.EMPTY_TEXT, BoxStatus.EMPTY_TEXT, BoxStatus.EMPTY_TEXT};
        ArrayList<String> rowNotCorrectPositionNumbers = new ArrayList<>();
        ArrayList<String> rowCorrectPositionNumbers = new ArrayList<>();


        //Find and set Correct Position Numbers
        for (String enteredChar : enteredNumber) {
            charAtIndexOfCorrectWord = String.valueOf(correctNumber.charAt(enteredWordIndex));

            if (enteredChar.equals(charAtIndexOfCorrectWord)) {
                enteredWordStatus[enteredWordIndex] = BoxStatus.CORRECT_POSITION;
                rowCorrectPositionNumbers.add(enteredChar);
            }
            enteredWordIndex++;
        }

        //Set not Correct Position Letters and not correct letters.
        enteredWordIndex = 0;
        for (String enteredChar : enteredNumber) {

            charAtIndexOfCorrectWord = String.valueOf(correctNumber.charAt(enteredWordIndex));

            if (enteredChar.equals(charAtIndexOfCorrectWord)) {
                enteredWordIndex++;
                continue;
            }

            if (correctNumber.contains(enteredChar) &&
                    countOfLetter(Arrays.asList(correctNumber.split("")), enteredChar) >
                            countOfLetter(rowCorrectPositionNumbers, enteredChar) +
                                    countOfLetter(rowNotCorrectPositionNumbers, enteredChar)) {
                enteredWordStatus[enteredWordIndex] = BoxStatus.WRONG_POSITION;
                wordMatched = false;
                rowNotCorrectPositionNumbers.add(enteredChar);
            } else {
                enteredWordStatus[enteredWordIndex] = BoxStatus.WRONG_CHAR;
                wordMatched = false;
            }

            enteredWordIndex++;
        }

        for (int i = 0; i < enteredWordStatus.length; i++) {
            this.boxes[currentRow][i].setStatus(enteredWordStatus[i]);
            numPad.setStyle(enteredNumber[i], enteredWordStatus[i]);
        }
        return wordMatched;
    }

    public long countOfLetter(List<String> list, String letter) {
        return list.stream()
                .filter(correctLetter -> correctLetter.equals(letter))
                .count();
    }
}
