package com.company.Exploration;

import com.company.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Stack;


public class ExplorationController {
    @FXML
    private Button StatusButton;

    @FXML
    private Button fightButton;

    @FXML
    private Label RemainingTrainerCountLabel;

    @FXML
    private Label NextChallengerNameLabel;

    @FXML
    private Label StageTitleLabel;

    @FXML
    private Button nextStageButton;

    private Stack<aiTrainer> remainingChallengers = new Stack<>();
    private aiTrainer curChallenger;
    private BattleController curBattle = null;

    private pcTrainer player;
    private Stage primaryStage;

    private boolean wantsToBattle;
    private boolean wantsToSeeStatus;
    private boolean wantsToGoToNextStage;


    public void loadStage(StageData stageToLoad){
        remainingChallengers.clear();
        remainingChallengers.addAll(stageToLoad.challengers);
        RemainingTrainerCountLabel.setText(Integer.toString(stageToLoad.challengers.size()));
        StageTitleLabel.setText(stageToLoad.stageName);
        NextChallengerNameLabel.setText(remainingChallengers.peek().name);
    }

    public void init(pcTrainer player, StageData stageToLoad,Stage primaryStage){
        this.player = player;
        this.primaryStage = primaryStage;
        loadStage(stageToLoad);
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                if(curChallenger == null){
                    if(remainingChallengers.isEmpty()){
                        System.out.println("to be continued...");
                        if(wantsToGoToNextStage) {
                            wantsToGoToNextStage = false;
                            System.out.println("somewhere else...");
                            stop();
                        }
                    }else{
                        if(wantsToBattle) {
                            wantsToBattle = false;
                            curChallenger = remainingChallengers.pop();
                            curBattle = new BattleController(player, curChallenger);
                            curBattle.begin(primaryStage);
                        }
                    }
                }else{
                    if(curBattle == null ||  curBattle.isComplete()){
                        if( curBattle.isComplete()){
                            if(curBattle.getResult().playerWon)
                                curChallenger = null;
                            else
                                curChallenger.heal();

                            player.heal();
                        }
                        curChallenger = null;
                    }
                }
            }
        }.start();
    }


    public void startNextBattle(){
        wantsToBattle =true;
    }
    public void goToNextStage(){
        wantsToGoToNextStage = true;
    }

    public void goToStatus(){
        wantsToSeeStatus = true;
        System.out.println("status not ready yet");
    }
}

