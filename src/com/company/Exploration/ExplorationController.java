package com.company.Exploration;

import com.company.*;
import com.company.Utilities.BattleResult;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    private List<WildMon> possibleEncounters = new ArrayList<>();//#TODO implement tomorrow
    private Stack<aiTrainer> remainingChallengers = new Stack<>();
    private aiTrainer curChallenger;
    private BattleController curBattle = null;
    private Stack<LevelData> levelsLeft =new Stack<>();

    private pcTrainer player;
    private Stage primaryStage;

    private boolean wantsToBattle;
    private boolean wantsToSeeStatus;
    private boolean wantsToGoToNextLevel;


    public void loadLevel(LevelData stageToLoad){
        remainingChallengers.clear();
        remainingChallengers.addAll(stageToLoad.challengers);
        updateChallengerText();
        StageTitleLabel.setText(stageToLoad.stageName);;
    }

    public void loadLevelFromStack(){
        if(levelsLeft.isEmpty()){
            System.out.println("No stages left to load");
            System.exit(-1);//#TODO transition to credits screen
        }

        nextStageButton.setDisable(true);
        LevelData nextStageData = levelsLeft.pop();
        loadLevel(nextStageData);
    }



    public void init(pcTrainer player,Stage primaryStage, LevelData... stagesToLoad){
        this.player = player;
        this.primaryStage = primaryStage;

        Collections.addAll(levelsLeft,stagesToLoad);
        loadLevelFromStack();

        nextStageButton.setDisable(true);

        new AnimationTimer(){
            @Override
            public void handle(long now) {
                if(curChallenger == null){
                    if(remainingChallengers.isEmpty()){
                        System.out.println("to be continued...");
                        if(wantsToGoToNextLevel) {
                            wantsToGoToNextLevel = false;
                            loadLevelFromStack();
                            stop();
                        }
                    }else{
                        if(wantsToBattle) {
                            wantsToBattle = false;
                            getNextChallenger();
                        }
                    }
                }else{
                    if(curBattle == null ||  curBattle.isComplete()){
                        if( curBattle.isComplete()){
                            BattleResult newResult = curBattle.getResult();
                            if(newResult.playerWon) {
                                System.out.println("player won");
                            }
                            else {
                                remainingChallengers.push(curChallenger);//if we don't win we fight the same trainer again
                                System.out.println("player lost");
                            }
                            curChallenger.heal();//hack fix for when we use the same trainer in multiple stages
                            curChallenger = null;
                            updateChallengerText();
                            player.heal();
                            System.out.println(newResult);

                            if(remainingChallengers.isEmpty())
                                nextStageButton.setDisable(false);

                        }
                    }
                }
            }
        }.start();
    }

    public void getNextChallenger(){
        System.out.println("getting next challenger...");
        curChallenger = remainingChallengers.pop();
        curBattle = new BattleController();
        curBattle.begin(primaryStage,player, curChallenger);
    }

    public void updateChallengerText(){
        RemainingTrainerCountLabel.setText(Integer.toString( remainingChallengers.size()));
        NextChallengerNameLabel.setText(remainingChallengers.isEmpty()? "None": remainingChallengers.peek().name);
    }

    public void startNextBattle(){
        wantsToBattle =true;
    }
    public void goToNextStage(){
        wantsToGoToNextLevel = true;
    }

    public void goToStatus(){
        wantsToSeeStatus = true;
        System.out.println("status not ready yet");
    }
}

