package com.company.Exploration;

import com.company.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

public class ExplorationTester extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ExplorationScreen.fxml"));
        Parent root =loader.load();
        ExplorationController explorationController =loader.getController();

        aiTrainer merry= new aiTrainer("Merry", PokemonFactory.getBlastoise(),PokemonFactory.getCharizard());
        aiTrainer sherry = new aiTrainer("Sherry", PokemonFactory.getVenasaur());

        pcTrainer player = new pcTrainer("Ash",PokemonFactory.getVenasaur(),PokemonFactory.getBlastoise(),PokemonFactory.getCharizard());
        explorationController.init(player,new StageData("Vermillion Path",merry,sherry),primaryStage);

        primaryStage.setScene(new Scene(root, Settings.windowWidth,Settings.windowLength));
        primaryStage.setTitle("Exploration Test");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
