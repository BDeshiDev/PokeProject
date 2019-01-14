package com.company;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

import com.company.Pokemon.Pokemon;
import com.company.Utilities.Debug.Debugger;
import com.company.Utilities.TextHandler.LineStream;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;


public class BattleController {

    @FXML
    private Label enemyNameLabel;
    @FXML
    private ProgressBar enemyHpBar;
    @FXML
    private Label enemyHpLabel;
    @FXML
    private Label enemyLvLabel;
    @FXML
    private Rectangle enemyTargetIndicator;
    @FXML
    private Label playerNameLabel;
    @FXML
    private ProgressBar playerHpBar;
    @FXML
    private Label playerHpLabel;
    @FXML
    private Label playerLvLabel;
    @FXML
    private Rectangle playerTargetIndicator;
    @FXML
    private ImageView enemyImageView;
    @FXML
    private ImageView playerImageView;
    @FXML
    private GridPane playerMoveGrid;
    @FXML
    private Button playerFightButton;
    @FXML
    private Button pokemonSwapButton;
    @FXML
    private FlowPane PartySwapPane;
    @FXML
    private Button swapCancelButton;
    @FXML
    private Pane dialogBox;
    @FXML
    private Text DialogText;
    @FXML
    private ImageView enemySideAnimationView;
    @FXML
    private ImageView playerSideAnimationView;
    @FXML
    private Button RunButton;
    @FXML
    private Button catchButton;

    private BattleUIHolder playerUI;
    private BattleUIHolder enemyUI;
    private BattleResult result = new BattleResult();
    private  Scene battleScene;
    private Stage curStage;

    MovesListUI movesUI;

    public class SwapUI{
        FlowPane pane;
        int buttonWidth = 300;
        int buttonheight = 100;
        public SwapUI(FlowPane pane) {
            this.pane = pane;
        }

        public void clear(){
            pane.getChildren().clear();
        }

