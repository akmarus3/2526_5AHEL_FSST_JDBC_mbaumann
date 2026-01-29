package com.example.jdbcdatenvisualisierung;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


// TODO: In der Überschrift des Diagramms sollen die Ländernamen erscheinen
// TODO: Tortenstück der Sprache mit dem kleinsten Anteil - herausziehen
//   immer die Quellen zum Sourcecode angeben - keine KI Lösungen, wenn KI verwendet, dann
//   mit einer anderen Quelle belegen
// TODO: Auswahl des Kontinents ermöglichen - nur Länder dieses Kontinents anzeigen
//    Auswahlmöglichkeit z.B. über CheckBoxen in der Liste nur die Länder der ausgewählten Kontinente anzeigen
public class WorldLanuagesApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WorldLanuagesApp.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}