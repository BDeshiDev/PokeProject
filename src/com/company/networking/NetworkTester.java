package com.company.networking;

import com.company.PokeTab;
import com.company.SaveData;
import com.company.Settings;
import com.company.TitleController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class NetworkTester extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        NetworkController nc = new NetworkController(new TitleController());
        nc.begin(primaryStage, SaveData.newGameData(),null);
        primaryStage.setTitle("networkScreen");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
