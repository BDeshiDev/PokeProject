package com.company.Exploration;

import com.company.*;
import com.company.Pokemon.Moves.MoveFactory;
import com.company.Pokemon.PokemonData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExplorationTester extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ExplorationScreen.fxml"));
        Parent root =loader.load();
        ExplorationController explorationController =loader.getController();

        aiTrainer merry= new aiTrainer("Merry", PokemonFactory.getBlastoise().toPokemon());
        aiTrainer sherry = new aiTrainer("Sherry",PokemonFactory.getPidgeot().toPokemon());

        List<aiTrainer> challengers = new ArrayList<>();
        Collections.addAll(challengers,merry,sherry);

        List<PokemonData> possibleEncounters = new ArrayList<>();
        Collections.addAll(possibleEncounters, PokemonFactory.getBlastoise(),PokemonFactory.getPidgeot());

        pcTrainer player = new pcTrainer("Ash",PokemonFactory.getVenasaur().toPokemon(),PokemonFactory.getCharizard().toPokemon(),
                PokemonFactory.getBlastoise().toPokemon(),PokemonFactory.getCharmander().toPokemon(36) );

        player.addMoveToAllInParty(MoveFactory.getDebugKo());//add this to test easily

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
