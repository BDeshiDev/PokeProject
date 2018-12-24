package com.company;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends  Application {
    @Override
    public void start(Stage primaryStage) {
        Pokemon poke1 =PokemonFactory.getBlastoise();
        Pokemon poke2  =PokemonFactory.getCharizard();

        pcTrainer ash = new pcTrainer("Ash",poke1);
        aiTrainer gary = new aiTrainer("Gary",poke2);

        BattleController battle = new BattleController(ash,gary);
        battle.begin(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


