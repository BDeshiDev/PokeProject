package com.company.Exploration;

import com.company.*;
import com.company.Pokemon.Pokemon;
import com.company.Utilities.Debug.Debugger;
import com.company.BattleResult;
import com.company.Utilities.TextHandler.LineStreamExecutable;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class PostBattleController {

    @FXML
    private Text DisplayText;
    @FXML
    private Button BackButton;

    Scene myScene;

    LineStreamExecutable lineSource  = new LineStreamExecutable();;
    boolean readyToExit = false;


    public void onExitPressed(){
        readyToExit = true;
    }

    public PostBattleController(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PostBattleScreen.fxml"));
        loader.setController(this);
        try {
            Parent root = loader.load();
            myScene = new Scene(root,Settings.windowWidth,Settings.windowLength);
            BackButton.setOnAction(event -> readyToExit = true);
        }catch (IOException ioe){
            System.out.println("post battle Screen load fail");
            System.exit(-1);
        }
    }

    public void begin(Stage primaryStage,BattleResult battleResult, pcTrainer player) {
        //continue here
        readyToExit = false;
        DisplayText.setText("");
        if(battleResult.hasRun)
            lineSource.push("Successfully ran from battle");
        else
            player.applyXp(battleResult,lineSource);//cowards don't get xp

        for (Pokemon p : battleResult.caughtMons) {
            lineSource.push(p.name + " was caught");
            if(player.isPartyFull()){
                lineSource.push("Party full. " + p.name + " was sent to storage...");
                PokemonStorage.addMon(p);
            }else {
                player.addMonToParty(p);
                lineSource.push(p.name + " was added to "+player.name+"'s party.");
            }
        }
        primaryStage.setScene(myScene);

        new AnimationTimer(){
            long now,prevNow;
            @Override
            public void start() {
                super.start();
                now = prevNow = System.nanoTime();
            }

            @Override
            public void handle(long time) {
                now = System.nanoTime();
                double delta =(now - prevNow)/1e9;
                prevNow = now;
                if(lineSource.isComplete()){
                    Debugger.out("Ready to exit post battle screen");
                    //readyToExit = true;
                    stop();
                }
                lineSource.continueExecution(delta,DisplayText);
            }
        }.start();
    }

    public boolean isComplete(){
        return  readyToExit;
    }
}
