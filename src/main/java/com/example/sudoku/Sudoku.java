/**
 * The Sudoku class is the main entry point for the Sudoku application.
 * It extends the JavaFX Application class and provides the start method
 * to initialize and display the Sudoku game.
 */
package com.example.sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Sudoku extends Application {

    /**
     * The start method is called when the JavaFX application is started.
     * It loads the Sudoku.fxml file, creates a scene with the loaded
     * FXML content, sets the stage title and scene, and displays the
     * Sudoku game UI.
     *
     * @param stage the primary stage for the application
     * @throws IOException if an error occurs while loading the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Sudoku.class.getResource("Sudoku.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 420, 420);
        stage.setTitle("Sudoku");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method is the entry point for the Sudoku application.
     * It launches the JavaFX application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
