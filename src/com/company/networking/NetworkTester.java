package com.company.networking;

import com.company.PokeTab;
import com.company.Settings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class NetworkTester extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("NetworkScreen.fxml"));
        Scene scene=new Scene(loader.load(), Settings.windowWidth,Settings.windowLength);
        NetworkController nc = loader.getController();
        nc.setPrimaryStage(primaryStage);
        primaryStage.setTitle("networkTest");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
