package com.company.networking;

import com.company.SaveData;
import com.company.TitleController;
import javafx.application.Application;
import javafx.stage.Stage;

public class NetworkTester extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        turnedNetWorkController nc = new turnedNetWorkController(new TitleController());
        nc.begin(primaryStage, SaveData.newGameData(),null);
        primaryStage.setTitle("networkScreen");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