        public  void addPokemon(pcTrainer player, Pokemon pokeToAdd){
            Button b = new Button(pokeToAdd.name);
            b.setPrefWidth(buttonWidth);
            b.setPrefHeight(buttonheight);
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    player.tryToSwap(pokeToAdd);
                }
            });
            pane.getChildren().add(b);
        }

        public void toggle(boolean shouldBeOn){
            toggleSwapMenu(shouldBeOn);
        }
    }
    SwapUI swapUI;

    pcTrainer player;
    Battler enemy;
    BattleSlot playerSlot;
    BattleSlot enemySlot;

    boolean canRun = true;
    boolean canUseItems = true;
    private boolean isComplete =false;
    Scene prevScene;
    Parent newRoot;

    public BattleController(){
        setFxml();
        battleScene = new Scene(newRoot, Settings.windowWidth, Settings.windowLength);
    }

    private void setFxml(){

        FXMLLoader loader = new FXMLLoader(getClass().getResource("BattleFXML.fxml"));
        loader.setController(this);
        try {
            newRoot = loader.load();
            enemyUI = new BattleUIHolder(enemyNameLabel,enemyHpBar,enemyHpLabel,enemyLvLabel,enemyImageView,true);
            playerUI = new BattleUIHolder(playerNameLabel,playerHpBar,playerHpLabel,playerLvLabel,playerImageView,false);

            playerSlot = new BattleSlot(playerUI,playerSideAnimationView);
            enemySlot = new BattleSlot(enemyUI,enemySideAnimationView);

            swapUI = new SwapUI(PartySwapPane);
            movesUI = new MovesListUI(playerMoveGrid);

            pokemonSwapButton.setOnAction(event -> {
                toggleSwapMenu(true);
            });
            swapCancelButton.setOnAction(event -> {
                if(player.canCancelSwap())
                    toggleSwapMenu(false);
                else
                    System.out.println("You must swap");
            }
            );

            RunButton.setOnAction(event -> {
                if(canRun)
                    System.out.println("running");
                else
                    System.out.println("can't run from this battle");
            });
            catchButton.setOnAction(event -> {
                if(canRun) {
                    if(player.hasPokeBalls()) {
                        System.out.println("catching");
                        player.skipTurn();
                        Pokemon catchResult = enemySlot.tryCatch();
                        if(catchResult != null)
                            result.addCaughtMon(catchResult);
                    }else
                        Debugger.out("insufficent pokeBalls");
                }
                else
                    System.out.println("can't catch in this battle");
            });

        }catch (IOException ioe){
            System.out.println("battlefxml load fail");
            System.exit(-1);
        }
    }

    public boolean isOver(){
        return !player.canFight() || !enemy.canFight();
    }
    public boolean isComplete(){
        return isComplete;
    }

    public void toggleSwapMenu(boolean isSwapEnabled){
        if(isSwapEnabled){
            PartySwapPane.setVisible(true);
            PartySwapPane.setDisable(false);
            swapCancelButton.setVisible(true);
            swapCancelButton.setDisable(false);
        }else{
            PartySwapPane.setVisible(false);
            PartySwapPane.setDisable(true);
            swapCancelButton.setVisible(false);
            swapCancelButton.setDisable(true);
        }
    }

    class  BattleLoop extends  AnimationTimer{
        PriorityQueue<BattleCommand> CommandList = new PriorityQueue<>();
        ArrayList<Battler> waitList = new ArrayList<>();;
        ArrayList<Battler> trainers = new ArrayList<>();;
        BattleCommand curExecutingCommand = null;
        LineStream linesSource = new LineStream();;

        Stack<BattleExecutable> CommandsAtTurnEnd = new Stack<>();
        BattleExecutable curTurnEndCommand =null;

        BattleState curState;

        double timePrev;
        double timeNow;

        BattleLoop(Trainer player , Battler enemy){
            trainers.add(player);
            trainers.add(enemy);
        }

        @Override
        public void start() {
            super.start();
            curState = BattleState.turnPreparing;
            timeNow = timePrev = System.nanoTime();
            refreshWaitList();
        }

        void prepareTurns(){
            curExecutingCommand = null;
            curTurnEndCommand = null;
            for (Battler t:trainers) {
                t.prepTurn();
            }
        }

        void refreshWaitList() {
            waitList.clear();
            waitList.addAll(trainers);
        }

        void executeAttacks(double delta) {
            dialogBox.setVisible(true);
            dialogBox.setDisable(false);
            playerMoveGrid.setDisable(true);

            if(curExecutingCommand == null){
                if(CommandList.isEmpty()) {
                    if(isOver()){
                        curState = BattleState.finishing;
                        return;
                    }
                    else {
                        for (Battler t : trainers) {
                            t.endTurnPrep();
                            waitList.add(t);
                        }
                        CommandsAtTurnEnd.clear();
                        curState = BattleState.endingTurn;
                        return;
                    }
                }
                else{
                    // System.out.println("getting next attack");
                    curExecutingCommand = CommandList.poll();
                    curExecutingCommand.start();
                }
            }
            curExecutingCommand.continueExecution(delta,DialogText);
            if(curExecutingCommand.isComplete()){
                curExecutingCommand.end();
                //System.out.println("nulling attack" + curExecutingCommand);
                curExecutingCommand = null;
            }
        }

        @Override
        public void handle(long now) {//essentially a infinite loop
            //deltatime calc
            timeNow = System.nanoTime();
            double delta = (timeNow - timePrev) / 1e9;
            timePrev = timeNow;
            //actual loop
            switch (curState){
                case turnPreparing:
                    prepareTurns();
                    curState = BattleState.waiting;
                    break;
                case waiting:
                    for(int i = waitList.size() -1; i >= 0 ;i--){
                        Battler t = waitList.get(i);//get commands until all trainers have given commands
                        if(t.hasFinalizedCommands()){
                            BattleCommand newCommand = t.getCommand();
                            if(newCommand != null)
                                CommandList.add(newCommand);
                            waitList.remove(t) ;
                        }
                    }
                    if(waitList.isEmpty()) {
                        curState = BattleState.executing;//everyone hgave commands so execute()
                    }
                    break;
                case executing:
                    executeAttacks(delta);
                    break;
                case endingTurn:
                    if (isOver())
                        curState = BattleState.finishing;//if no one can fight then don't bother handling turn end
                    else {
                        //System.out.println("trying to end turn");
                        for (int i = waitList.size() - 1; i >= 0; i--) {//ask for turnEndCommands and add them to the stack
                            Battler t = waitList.get(i);
                            if (t.canEndTurn()) {
                                waitList.remove(t);
                                System.out.println("removing " + t.getName());
                            } else if (t.hasCommandBeforeTurnEnd()) {
                                BattleCommand c= t.getCommandToExecuteBeforeTurnEnd();
                                CommandsAtTurnEnd.add(t.getCommandToExecuteBeforeTurnEnd());
                                waitList.remove(t);
                            }
                        }
                        if (waitList.isEmpty()) {
                            if(curTurnEndCommand == null){
                                if(CommandsAtTurnEnd.isEmpty()) {
                                    System.out.println("ending turn");
                                    refreshWaitList();
                                    CommandList.clear();
                                    dialogBox.setVisible(false);
                                    dialogBox.setDisable(true);
                                    DialogText.setText("");
                                    playerMoveGrid.setDisable(false);
                                    curState = BattleState.turnPreparing;
                                    return;
                                }
                                else{
                                    curTurnEndCommand = CommandsAtTurnEnd.pop();
                                    Debugger.out("getting another turn end command" );
                                    curTurnEndCommand.start();
                                }
                            }
                            curTurnEndCommand.continueExecution(delta,DialogText);
                            if(curTurnEndCommand.isComplete()){
                                curTurnEndCommand.end();
                                curTurnEndCommand = null;
                            }
                        }
                    }
                    break;
                case finishing:
                    stop();
                    break;
            }
        }
        @Override
        public void stop(){
            super.stop();
            for (Battler t: trainers) {
                t.endBattle();
            }
            //calc results
            if (!player.canFight() && !enemy.canFight())
                System.out.println("result: Draw");
            else if (!enemy.canFight()) {
                System.out.println("Result: " + player.name + " wins");
                result.playerWon = true;
                result.totalXp += enemy.getDefeatXp();
            }
            else
                System.out.println("Result: " + enemy.getName() + " wins");
            curStage.setScene(prevScene);
            isComplete = true;
        }
    };

    BattleLoop battleLoop;

    public void begin(Stage curStage,pcTrainer pcTrainer,aiTrainer enemy) {
        beginPrep(curStage,pcTrainer,enemy);
        canRun = canUseItems = false;
    }


    public void begin(Stage curStage,pcTrainer pcTrainer,WildMon enemy){
        beginPrep(curStage,pcTrainer,enemy);
        canRun = canUseItems = true;
        RunButton.setDisable(false);
        catchButton.setDisable(false);
    }

    private void beginPrep(Stage curStage,pcTrainer pcTrainer,Battler enemy){
        this.player = pcTrainer;
        this.enemy = enemy;
        this.curStage = curStage;

        swapUI.toggle(false);
        dialogBox.setVisible(false);
        dialogBox.setDisable(true);
        playerMoveGrid.setDisable(false);
        pcTrainer.prepTurn();
        enemy.prepTurn();

        pcTrainer.prepareForBattle(playerSlot,enemySlot);
        enemy.prepareForBattle(enemySlot,playerSlot);


        pcTrainer.setMovesListUI(movesUI);
        pcTrainer.updateMoveUI();
        pcTrainer.setSwapUI(swapUI);
        pcTrainer.updateSwapUI();

        result.reset();
        DialogText.setText("");
        prevScene = curStage.getScene();
        isComplete = false;
        curStage.setScene(battleScene);

        System.out.println(pcTrainer.getName() + "  VS  " + enemy.getName() + "!!!");//#unimplimented show this in battle transition animation
        battleLoop = new BattleLoop(player,enemy);
        battleLoop.start();
    }

    public BattleResult getResult(){
        return  result;
    }
}

enum BattleState{
    waiting,executing,finishing,endingTurn,turnPreparing
}


