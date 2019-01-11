package com.company.Exploration;

import com.company.*;
import com.company.Utilities.BattleResult;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
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
    private BattleController curBattle;
    private PostBattleController xpScreenController;
    private Stack<LevelData> levelsLeft =new Stack<>();

    private pcTrainer player;
    private Stage primaryStage;
    private Scene myScene;

    private boolean wantsToBattle;
    private boolean wantsToSeeStatus;
    private boolean wantsToGoToNextLevel;

    ExplorationState explorationState;


    public void loadLevel(LevelData stageToLoad){
        remainingChallengers.clear();
        remainingChallengers.addAll(stageToLoad.challengers);
        updateChallengerText();
        StageTitleLabel.setText(stageToLoad.stageName);;
    }

    public void loadLevelFromStack(){
        if(levelsLeft.isEmpty()){
            onExplorationComplete();
        }
        else {
            nextStageButton.setDisable(true);
            fightButton.setDisable(false);
            LevelData nextStageData = levelsLeft.pop();
            loadLevel(nextStageData);
        }
    }

    public void onExplorationComplete(){
        System.out.println("No stages left to load");
        mainLoop.stop();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreditsScene.fxml"));
            Parent root = loader.load();
            CreditsController cc= loader.getController();
            cc.init(primaryStage);
            primaryStage.setScene(new Scene(root, Settings.windowWidth, Settings.windowLength));
            primaryStage.setTitle("Ending Screen");
            primaryStage.show();
        }catch(IOException ioe){
            System.out.println("failed to load credit screen");
            System.exit(-1);//#TODO move to a creditScreen class
        }
    }

    class  ExplorationLoop extends  AnimationTimer{
        @Override
        public void handle(long now) {

            switch (explorationState){
                case Exploring:
                    if(wantsToBattle){
                        wantsToBattle = false;
                        explorationState = ExplorationState.WaitingForBattleEnd;
                        getNextChallenger();
                    } else if(wantsToGoToNextLevel){
                        wantsToGoToNextLevel = false;
                        loadLevelFromStack();
                    }
                    break;
                case WaitingForBattleEnd:
                    if( curBattle.isComplete()){
                        BattleResult newResult = curBattle.getResult();
                        if(newResult.playerWon) {
                            System.out.println("player won");
                        }
                        else{
                            remainingChallengers.push(curChallenger);//if we don't win we fight the same trainer again
                            System.out.println("player lost");
                        }
                        System.out.println(newResult);
                        explorationState = ExplorationState.WaitingForExpScreen;
                        System.out.println("entering post battle screen");
                        xpScreenController.begin(primaryStage,newResult,player);
                    }
                    break;
                case WaitingForExpScreen:
                    if(xpScreenController.readyToExit){
                        curChallenger.heal();
                        updateChallengerText();
                        player.heal();
                        if(remainingChallengers.isEmpty()) {
                            nextStageButton.setDisable(false);
                            fightButton.setDisable(true);
                        }

                        primaryStage.setScene(myScene);
                        primaryStage.setTitle("Exploration Test");
                        explorationState = ExplorationState.Exploring;
                    }
                    break;
            }
            /*
            if(curChallenger == null){
                if(remainingChallengers.isEmpty()){
                    System.out.println("to be continued...");
                    if(wantsToGoToNextLevel) {
                        wantsToGoToNextLevel = false;
                        loadLevelFromStack();
                    }
                }else{
                    if(wantsToBattle) {
                        wantsToBattle = false;
                        getNextChallenger();
                    }
                }
            }else{
                    if( curBattle.isComplete()){
                        curChallenger.heal();
                        player.heal();
                        BattleResult newResult = curBattle.getResult();
                        if(newResult.playerWon) {
                            System.out.println("player won");

                        }
                        else {
                            remainingChallengers.push(curChallenger);//if we don't win we fight the same trainer again
                            System.out.println("player lost");
                        }
                        curChallenger.heal();
                        curChallenger = null;
                        updateChallengerText();
                        System.out.println(newResult);
                        if(remainingChallengers.isEmpty())
                            nextStageButton.setDisable(false);
                    }
            }*/
        }

        @Override
        public void stop() {
            super.stop();
            System.out.println("exiting exploration loop");
        }
    }
    ExplorationLoop mainLoop = new ExplorationLoop();


    public void init(pcTrainer player,Stage primaryStage,Scene sceneToUse, LevelData... stagesToLoad){
        curBattle = new BattleController();
        xpScreenController = new PostBattleController();
        explorationState = ExplorationState.Exploring;

        this.player = player;
        this.primaryStage = primaryStage;
        this.myScene = sceneToUse;

        primaryStage.setScene(myScene);
        primaryStage.setTitle("Exploration Test");

        Collections.addAll(levelsLeft,stagesToLoad);
        loadLevelFromStack();
        nextStageButton.setDisable(true);

        mainLoop.start();
    }

    public void getNextChallenger(){
        System.out.println("getting next challenger...");
        curChallenger = remainingChallengers.pop();
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

    enum  ExplorationState{
        Exploring,WaitingForBattleEnd,WaitingForExpScreen,waitingForStatusScreen,
    }
}

