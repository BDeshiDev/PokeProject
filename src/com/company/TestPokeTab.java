package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TestPokeTab extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("PokeTab.fxml"));
        Scene scene=new Scene(new StackPane(),Settings.windowWidth,Settings.windowLength);
        scene.setRoot(loader.load());
        PokeTab controller=loader.getController();
        controller.init();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
