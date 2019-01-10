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


    private Stack<aiTrainer> remainingChallengers = new Stack<>();
    private aiTrainer curChallenger;
    private BattleController curBattle = null;



    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ExplorationScreen.fxml"));
        Parent root =loader.load();
        ExplorationController explorationController =loader.getController();

        aiTrainer merry= new aiTrainer("merry", PokemonFactory.getBlastoise(),PokemonFactory.getCharizard());
        aiTrainer sherry = new aiTrainer("Sherry", PokemonFactory.getVenasaur());

        pcTrainer player = new pcTrainer("Ash",PokemonFactory.getVenasaur(),PokemonFactory.getBlastoise(),PokemonFactory.getCharizard());
        remainingChallengers.add(merry);
        remainingChallengers.add(sherry);

        explorationController.loadStage(new stageData("Vermillion Path",merry,sherry));

        new AnimationTimer(){
            @Override
            public void handle(long now) {
                if(curChallenger == null){
                    if(remainingChallengers.isEmpty()){
                        System.out.println("to be continued");
                        stop();
                    }else{
                        curChallenger = remainingChallengers.pop();
                        curBattle = new BattleController(player,curChallenger);
                        curBattle.begin(primaryStage);
                    }
                }else{
                    if(curBattle.isOver()){
                        curChallenger = null;
                    }
                }
            }
        }.start();

        primaryStage.setScene(new Scene(root, Settings.windowWidth,Settings.windowLength));
        primaryStage.setTitle("Exploration Test");
        primaryStage.show();
    }

    public void startNextBattle(){

    }

    public void onBattleEnd(){

    }

    public static void main(String[] args) {
        launch(args);
    }
}
