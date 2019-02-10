package com.company.RealTime;

import com.company.BattleDisplayController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    protected ObservableList<MoveCardData> selectedCards = FXCollections.observableArrayList();//moves that you use with x
    private MoveCardData defaultAttack = MoveCardData.getTestMove();//move that you use with z

    public GridPlayer(ImageView playerImage, Grid grid,boolean isOnLeft, Scene scene,BattleScreenController battleScreenController, BattleDisplayController uiDisplay, List<FighterData> party) {
        super(playerImage,grid,isOnLeft,uiDisplay,party);
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

        battleScreenController.getCarcChoiceBox().setItems(cardChoices);
        battleScreenController.getSelectedCardList().setItems(selectedCards);

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
                        tryChooseCard(0);
                    qPressed = true;
                    break;
                case W:
                    if(!wPressed )
                        tryChooseCard(1);
                    wPressed = true;
                    break;
                case E:
                    if(!ePressed )
                        tryChooseCard(2);
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
        MoveCardData moveToUse = null;
        if(!selectedCards.isEmpty()){
            moveToUse = selectedCards.get(0);
            selectedCards.remove(0);
        }
        handleAttack(moveToUse);
    }
    public void handleAttack(MoveCardData moveToUse){
        System.out.println("can't attack in non networked mode...");
    }

    public void handleExitButton(){
    }

    @Override
    public void setCurFighter(FighterData fd) {
        super.setCurFighter(fd);
        selectedCards.clear();
    }

    public void tryChooseCard(int choiceNo){
        if(choiceNo >= cardChoices.size())
            System.out.println("invalid choice " + choiceNo);
        else if(selectedCards.size() >=3)
            System.out.println("can't hold more than 3 cards");
        else{
            addMove(cardChoices.get(choiceNo));
            cardChoices.clear();
            handleTurnConfirm();
        }
    }

    public void handleTurnConfirm(){
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
