package com.example.jdbcdatenvisualisierung;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxml = new FXMLLoader(MainApp.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxml.load());
        stage.setTitle("World Languages");
        stage.setScene(scene);
        stage.setWidth(900);
        stage.setHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
