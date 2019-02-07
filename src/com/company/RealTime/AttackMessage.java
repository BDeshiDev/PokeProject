package com.company.RealTime;

import java.util.List;

public class AttackMessage {
    String attackName;
    int userID;
    int userPosX, userPosY;
    int attackDuration;
    int damagePerHit;
    TargetPattern targetPattern;
    boolean shouldTargetOwnGrid;
    boolean shouldStopAfterCollision;

    public AttackMessage(String attackName, int userID, int userPosX, int userPosY, int attackDuration, int damagePerHit, TargetPattern targetPattern, boolean shouldTargetOwnGrid, boolean shouldStopAfterCollision) {
        this.attackName = attackName;
        this.userID = userID;
        this.userPosX = userPosX;
        this.userPosY = userPosY;
        this.attackDuration = attackDuration;
        this.damagePerHit = damagePerHit;
        this.targetPattern = targetPattern;
        this.shouldTargetOwnGrid = shouldTargetOwnGrid;
        this.shouldStopAfterCollision = shouldStopAfterCollision;
    }

    public  static AttackMessage getTestMessage(int userID, int userPosX, int userPosY){
        return  new AttackMessage("test Attack ", userID,userPosX,userPosY,20,10,TargetPattern.singleTile,false,true);
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


    public void addDamageTimers(Grid userGrid, Grid enemyGrid,List<AttackDamageTimer> attacksToCheck){
        int startx = shouldTargetOwnGrid?3-userPosX:enemyGrid.mirrorX(userPosX);
        int starty = userPosY;
        Grid targetGrid = shouldTargetOwnGrid?userGrid:enemyGrid;
        List<Tile> targets = getTargets(targetGrid,startx,starty);
        attacksToCheck.add(new AttackDamageTimer(attackDuration,damagePerHit,targets,shouldStopAfterCollision));
    }

}
