package com.company;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;

import com.sun.org.apache.bcel.internal.generic.SWAP;
import com.sun.org.apache.xpath.internal.axes.RTFIterator;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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


    private BattleUIHolder playerUI;
    private BattleUIHolder enemyUI;

    public class MovesListUI{//for easily setting moves
        private final int maxRowOrCol = 2;//maximum 4 moves but this shouldn't even come in to play normally
        private int row=0,col=0;
        private GridPane grid;

        public MovesListUI(GridPane grid){
            this.grid = grid;
        }

        public void add(Move move,pcTrainer player,Pokemon moveOwnerMon){
            if((row+1) >=maxRowOrCol && (col+1) >=maxRowOrCol){
                System.out.println("failed to add move: " + move.getName()+ " since move grid is full");
                return;
            }

            Button mButton = new Button(move.getName());
            playerMoveGrid.add(mButton,col,row);//event handler requires constant variable
            mButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    player.setCommand(move,moveOwnerMon);
                }
            });

            col++;
            if(row>maxRowOrCol){
                row++;
                col =0;
            }
        }

        public void load(Pokemon pokemonToLoad,pcTrainer player){
            final ArrayList<Move> moves = pokemonToLoad.getMoves();
            grid.getChildren().clear();//messes up the grid. change later
            for (Move m :moves) {
                add(m,player,pokemonToLoad);
            }
        }
    }
    BattleController.MovesListUI movesUI;

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

            playerSlot = new BattleSlot();
            playerSlot.setSlotUI(playerUI);
            enemySlot = new BattleSlot();
            enemySlot.setSlotUI(enemyUI);

            swapUI = new SwapUI(PartySwapPane);
            movesUI = new MovesListUI(playerMoveGrid);
            pokemonSwapButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    toggleSwapMenu(true);
                    //player.swapPokemon();
                }
            });
            swapCancelButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    toggleSwapMenu(false);
                }
            });

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
        curStage.setScene(new Scene(newRoot, Settings.windowWidth, Settings.windowLength));

        System.out.println(player.name + "  VS  " + enemy.name + "!!!");//#unimplimented show this in battle transition animation

        AnimationTimer battleLoop = new AnimationTimer() {
            ArrayList<Attack> attacksList;
            ArrayList<Trainer> waitList;
            ArrayList<Trainer> trainers;

            @Override
            public void start() {
                super.start();
                attacksList = new ArrayList<>();
                waitList = new ArrayList<>();
                trainers = new ArrayList<>();
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

            void executeAttacks() {
                Collections.sort(attacksList);
                for (Attack a : attacksList) {
                    a.execute();
                }
            }

            @Override
            public void handle(long now) {//essentially a infinite loop
                if (isOver()) {
                    stop();
                }

                for(int i = waitList.size() -1; i >= 0 ;i--){
                    Trainer t = waitList.get(i);
                    if(t.hasFinalizedCommands()){
                        attacksList.addAll(t.getCommands());
                        waitList.remove(t) ;
                    }
                }

                if (waitList.isEmpty()) {
                    executeAttacks();
                    if(!isOver()) {
                        refreshWaitList();
                        attacksList.clear();
                        prepareTurns();
                    }else{
                        stop();
                    }
                }
            }
            @Override
            public void stop() {
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
            }
        };
        battleLoop.start();
    }

}
