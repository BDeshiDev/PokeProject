package com.company.RealTime;

import com.company.BattleDisplayController;
import com.company.ButtonFactory;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    HBox cardChoiceParent ,selectedCardParent;

    private MoveCardData defaultAttack;//move that you use with z
    private Rectangle[][] myGridView;
    private Rectangle playerPositionIndicator = new Rectangle(10,10,new Color(.2,.5,.6,1));
    private GridPane gridViewPane;

    List<Rectangle> highlightedSquares = new ArrayList<>();
    Color defaultCellColor = new Color(.16,.19,.3,1);

    int viewCellSize  = 30;
    Color highLightedCellColor = Color.YELLOWGREEN;

    double chargeDuration = 45;
    double chargeTimer = 0;
    boolean isChargeDisabled = false;


    public GridPlayer(String name,ImageView playerImage, Grid grid,boolean isOnLeft, Scene scene,BattleScreenController battleScreenController, BattleDisplayController uiDisplay, List<FighterData> party) {
        super(name,playerImage,grid,isOnLeft,uiDisplay,party);
        this.scene = scene;
        this.grid =grid;
        this.battleScreenController = battleScreenController;
        this.turnProgressBar = battleScreenController.getTurnBar();

        gridViewPane = battleScreenController.getPlayerGridPane();
        myGridView = grid.createGridView(gridViewPane,defaultCellColor,viewCellSize);
        UpdatePosIndicator();

        FlowPane parentPane = battleScreenController.getSwapParentPane();
        for (int i = 0; i < party.size();i++) {
            Button swapButton = ButtonFactory.getSwapButton(300,100,party.get(i).name,new Image(party.get(i).icon));

            final  int swapNo = i;
            swapButton.setOnAction(event -> handleSwapButtonClick(swapNo));
            parentPane.getChildren().add(swapButton);
        }
        battleScreenController.getExitButton().setOnAction(event -> handleExitButton());

        cardChoiceParent = battleScreenController.getCardChoiceParent();
        selectedCardParent = battleScreenController.getSlectedCardParent();

        addListeners(scene);
    }


    @Override
    public void updateTurn(double amount) {
        super.updateTurn(amount);
        turnProgressBar.setProgress(getTurnProgress());
    }

    @Override
    public void resetTurn(double decreasePercentage) {
        super.resetTurn(decreasePercentage);
        System.out.println("reset to ");
        turnProgressBar.setProgress(getTurnProgress());
    }

    @Override
    public void disableActions(boolean shouldDisable) {
        super.disableActions(shouldDisable);
        battleScreenController.getLoadingImage().setVisible(shouldDisable);
    }

    @Override
    public void moveToTile(Tile newTile) {
        super.moveToTile(newTile);
        updateGridView();
    }

    public MoveCardData getDefaultAttack() {
        return defaultAttack;
    }

    public void addMove(MoveCardData dataToUse){
        selectedCards.add(dataToUse);
        updateChoiceView(selectedCardParent,selectedCards);
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
                    if(!zPressed ) {
                        handleAttack(defaultAttack);
                        chargeTimer = 0;
                    }
                    System.out.println("charge++ " + chargeTimer);
                    chargeTimer++;
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
                    System.out.println("release the charge");
                    if(chargeTimer >= chargeDuration && !isChargeDisabled){
                        handleChargeAttack();
                    }
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
            updateGridView();
            updateChoiceView(selectedCardParent,selectedCards);
        }
        handleAttack(moveToUse);
    }
    public void handleAttack(MoveCardData moveToUse){
        System.out.println("can't attack in non networked mode...");
    }
    public void handleChargeAttack(){
        System.out.println("can't use charged attack as base class");
    }

    public void handleExitButton(){
    }

    @Override
    public void setCurFighter(FighterData fd) {
        super.setCurFighter(fd);
        selectedCards.clear();

        updateChoiceView(cardChoiceParent,cardChoices);
        updateChoiceView(selectedCardParent,selectedCards);
        defaultAttack = MoveCardData.getCardByName(fd.defaultAttack);
    }

    public void tryChooseCard(int choiceNo){
        if(choiceNo >= cardChoices.size())
            System.out.println("invalid choice " + choiceNo);
        else if(selectedCards.size() >=3)
            System.out.println("can't hold more than 3 cards");
        else{
            addMove(cardChoices.get(choiceNo));
            cardChoices.clear();

            updateChoiceView(cardChoiceParent,cardChoices);
            updateChoiceView(selectedCardParent,selectedCards);
            updateGridView();
            handleTurnConfirm();
        }
    }

    @Override
    public void handleTurnRequest() {
        super.handleTurnRequest();

        updateChoiceView(cardChoiceParent,cardChoices);
        cardChoiceParent.setVisible(true);
    }

    private void updateChoiceView(HBox parent,List<MoveCardData> cardsToUpdateFrom) {
        parent.getChildren().clear();
        for (MoveCardData mcd : cardsToUpdateFrom) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cards.fxml"));
            try {
                Node n = loader.load();
                cards c = loader.getController();
                c.setCard(mcd);
                parent.getChildren().add(n);
                n.setScaleX(.8);
                n.setScaleY(.8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateGridView(){
        clearHighlights();

        if(curtile != null && playerPositionIndicator != null) {
            UpdatePosIndicator();
        }

        if(selectedCards != null && !selectedCards.isEmpty()){
            MoveCardData selectedMove = selectedCards.get(0);
            highlightedSquares = selectedMove.targetPattern.getTargetTiles(myGridView,false,curtile.x + selectedMove.rowOffset,curtile.y,
                    selectedMove.maxXCount,selectedMove.maxYCount,Grid.tileCountX,Grid.tileCountY);
            System.out.println("updating " + highlightedSquares.size());
            for(int i = 0 ; i< myGridView.length ; i++){
                for(int j = 0 ; j< myGridView[0].length ; j++){
                    if(highlightedSquares.contains(myGridView[i][j]))
                        System.out.print("O");
                    else
                        System.out.print("X");
                }
                System.out.println();
            }
            for (Rectangle r:highlightedSquares) {
                System.out.println("another highlighted  ");
                r.setFill(highLightedCellColor);
            }
        }
    }

    private void UpdatePosIndicator() {
        battleScreenController.getPlayerGridPane().getChildren().remove(playerPositionIndicator);
        battleScreenController.getPlayerGridPane().add(playerPositionIndicator,curtile.x,Grid.tileCountY - 1 -curtile.y);
        playerPositionIndicator.relocate(curtile.getX(), curtile.getY());
    }

    public void clearHighlights(){
        if(highlightedSquares !=  null && !highlightedSquares.isEmpty()){// never use overloaded methods in constructors or you'll have to pay for it like this
            for (Rectangle r:highlightedSquares) {
                r.setFill(defaultCellColor);
            }
            highlightedSquares.clear();
        }
    }

    public void handleTurnConfirm(){
        cardChoiceParent.setVisible( false);
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
