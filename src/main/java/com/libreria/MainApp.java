package com.libreria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/libreria/login.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(
                getClass().getResource("/com/libreria/styles.css").toExternalForm());
        primaryStage.setTitle("Librería Musical – Login");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
