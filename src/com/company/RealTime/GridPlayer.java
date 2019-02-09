package com.company.RealTime;

import com.company.BattleDisplayController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/*
* Supposed to be an offline version of the player... but our game won't work offline
* */
class GridPlayer extends  BattlePlayer{
    Scene scene;
    boolean leftPressed = false,rightPressed = false,upPressed = false,downPressed = false,
            zPressed = false,xPressed = false;
    BattleScreenController battleScreenController;
    ProgressBar turnProgressBar;
    boolean canCancelSwap = true;

    protected Queue<MoveCardData> selectedMoves;
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
        battleScreenController.getExitButton().setOnAction(event -> handleConfirmButton());

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
        battleScreenController.toggleChoiceBox(true);
    }

    @Override
    public void setCurFighter(FighterData fd) {
        super.setCurFighter(fd);

        if(selectedMoves == null) {
            selectedMoves = new ArrayDeque<>();
            System.out.println("making queue");
        }
        selectedMoves.clear();

        if(battleScreenController != null){
            FlowPane p = battleScreenController.getMoveCardView();
            p.getChildren().clear();
            for (MoveCardData mcd:movesList) {
                System.out.println("adding " + mcd.attackName);
                Button moveUseButton = new Button("Add");
                moveUseButton.setOnAction(e->{addMove(mcd);});

                VBox vb= new VBox(20,new Label(mcd.attackName),new Label("power :" + mcd.damagePerHit),moveUseButton);
                vb.setAlignment(Pos.CENTER);
                vb.setStyle("-fx-background-color: #eaf6ff");
                p.getChildren().add(vb);
            }
        }
    }

    public void addMove(MoveCardData dataToUse){
        selectedMoves.add(dataToUse);
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
                case SPACE:
                    handleMenuPressed();
                    break;
            }});
    }
    public void handleMenuPressed(){
        System.out.println("Can't pause in non networked mode");
    }
    public void handleMove(int dx,int  dy){
        grid.movePlayer(this,dx,dy);
    }

    public void useNextSelectedMove(){
        handleAttack(selectedMoves.poll());
    }
    public void handleAttack(MoveCardData moveToUse){
        System.out.println("can't attack in non networked mode...");
    }

    public void handleConfirmButton(){ }

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
