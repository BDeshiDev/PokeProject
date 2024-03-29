package com.company.RealTime;

import com.company.BattleDisplayController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattlePlayer{
    String name;
    Tile curtile;
    Grid grid;
    boolean isOnLeft;
    ImageView playerImage;

    private BattleDisplayController uiDisplay;
    private int id;
    protected boolean canAct = true;

    private double turnCharge = 0;
    private final  double turnChargeThreshold = 6 * 1000;//you get a turn every 5000ms at max speed

    FighterData curFighter;
    List<FighterData> party;
    List<MoveCardData> movesList = new ArrayList<>();
    ObservableList<MoveCardData> cardChoices = FXCollections.observableArrayList();

    public BattlePlayer(String name,ImageView playerImage,Grid grid,boolean isOnLeft,BattleDisplayController uiDisplay, List<FighterData> party) {
        this.playerImage = playerImage;
        this.grid = grid;
        this.name = name;
        this.uiDisplay = uiDisplay;
        this.party = party;
        this.isOnLeft = isOnLeft;
        grid.setPlayer(this,isOnLeft);

        if(playerImage != null){
            playerImage.setScaleX(isOnLeft?-1:1);
        }
    }

    public void init(){
        setCurFighter(party.get(0));
    }

    public void resetTurn(double decreasePercentage){
        turnCharge = Math.max(turnCharge - turnChargeThreshold * decreasePercentage,0);
    }

    public void setCurFighter(FighterData fd){
        curFighter = fd;
        movesList.clear();
        cardChoices.clear();
        for (String s:fd.moves) {
            movesList.add(MoveCardData.getCardByName(s));
        }

        if(uiDisplay != null)
            uiDisplay.update(fd);
        if(playerImage != null)
            playerImage.setImage(new Image(fd.imageName));
    }

    public void setCurFighter(int index){
        setCurFighter(party.get(index));
    }

    public void moveToTile(Tile newTile){
        curtile = newTile;
        if(playerImage != null)
            playerImage.relocate(newTile.getX(),newTile.getY());
    }

    public void takeDamage(int damage,boolean isGuaranteedKill){
        curFighter.takeDamage(damage,isGuaranteedKill);
        System.out.println(curFighter.name + " took " + damage + "damage");
        if(uiDisplay != null)
            uiDisplay.update(curFighter.curHp,curFighter.maxHp);
    }

    public void takeDamage(DamageMessage dm){
        takeDamage(dm.damageAmount,dm.isGuaranteedKill);
        if(!curFighter.canFight())
            handleKo();
    }

    public void handleKo(){
        System.out.println("base class can't handle ko");
    }

    public void disableActions(boolean shouldDisable){
        canAct = !shouldDisable;
        System.out.println("player is no  disabled:" + shouldDisable);
    }

    public void handleSwap(int swapIndex){
        if(swapIndex>=party.size() || swapIndex < 0){
            System.out.println("swap index out of bounds " + swapIndex);
        }else{
            if(party.get(swapIndex).canFight()) {
                setCurFighter(swapIndex);
            }
            else
                System.out.println("invalid swap due to insufficient hp");
        }
    }

    public void handleSwapRequest(boolean canCancel){
        System.out.println("Base class can't generate swap requests");
    }

    public boolean canFight(){
        for (FighterData fd:party) {
            if(fd.canFight())
                return  true;
        }
        return  false;
    }


    public void increaseTurn(double increaseAmount){
        updateTurn(turnCharge + increaseAmount);
    }

    public void updateTurn(double newChargeAmount){
        turnCharge =newChargeAmount;
    }

    public void handleTurnRequest(){
        Random r = new Random();
        if(cardChoices.isEmpty()) {
            if (movesList.size() > 0)
                cardChoices.add(movesList.get(r.nextInt(movesList.size())));
            if (movesList.size() > 1)
                cardChoices.add(movesList.get(r.nextInt(movesList.size())));
            if (movesList.size() > 2)
                cardChoices.add(movesList.get(r.nextInt(movesList.size())));
        }
    }

    public double calculateChargeFromTicks(int ticksToAdd){
        if(curFighter == null)
            return 0;
        return ticksToAdd * ((double)curFighter.speed / curFighter.maxSpeed);
    }

    public double getTurnCharge() {
        return turnCharge;
    }


    public void updateTurn(TurnChargeMessage tcm) {
        updateTurn(tcm.chargeAmount);
        if(tcm.startTurn){
            System.out.println("starting turn");
            handleTurnRequest();
        }
    }

    public double getTurnProgress(){
        return turnCharge/turnChargeThreshold;
    }

    boolean readyForTurn(){
        return turnCharge >= turnChargeThreshold;
    }

    public void setMove(int dx,int dy){
        grid.movePlayer(this,dx,dy);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
