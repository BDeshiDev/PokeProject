package com.company;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends  Application {
    @Override
    public void start(Stage primaryStage) throws  Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartScreen.fxml"));
        Parent root =loader.load();
        TitleController titleController =loader.getController();
        titleController.setCurStage(primaryStage);

        primaryStage.setScene(new Scene(root,Settings.windowWidth,Settings.windowLength));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


