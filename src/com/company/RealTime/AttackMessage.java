package com.company.RealTime;

import com.company.networking.BattleProtocol;
import com.company.networking.JsonDataAble;

import java.util.List;

public class AttackMessage implements JsonDataAble {
    String attackName;
    String animName;
    int userID;
    int userPosX, userPosY;
    int attackDuration;
    int damagePerHit;
    TargetPattern targetPattern;
    boolean shouldTargetOwnGrid;
    boolean shouldStopAfterCollision;

    public AttackMessage(String attackName, String animName, int userID, int userPosX, int userPosY, int attackDuration, int damagePerHit, TargetPattern targetPattern, boolean shouldTargetOwnGrid, boolean shouldStopAfterCollision) {
        this.attackName = attackName;
        this.animName = animName;
        this.userID = userID;
        this.userPosX = userPosX;
        this.userPosY = userPosY;
        this.attackDuration = attackDuration;
        this.damagePerHit = damagePerHit;
        this.targetPattern = targetPattern;
        this.shouldTargetOwnGrid = shouldTargetOwnGrid;
        this.shouldStopAfterCollision = shouldStopAfterCollision;
    }

    public AttackMessage(MoveCardData rtmd, int userID, int userPosX, int userPosY){
        this(rtmd.attackName,rtmd.animName,userID,userPosX,userPosY,rtmd.attackDuration,rtmd.damagePerHit,rtmd.targetPattern,rtmd.shouldTargetOwnGrid,rtmd.shouldStopAfterCollision);
    }


    public List<Tile> getTargets(Grid userGrid, Grid enemyGrid){
        int startx = shouldTargetOwnGrid?userPosX:enemyGrid.mirrorX(userPosX);
        int starty = userPosY;
        Grid targetGrid = shouldTargetOwnGrid?userGrid:enemyGrid;

        return getTargets(targetGrid,startx,starty);
    }

    private List<Tile> getTargets(Grid targetGrid,int startX, int startY){
        System.out.println("get tiles from " + startX +"," +startY );
        List<Tile> targets = targetPattern.getTargetTiles(targetGrid,startX,startY );
        return targets;
    }

    @Override
    public String toJsonData() {
        return BattleProtocol.createMessage(this,BattleProtocol.attackMessageHeader);
    }

    public void addDamageTimers(Grid userGrid, Grid enemyGrid, List<AttackDamageTimer> attacksToCheck){
        int startx = shouldTargetOwnGrid?3-userPosX:enemyGrid.mirrorX(userPosX);
        int starty = userPosY;
        Grid targetGrid = shouldTargetOwnGrid?userGrid:enemyGrid;
        List<Tile> targets = getTargets(targetGrid,startx,starty);
        attacksToCheck.add(new AttackDamageTimer(attackDuration,damagePerHit,targets,shouldStopAfterCollision));
    }

}
