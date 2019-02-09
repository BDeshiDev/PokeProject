package com.company.RealTime;

import com.company.BattleDisplayController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

/*
* Supposed to be an offline version of the player... but our game won't work offline
* */
class GridPlayer extends  BattlePlayer{
    Scene scene;
    boolean leftPressed = false,rightPressed = false,upPressed = false,downPressed = false,
            zPressed = false,xPressed = false,qPressed =false,wPressed = false,ePressed =false;
    BattleScreenController battleScreenController;
    ProgressBar turnProgressBar;
    boolean canCancelSwap = true;

    protected Queue<MoveCardData> selectedCards = new ArrayDeque<>();
    private MoveCardData defaultAttack = MoveCardData.getTestMove();

    public GridPlayer(ImageView playerImage, Grid grid, Scene scene,BattleScreenController battleScreenController, BattleDisplayController uiDisplay, List<FighterData> party) {
        super(playerImage,grid,uiDisplay,party);
        this.scene = scene;
        this.grid =grid;
        this.battleScreenController = battleScreenController;
        this.turnProgressBar = battleScreenController.getTurnBar();

        FlowPane parentPane = battleScreenController.getSwapParentPane();
        for (int i = 0; i < party.size();i++) {
            Button swapButton = new Button(party.get(i).Name);
            final  int swapNo = i;
            swapButton.setOnAction(event ->handleSwapButtonClick(swapNo));
            parentPane.getChildren().add(swapButton);
        }
        battleScreenController.getExitButton().setOnAction(event -> handleExitButton());

        addListeners(scene);
    }

    @Override
    public void updateTurn(double amount) {
        super.updateTurn(amount);
        turnProgressBar.setProgress(getTurnProgress());
    }

    @Override
    public void resetTurn() {
        super.resetTurn();
        System.out.println("reset to ");
        turnProgressBar.setProgress(getTurnProgress());
    }

    @Override
    public void handleTurnRequest() {
        super.handleTurnRequest();
        HBox powerUpBox =battleScreenController.getPowerUpBox();
        for (MoveCardData mcd:cardChoices) {
            VBox vb = new VBox();
            vb.setStyle("-fx-background-color: #00acf5");
            vb.getChildren().add(new Label(mcd.attackName));
            powerUpBox.getChildren().add(vb);
        }
    }

    public void addMove(MoveCardData dataToUse){
        selectedCards.add(dataToUse);
    }

    public void addListeners(Scene s){//the player should only move one tile at a time
        s.setOnKeyPressed(e->{
            if(!canAct)
                return;
            switch (e.getCode()){
                case UP:
                    if(!upPressed)
                        handleMove(0,1);
                    upPressed = true;
                    break;
                case DOWN:
                    if(!downPressed)
                        handleMove(0,-1);
                    downPressed = true;
                    break;
                case LEFT:
                    if(!leftPressed)
                        handleMove(-1,0);
                    leftPressed = true;
                    break;
                case RIGHT:
                    if(!rightPressed)
                        handleMove(1,0);
                    rightPressed = true;
                    break;
                case Z:
                    if(!zPressed )
                        handleAttack(defaultAttack);
                    zPressed = true;
                    break;
                case X:
                    if(!xPressed )
                        useNextSelectedMove();
                    xPressed = true;
                    break;
                case Q:
                    if(!qPressed )
                        tryChooseCard(1);
                    qPressed = true;
                    break;
                case W:
                    if(!wPressed )
                        tryChooseCard(2);
                    wPressed = true;
                    break;
                case E:
                    if(!ePressed )
                        tryChooseCard(0);
                    ePressed = true;
                    break;

            }});
        s.setOnKeyReleased(e->{
            if(!canAct)
                return;
            switch (e.getCode()){
                case UP:
                    upPressed = false;
                    break;
                case DOWN:
                    downPressed = false;
                    break;
                case LEFT:
                    leftPressed = false;
                    break;
                case RIGHT:
                    rightPressed = false;
                    break;
                case Z:
                    zPressed  = false;
                    break;
                case X:
                    xPressed  = false;
                    break;
                case Q:
                    qPressed = false;
                    break;
                case W:
                    wPressed = false;
                    break;
                case E:
                    ePressed = false;
                    break;
                case SPACE:
                    handleSwapPressed();
                    break;
            }});
    }
    public void handleSwapPressed(){
        System.out.println("Can't pause in non networked mode");
    }
    public void handleMove(int dx,int  dy){
        grid.movePlayer(this,dx,dy);
    }

    public void useNextSelectedMove(){
        handleAttack(selectedCards.poll());
    }
    public void handleAttack(MoveCardData moveToUse){
        System.out.println("can't attack in non networked mode...");
    }

    public void handleExitButton(){
    }

    public void tryChooseCard(int choiceNo){
        if(choiceNo >= cardChoices.size())
            System.out.println("invalid choice " + choiceNo);
        else if(selectedCards.size() >=3)
            System.out.println("can't hold more than 3 cards");
        else{
            battleScreenController.getPowerUpBox().getChildren().clear();//clear powerupbox after choosing move
            addMove(cardChoices.get(choiceNo));
            handleTurnConfirm();
        }
    }

    public void handleTurnConfirm(){
        HBox selectedMoveBox = battleScreenController.getSelectedMoveBox();
        selectedMoveBox.getChildren().clear();;
        for (MoveCardData mcd:selectedCards) {
            VBox moveCard = new VBox(new Label(mcd.attackName), new Label("Power: " + mcd.damagePerHit));
            moveCard.setStyle("-fx-background-color: #deff7c");
            selectedMoveBox.getChildren().add(moveCard);
        }
    }


    @Override
    public void handleSwapRequest(boolean canCancel)
    {
        this.canCancelSwap = canCancel;
        battleScreenController.toggleChoiceBox(true);
    }

    public void handleSwapButtonClick(int i){
        System.out.println("can't handle swap in non networked class");
    }
}
