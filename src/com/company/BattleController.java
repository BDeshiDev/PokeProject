package com.company;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


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
    private Label playerNameLabel;
    @FXML
    private ProgressBar playerHpBar;
    @FXML
    private Label playerHpLabel;
    @FXML
    private Label playerLvLabel;
    @FXML
    private ImageView enemyImageView;
    @FXML
    private ImageView playerImageView;
    @FXML
    private GridPane playerMoveGrid;
    @FXML
    private Button playerFightButton;

    Trainer t1,t2;

    boolean canRun = true;
    boolean canUseItems = true;
    Scene prevScene;
    Parent newRoot;



    public BattleController(pcTrainer player, aiTrainer enemy){
        t1 = player;
        t2 = enemy;
        setFxml();

        playerFightButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                player.setCommand(0);//will use first move for testing
            }
        });
    }

    private void setFxml(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BattleFXML.fxml"));
        loader.setController(this);
        try {
            newRoot = loader.load();
            Pokemon enemyPoke = t1.getCurPokemon();

            enemyHpBar.setProgress(enemyPoke.getHpRaio());
            enemyHpLabel.setText(enemyPoke.getCurHp() + " / " + enemyPoke.maxHp);
            enemyImageView.setImage( new Image(enemyPoke.frontImage));
            enemyLvLabel.setText("LV. "+ enemyPoke.getLevel());
            enemyNameLabel.setText(enemyPoke.name);

            Pokemon playerPoke = t2.getCurPokemon();
            playerHpBar.setProgress(playerPoke.getHpRaio());
            playerHpLabel.setText(playerPoke.getCurHp() + " / " + playerPoke.maxHp);
            playerImageView.setImage( new Image(playerPoke.backImage));
            playerLvLabel.setText("LV. "+ playerPoke.getLevel());
            playerNameLabel.setText(playerPoke.name);
        }catch (IOException ioe){
            System.out.println("battlefxml load fail");
            System.exit(-1);
        }

    }

    public boolean isOver(){
        return !t1.canFight() || !t2.canFight();
    }

    public void begin(Stage curStage) {
        curStage.setScene(new Scene(newRoot, Settings.windowWidth, Settings.windowLength));

        System.out.println(t1.name + "  VS  " + t2.name + "!!!");

        AnimationTimer battleLoop = new AnimationTimer() {
            ArrayList<Attack> attacksList;
            ArrayList<Trainer> waitList;

            @Override
            public void start() {
                super.start();
                attacksList = new ArrayList<>();
                waitList = new ArrayList<>();

                refreshWaitList();
            }

            void refreshWaitList() {
                waitList.clear();
                waitList.add(t1);
                waitList.add(t2);


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

                if (waitList.contains(t1)&& t1.hasCommand()) {//temp fix until we implement targeting logic to set move target outside
                    attacksList.add(t1.getCommand(t2.getCurPokemon()));
                    waitList.remove(t1);
                }
                if (waitList.contains(t2) && t2.hasCommand()) {//temp fix until we implement targeting logic to set move target outside
                    attacksList.add(t2.getCommand(t1.getCurPokemon()));
                    waitList.remove(t2);
                }

                if (waitList.isEmpty()) {
                    executeAttacks();
                    refreshWaitList();
                    t1.prepTurn();
                    t2.prepTurn();
                }

            }

            @Override
            public void stop() {
                super.stop();
                //calc results
                if (!t1.canFight() && !t2.canFight())
                    System.out.println("result: Draw");
                else if (!t2.canFight())
                    System.out.println("Result: " + t1.name + " wins");
                else
                    System.out.println("Result: " + t2.name + " wins");
            }
        };
        battleLoop.start();


    }

}
