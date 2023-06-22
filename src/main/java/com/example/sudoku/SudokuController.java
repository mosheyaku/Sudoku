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
    private final int ROW_PLUS_COL_IN_START_POSITION = 6;


    @FXML
    private GridPane grid;

    private TextField text[][];

    public void initialize() {
        text = new TextField[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                final int currentRow = i;
                final int currentCol = j;
                text[i][j] = new TextField();

                String setColor = "-fx-text-fill: black; -fx-border-color: white; ";
                int blockRow = i - (i % BLOCK_SIZE);
                int blockCol = j - (j % BLOCK_SIZE);
                int textPosition[] = {blockRow, blockCol};
                if ((textPosition[0] + textPosition[1]) % ROW_PLUS_COL_IN_START_POSITION == 0)
                    setColor = setColor.concat("-fx-background-color: #e2e4e6;");

                text[i][j].setStyle(setColor);

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

    private void handleTextField(ActionEvent event, int row, int col) {
        TextField currentTextField = (TextField) event.getSource();
        String errorMessage = "The digit already exist in the current ";
        boolean isValidInput = false;
        if (!isValidDigit(currentTextField.getText())) {
            errorMessage = "Please type a number between 1 and 9.";

        } else if (isInBlock(row, col, currentTextField.getText())) {
            errorMessage = errorMessage.concat("block.");

        } else if (isInRow(currentTextField, row, col)) {
            errorMessage = errorMessage.concat("row.");

        } else if (isInCol(currentTextField, row, col)) {
            errorMessage = errorMessage.concat("column.");
        } else
            isValidInput = true;

        if (!isValidInput) {
            currentTextField.clear();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input:");
            alert.setContentText(errorMessage);
            Optional<ButtonType> option = alert.showAndWait();
        }
    }

    private boolean isInRow(TextField currentTextField, int row, int col) {
        for (int k = 0; k < SIZE; k++)
            if (text[k][col].getText().equals(currentTextField.getText()) && k != row)
                return true;
        return false;
    }

    private boolean isInCol(TextField currentTextField, int row, int col) {
        for (int k = 0; k < SIZE; k++)
            if (text[row][k].getText().equals(currentTextField.getText()) && k != col)
                return true;
        return false;
    }

    private boolean isInBlock(int row, int col, String digit) {
        int blockRow = row - (row % BLOCK_SIZE);
        int blockCol = col - (col % BLOCK_SIZE);
        int position[] = {blockRow, blockCol};

        for (int i = position[0]; i < position[0] + BLOCK_SIZE; i++)
            for (int j = position[1]; j < position[1] + BLOCK_SIZE; j++)
                if (text[i][j].getText().equals(digit) && i != row && j != col)
                    return true;
        return false;
    }


    private boolean isValidDigit(String currentText) {
        try {
            int checkIfDigit = Integer.parseInt(currentText);
            return checkIfDigit >= 1 && checkIfDigit <= SIZE;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    @FXML
    void clearPressed(ActionEvent event) {

    }

    @FXML
    void setPressed(ActionEvent event) {

    }
}
