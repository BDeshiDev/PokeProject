package com.company;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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

    public class BattleUIHolder {
        //helper class so that I don't have to mention playerNameLabel by Name
        private Label NameLabel;
        private ProgressBar HpBar;
        private Label HpLabel;
        private Label LvLabel;
        private Rectangle Indicator;
        private ImageView imageView;

        public BattleUIHolder(Label nameLabel, ProgressBar hpBar, Label hpLabel, Label lvLabel, Rectangle indicator, ImageView imageView) {
            NameLabel = nameLabel;
            HpBar = hpBar;
            HpLabel = hpLabel;
            LvLabel = lvLabel;
            Indicator = indicator;
            this.imageView = imageView;

            Indicator.setVisible(false);
        }
        public void load(Pokemon pokemon,boolean shouldUseFrontImage){
            HpBar.setProgress(pokemon.getHpRaio());
            HpLabel.setText(pokemon.getCurHp() + " / " + pokemon.maxHp);
            imageView.setImage( new Image(shouldUseFrontImage?pokemon.frontImage:pokemon.backImage));
            LvLabel.setText("LV. "+ pokemon.getLevel());
            NameLabel.setText(pokemon.name);
        }

        public void setIndicatorVisible(boolean shouldBeVisible){
            Indicator.setVisible(shouldBeVisible);
        }
    }
    private BattleUIHolder playerUI;
    private BattleUIHolder enemyUI;


    Trainer t1,t2;

    boolean canRun = true;
    boolean canUseItems = true;
    Scene prevScene;
    Parent newRoot;

    public BattleController(pcTrainer player, aiTrainer enemy){
        t1 = player;
        t2 = enemy;
        setFxml(player,enemy);
        
        final ArrayList<Move> availableMoves = player.curPokemon.getMoves();
        int r=0,c=0;
        for (int i = 0; i < availableMoves.size();i++){
            Move m = availableMoves.get(i);
            Button mButton = new Button(m.getName());
            playerMoveGrid.add(mButton,c,r);
            c++;
            if(c>1){
                c = 0;
                r++;
            }
            final int moveIndex = i;//event handler requires constant variable
            mButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    player.setCommand(moveIndex);
                }
            });
        }
    }

    private void setFxml(pcTrainer player,aiTrainer enemy){

        FXMLLoader loader = new FXMLLoader(getClass().getResource("BattleFXML.fxml"));
        loader.setController(this);
        try {
            newRoot = loader.load();
            enemyUI = new BattleUIHolder(enemyNameLabel,enemyHpBar,enemyHpLabel,enemyLvLabel,enemyTargetIndicator,enemyImageView);
            playerUI = new BattleUIHolder(playerNameLabel,playerHpBar,playerHpLabel,playerLvLabel,playerTargetIndicator,playerImageView);
            playerUI.load(player.getCurPokemon(),false);
            enemyUI.load(enemy.getCurPokemon(),true);
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

        System.out.println(t1.name + "  VS  " + t2.name + "!!!");//#unimplimented show this in battle transition animation

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
