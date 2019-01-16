package com.company.Exploration;

import com.company.*;
import com.company.BattleResult;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;


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

    @FXML
    private Button wildMonButton;


    private List<WildMon> possibleEncounters = new ArrayList<>();//#TODO implement tomorrow
    private Stack<aiTrainer> remainingChallengers = new Stack<>();
    private aiTrainer curChallenger;
    private BattleController curBattle;
    private PostBattleController xpScreenController;
    private PokemonStorageController storageController;
    private Stack<LevelData> levelsLeft =new Stack<>();

    private pcTrainer player;
    private Stage primaryStage;
    private Scene myScene;

    private boolean wantsToBattle;
    private boolean wantsToSeeStatus;
    private boolean wantsToFightWildMon;
    private boolean wantsToGoToNextLevel;

    ExplorationState explorationState;


    public ExplorationController() {
        curBattle = new BattleController();
        xpScreenController = new PostBattleController();
    }

    public void loadLevel(LevelData stageToLoad){
        remainingChallengers.clear();
        remainingChallengers.addAll(stageToLoad.challengers);
        possibleEncounters.clear();
        possibleEncounters.addAll(stageToLoad.possibleEncounters);
        updateChallengerText();
        StageTitleLabel.setText(stageToLoad.stageName);
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

    public void fightWildMons(){
        wantsToFightWildMon = true;
    }

    public void startWildMonBattle(){
        System.out.println("getting next wild mon...");
        int monIndex = new Random().nextInt(possibleEncounters.size());
        Battler newWildmon =  possibleEncounters.get(monIndex);
        newWildmon.heal();
        curBattle.begin(primaryStage,player, possibleEncounters.get(monIndex));
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
                    }else if(wantsToFightWildMon){
                        wantsToFightWildMon = false;
                        explorationState = ExplorationState.WaitingForBattleEnd;
                        startWildMonBattle();
                    } else if(wantsToGoToNextLevel){
                        wantsToGoToNextLevel = false;
                        loadLevelFromStack();
                    }else if(wantsToSeeStatus){
                        wantsToSeeStatus= false;
                        explorationState = ExplorationState.waitingForStatusScreen;
                        loadStatusScreen();
                    }
                    break;
                case WaitingForBattleEnd:
                    if( curBattle.isComplete()){
                        BattleResult newResult = curBattle.getResult();
                        if(newResult.playerWon) {
                            System.out.println("player won");
                        }
                        else{
                            if(curChallenger != null)
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
                case waitingForStatusScreen:
                    if(storageController.readyToExit()){
                        primaryStage.setScene(myScene);
                        primaryStage.setTitle("Exploration Test");
                        explorationState = ExplorationState.Exploring;
                    }
                    break;
            }
        }

        @Override
        public void stop() {
            super.stop();
            System.out.println("exiting exploration loop");
        }
    }
    ExplorationLoop mainLoop = new ExplorationLoop();


    public void init(pcTrainer player,Stage primaryStage,Scene sceneToUse, LevelData... stagesToLoad){
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
        curChallenger.heal();
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
    }

    public void loadStatusScreen(){
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("PokemonStorageScreen.fxml"));
            Scene scene=new Scene(new Pane(), Settings.windowWidth,Settings.windowLength);
            scene.setRoot(loader.load());
            storageController=loader.getController();
            storageController.begin(primaryStage,player.getParty());

            primaryStage.setTitle("Storage");
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch(IOException ioe){
            ioe.printStackTrace();
            System.out.println("failed to load storage Screen");
            System.exit(-1);
        }
    }

    enum  ExplorationState{
        Exploring,WaitingForBattleEnd,WaitingForExpScreen,waitingForStatusScreen,
    }
}

