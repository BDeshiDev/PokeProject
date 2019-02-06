package com.company.Exploration;

import com.company.Exploration.PokemonStorage;
import com.company.Exploration.PokemonStorageController;
import com.company.Pokemon.Pokemon;
import com.company.PokemonFactory;
import com.company.Settings;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StorageTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("PokemonStorageScreen.fxml"));
        Scene scene=new Scene(new Pane(), Settings.windowWidth,Settings.windowLength);

        PokemonStorage.addMon(PokemonFactory.getBlastoise().toPokemon(),PokemonFactory.getCharizard().toPokemon());
        scene.setRoot(loader.load());
        PokemonStorageController controller=loader.getController();

        ArrayList<Pokemon> testParty = new ArrayList<>();
        testParty.add(PokemonFactory.getBlastoise().toPokemon());
        testParty.add(PokemonFactory.getCharizard().toPokemon());
        controller.begin(primaryStage,testParty);

        primaryStage.setTitle("StorageTest");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
