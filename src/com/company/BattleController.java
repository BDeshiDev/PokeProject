package com.company;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

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


class BattleController {

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

    private BattleUIHolder playerUI;
    private BattleUIHolder enemyUI;

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

        public  void addPokemon(pcTrainer player,Pokemon pokeToAdd){
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
    aiTrainer enemy;
    BattleSlot playerSlot;
    BattleSlot enemySlot;

    boolean canRun = true;
    boolean canUseItems = true;
    Scene prevScene;
    Parent newRoot;

    public BattleController(pcTrainer player, aiTrainer enemy){
        this.player = player;
        this.enemy = enemy;

        setFxml(player,enemy);
    }

    private void setFxml(pcTrainer player,aiTrainer enemy){

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


        }catch (IOException ioe){
            System.out.println("battlefxml load fail");
            System.exit(-1);
        }
    }

    public boolean isOver(){
        return !player.canFight() || !enemy.canFight();
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

    public void begin(Stage curStage) {
        prevScene = curStage.getScene();
        curStage.setScene(new Scene(newRoot, Settings.windowWidth, Settings.windowLength));

        System.out.println(player.name + "  VS  " + enemy.name + "!!!");//#unimplimented show this in battle transition animation

        AnimationTimer battleLoop = new AnimationTimer() {
            PriorityQueue<Attack> attacksList;
            ArrayList<Trainer> waitList;
            ArrayList<Trainer> trainers;
            Attack curExecutingAttack = null;
            LineStream linesSource;

            BattleState curState;

            double timePrev;
            double timeNow;

            @Override
            public void start() {
                super.start();
                curState = BattleState.turnPreparing;
                timeNow = timePrev = System.nanoTime();

                attacksList = new PriorityQueue<>();
                waitList = new ArrayList<>();
                trainers = new ArrayList<>();
                linesSource = new LineStream();

                trainers.add(player);
                trainers.add(enemy);

                player.prepareForBattle(playerSlot,enemySlot);
                enemy.prepareForBattle(enemySlot,playerSlot);
                prepareTurns();

                player.setMovesListUI(movesUI);
                player.updateMoveUI();
                player.setSwapUI(swapUI);
                player.updateSwapUI();
                refreshWaitList();
            }

            void prepareTurns(){
                for (Trainer t:trainers) {
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

                if(curExecutingAttack == null || curExecutingAttack.isExecutionComplete()){
                    if(attacksList.isEmpty()) {
                        curState = BattleState.endingTurn;
                        return;
                    }
                    else{
                            curExecutingAttack = attacksList.poll();
                            curExecutingAttack.startExectution();
                    }
                }
                curExecutingAttack.continueExecution(delta,DialogText);
            }

            @Override
            public void handle(long now) {//essentially a infinite loop
                //deltatime calc
                timeNow = System.nanoTime();
                double delta = (timeNow - timePrev) / 1e9;
                timePrev = timeNow;

                switch (curState){
                    case turnPreparing:
                        prepareTurns();
                        curState = BattleState.waiting;
                        break;
                    case waiting:
                        for(int i = waitList.size() -1; i >= 0 ;i--){
                            Trainer t = waitList.get(i);
                            if(t.hasFinalizedCommands()){
                                attacksList.addAll(t.getCommands());
                                waitList.remove(t) ;
                            }
                        }
                        if(waitList.isEmpty()) {
                            curState = BattleState.executing;
                        }
                        break;
                    case executing:
                        executeAttacks(delta);
                        break;
                    case endingTurn:
                        System.out.println("ending turn");
                        if(!isOver()) {
                            refreshWaitList();
                            attacksList.clear();
                            curState = BattleState.turnPreparing;
                        }else
                            curState = BattleState.finishing;

                        dialogBox.setVisible(false);
                        dialogBox.setDisable(true);
                        playerMoveGrid.setDisable(false);
                        break;
                    case finishing:
                        stop();
                        break;
                }
            }
            @Override
            public void stop(){
                super.stop();
                for (Trainer t: trainers) {
                    t.endBattle();
                }
                //calc results
                if (!player.canFight() && !enemy.canFight())
                    System.out.println("result: Draw");
                else if (!enemy.canFight())
                    System.out.println("Result: " + player.name + " wins");
                else
                    System.out.println("Result: " + enemy.name + " wins");
                curStage.setScene(prevScene);
            }
        };
        battleLoop.start();
    }
}

enum BattleState{
    waiting,executing,finishing,endingTurn,turnPreparing
}
