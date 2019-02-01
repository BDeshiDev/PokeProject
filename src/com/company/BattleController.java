package com.company;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import com.company.Pokemon.Pokemon;
import com.company.Utilities.Animation.AnimationFactory;
import com.company.Utilities.Animation.Tester.AnimationTester;
import com.company.Utilities.Debug.Debugger;
import com.company.Utilities.TextHandler.LineStream;
import com.company.networking.NetworkedEnemy;
import com.company.networking.NetworkedPlayer;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
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
    private ProgressBar playerHpBar;

    @FXML
    private Button playerFightButton;

    @FXML
    private Label enemyHpLabel;

    @FXML
    private ProgressBar enemyHpBar;

    @FXML
    private Button catchButton;

    @FXML
    private ImageView enemySideAnimationView;

    @FXML
    private ImageView playerImageView;

    @FXML
    private HBox playerFIghtBox;

    @FXML
    private Pane dialogBox;

    @FXML
    private Label playerNameLabel;

    @FXML
    private ImageView enemyImageView;

    @FXML
    private Label playerHpLabel;

    @FXML
    private Label playerLvLabel;

    @FXML
    private Button RunButton;

    @FXML
    private Button swapCancelButton;

    @FXML
    private Button pokemonSwapButton;

    @FXML
    private Text DialogText;

    @FXML
    private FlowPane PartySwapPane;

    @FXML
    private Label enemyLvLabel;

    @FXML
    private GridPane playerMoveGrid;

    @FXML
    private ImageView playerSideAnimationView;

    private BattleUIHolder playerUI;
    private BattleUIHolder enemyUI;
    private BattleResult result = new BattleResult();
    private  Scene battleScene;
    private Stage curStage;
    private SaveData curSave;

    MovesListUI movesUI;

    public Scene getBattleScene() {
        return battleScene;
    }

    public class SwapUI{
        FlowPane pane;
        int buttonWidth = 300;
        int buttonHeight = 100;
        public SwapUI(FlowPane pane) {
            this.pane = pane;
        }

        public void clear(){
            pane.getChildren().clear();
        }

        public  void addPokemon(pcTrainer player,Pokemon pokeToAdd,int pokeIndex){
            Button b = ButtonFactory.getSwapButton(buttonWidth,buttonHeight,pokeToAdd);
            b.setOnAction(event -> player.tryToSwap(pokeIndex));
            pane.getChildren().add(b);
        }

        public void addParty(pcTrainer player,List<Pokemon> party){
            swapUI.clear();
            for(int i = 0 ; i< party.size();i++){
                swapUI.addPokemon(player,party.get(i),i);
            }
            swapUI.toggle(false);
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
    boolean hasRun = false;
    private boolean isComplete =false;
    PokeScreen prevScreen = null;
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
                if(canRun) {
                    System.out.println("running");
                    hasRun = true;
                }
                else
                    System.out.println("can't run from this battle");
            });
            catchButton.setOnAction(event -> {
                if(canUseItems) {
                    player.setCommand(new CatchCommand(player,result));
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
        PartySwapPane.setVisible(isSwapEnabled);
        PartySwapPane.setDisable(!isSwapEnabled);
        swapCancelButton.setVisible(isSwapEnabled);
        swapCancelButton.setDisable(!isSwapEnabled);
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
            //delta time calc
            timeNow = System.nanoTime();
            double delta = (timeNow - timePrev) / 1e9;
            timePrev = timeNow;

            if(hasRun)
                stop();
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
                            t.onCommandAccepted();
                            if(newCommand != null)
                                CommandList.add(newCommand);
                            waitList.remove(t) ;
                        }
                    }
                    if(waitList.isEmpty()) {
                        dialogBox.setVisible(true);
                        dialogBox.setDisable(false);
                        playerMoveGrid.setDisable(true);
                        curState = BattleState.executing;//everyone gave commands so execute()
                    }
                    break;
                case executing:
                    executeAttacks(delta);
                    break;
                case endingTurn:
                    if (isOver())
                        curState = BattleState.finishing;//if no one can fight then don't bother handling turn end
                    else {
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
            if(hasRun) {
                System.out.println("ran from battle");
                result.hasRun = true;
            }
            else if (!player.canFight() && !enemy.canFight())
                System.out.println("result: Draw");
            else if (!enemy.canFight()) {
                System.out.println("Result: " + player.name + " wins");
                result.playerWon = true;
                result.totalXp += enemy.getDefeatXp();
            }
            else
                System.out.println("Result: " + enemy.getName() + " wins");
            if(prevScreen == null) {
                System.out.println("prev screen is null... exiting battle screen");
                System.exit(-555);
            }
            else
                prevScreen.begin(curStage,curSave,null);
            isComplete = true;
        }
    };

    BattleLoop battleLoop;

    public void begin(Stage curStage,pcTrainer pcTrainer,aiTrainer enemy,PokeScreen prevScreen,SaveData curSave) {
        this.prevScreen = prevScreen;
        this.curSave = curSave;
        beginPrep(curStage,pcTrainer,enemy);
        canRun = canUseItems = false;
    }
    public void begin(Stage curStage, NetworkedPlayer pcTrainer, NetworkedEnemy enemy) {
        beginPrep(curStage,pcTrainer,enemy);
        canRun = canUseItems = false;
    }


    public void begin(Stage curStage,pcTrainer pcTrainer,WildMon enemy,PokeScreen prevScreen,SaveData curSave){
        this.prevScreen = prevScreen;
        this.curSave = curSave;
        beginPrep(curStage,pcTrainer,enemy);
        canRun = canUseItems = true;
        RunButton.setDisable(false);
        catchButton.setDisable(false);
    }

    private void beginPrep(Stage curStage,pcTrainer pcTrainer,Battler enemy){
        this.player = pcTrainer;
        this.enemy = enemy;
        this.curStage = curStage;

        hasRun = false;

        swapUI.toggle(false);
        dialogBox.setVisible(false);
        dialogBox.setDisable(true);
        playerMoveGrid.setDisable(false);

        pcTrainer.prepareForBattle(playerSlot,enemySlot);
        enemy.prepareForBattle(enemySlot,playerSlot);

        pcTrainer.setMovesListUI(movesUI);
        pcTrainer.setSwapUI(swapUI);
        pcTrainer.setActionControlPane(playerFIghtBox);

        pcTrainer.prepTurn();
        enemy.prepTurn();


        result.reset();
        DialogText.setText("");
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


