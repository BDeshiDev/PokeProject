package com.company.Exploration;

import com.company.Settings;
import com.company.TitleController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExplorationTester extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ExplorationScreen.fxml"));
        Parent root =loader.load();
        ExplorationController explorationController =loader.getController();
        explorationController.loadStage(new stageData("Vermillion Path"));

        primaryStage.setScene(new Scene(root, Settings.windowWidth,Settings.windowLength));
        primaryStage.setTitle("Exploration Test");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
