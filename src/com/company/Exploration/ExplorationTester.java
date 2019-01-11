package com.company.Exploration;

import com.company.*;
import com.company.Utilities.BattleResult;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExplorationTester extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ExplorationScreen.fxml"));
        Parent root =loader.load();
        ExplorationController explorationController =loader.getController();

        aiTrainer merry= new aiTrainer("Merry", PokemonFactory.getBlastoise());
        aiTrainer sherry = new aiTrainer("Sherry",PokemonFactory.getPidgeot());

        List<aiTrainer> challengers = new ArrayList<>();
        Collections.addAll(challengers,merry,sherry);

        List<WildMon> possibleEncounters = new ArrayList<>();
        Collections.addAll(possibleEncounters, new WildMon(PokemonFactory.getBlastoise()), new WildMon(PokemonFactory.getPidgeot()));

        pcTrainer player = new pcTrainer("Ash",PokemonFactory.getVenasaur(),PokemonFactory.getCharizard(), PokemonFactory.getBlastoise() );

        explorationController.init(player,primaryStage,new Scene(root, Settings.windowWidth,Settings.windowLength),
                new LevelData("Vermillion Path",challengers,possibleEncounters),
                new LevelData("Cerulean Gym",challengers,possibleEncounters)
                );

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
