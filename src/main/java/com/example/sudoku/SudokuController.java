/**
 * The SudokuController class is the controller for the Sudoku game.
 * It handles the user interactions and logic for validating the Sudoku board.
 */
package com.example.sudoku;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class SudokuController {

    private final int SIZE = 9;
    private final int BLOCK_SIZE = 3;
    private final int POSITIONS = 2;
    private final int ROW_PLUS_COL_IN_START_POSITION = 6;

    @FXML
    private GridPane grid;

    private TextField text[][];

    /**
     * The initialize method is called after the FXML file is loaded.
     * It initializes the Sudoku board grid and sets up event handlers
     * for handling user input.
     */
    public void initialize() {
        text = new TextField[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                final int currentRow = i;
                final int currentCol = j;
                text[i][j] = new TextField();
                updateBoardColors(i, j, "black");

                text[i][j].setPrefSize(grid.getPrefWidth() / SIZE,
                        grid.getPrefHeight() / SIZE);
                grid.add(text[i][j], i, j);
                text[i][j].setAlignment(Pos.CENTER);
                text[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        handleTextField(event, currentRow, currentCol);
                    }
                });
            }
        }
    }

    /**
     * The handleTextField method is called when a TextField is edited.
     * It validates the user input and displays an error message if the
     * input is invalid.
     *
     * @param event the action event triggered by the TextField
     * @param row   the row index of the TextField in the Sudoku board
     * @param col   the column index of the TextField in the Sudoku board
     */
    private void handleTextField(ActionEvent event, int row, int col) {
        TextField currentTextField = (TextField) event.getSource();
        String errorMessage = "The digit already exists in the current ";
        boolean isValidInput = false;
        if (!isValidDigit(currentTextField.getText())) {
            errorMessage = "Please type a number between 1 and 9.";
        } else if (isInBlock(row, col, currentTextField.getText())) {
            errorMessage = errorMessage.concat("block.");
        } else if (isInRow(currentTextField, row, col)) {
            errorMessage = errorMessage.concat("row.");
        } else if (isInCol(currentTextField, row, col)) {
            errorMessage = errorMessage.concat("column.");
        } else {
            isValidInput = true;
        }

        if (!isValidInput) {
            currentTextField.clear();
            makeAlert(errorMessage);
        }
    }

    /**
     * The isInRow method checks if a digit exists in the same row,
     * excluding the current TextField.
     *
     * @param currentTextField the current TextField
     * @param row              the row index of the TextField
     * @param col              the column index of the TextField
     * @return true if the digit exists in the row, false otherwise
     */
    private boolean isInRow(TextField currentTextField, int row, int col) {
        for (int k = 0; k < SIZE; k++) {
            if (text[k][col].getText().equals(currentTextField.getText()) && k != row) {
                return true;
            }
        }
        return false;
    }

    /**
     * The isInCol method checks if a digit exists in the same column,
     * excluding the current TextField.
     *
     * @param currentTextField the current TextField
     * @param row              the row index of the TextField
     * @param col              the column index of the TextField
     * @return true if the digit exists in the column, false otherwise
     */
    private boolean isInCol(TextField currentTextField, int row, int col) {
        for (int k = 0; k < SIZE; k++) {
            if (text[row][k].getText().equals(currentTextField.getText()) && k != col) {
                return true;
            }
        }
        return false;
    }

    /**
     * The makeAlert method displays an error alert with the specified content.
     *
     * @param content the content of the error alert
     */
    private void makeAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid input:");
        alert.setContentText(content);
        Optional<ButtonType> option = alert.showAndWait();
    }

    /**
     * The isInBlock method checks if a digit exists in the same block,
     * excluding the current TextField.
     *
     * @param row   the row index of the TextField
     * @param col   the column index of the TextField
     * @param digit the digit to check for
     * @return true if the digit exists in the block, false otherwise
     */
    private boolean isInBlock(int row, int col, String digit) {
        int position[] = getStartBlock(row, col);
        for (int i = position[0]; i < position[0] + BLOCK_SIZE; i++) {
            for (int j = position[1]; j < position[1] + BLOCK_SIZE; j++) {
                if (text[i][j].getText().equals(digit) && i != row && j != col) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * The getStartBlock method calculates the starting position of the block
     * based on the given row and column indices.
     *
     * @param row the row index
     * @param col the column index
     * @return an array containing the starting row and column indices of the block
     */
    public int[] getStartBlock(int row, int col) {
        int blockRow = row - (row % BLOCK_SIZE);
        int blockCol = col - (col % BLOCK_SIZE);
        int position[] = {blockRow, blockCol};
        return position;
    }

    /**
     * The isValidDigit method checks if the given string represents a valid digit.
     *
     * @param currentText the string to check
     * @return true if the string represents a valid digit, false otherwise
     */
    private boolean isValidDigit(String currentText) {
        try {
            int checkIfDigit = Integer.parseInt(currentText);
            return checkIfDigit >= 1 && checkIfDigit <= SIZE;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * The updateBoardColors method updates the text color and background color
     * of the TextField based on its position in the Sudoku board.
     *
     * @param row       the row index of the TextField
     * @param col       the column index of the TextField
     * @param textColor the color of the text
     */
    private void updateBoardColors(int row, int col, String textColor) {
        int textPosition[] = new int[POSITIONS];
        String setColor = "-fx-text-fill: " + textColor + "; -fx-border-color: white; ";
        textPosition = getStartBlock(row, col);
        if ((textPosition[0] + textPosition[1]) % ROW_PLUS_COL_IN_START_POSITION == 0) {
            setColor = setColor.concat("-fx-background-color: #e2e4e6;");
        }
        text[row][col].setStyle(setColor);
    }

    /**
     * The clearPressed method is called when the "Clear" button is pressed.
     * It clears all the TextField values and resets the board colors.
     *
     * @param event the action event triggered by the "Clear" button
     */
    @FXML
    void clearPressed(ActionEvent event) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                text[i][j].setEditable(true);
                updateBoardColors(i, j, "black");
                text[i][j].clear();
            }
        }
    }

    /**
     * The setPressed method is called when the "Set" button is pressed.
     * It sets the TextField values as fixed (non-editable) and updates
     * the board colors accordingly.
     *
     * @param event the action event triggered by the "Set" button
     */
    @FXML
    void setPressed(ActionEvent event) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isValidDigit(text[i][j].getText())) {
                    text[i][j].setEditable(false);
                    updateBoardColors(i, j, "red");
                } else {
                    text[i][j].clear();
                }
            }
        }
    }
}
