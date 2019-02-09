package com.company.RealTime;

public class MoveCardData {
    public final String attackName;
    public final String animName;
    public final String iconName="Assets/charizardOoverWorld.png";
    int attackDuration;
    public final int damagePerHit;
    public final TargetPattern targetPattern;
    boolean shouldTargetOwnGrid;
    boolean shouldStopAfterCollision;

    public MoveCardData(String attackName, String animName, int attackDuration, int damagePerHit, TargetPattern targetPattern, boolean shouldTargetOwnGrid, boolean shouldStopAfterCollision) {
        this.attackName = attackName;
        this.animName = animName;
        this.attackDuration = attackDuration;
        this.damagePerHit = damagePerHit;
        this.targetPattern = targetPattern;
        this.shouldTargetOwnGrid = shouldTargetOwnGrid;
        this.shouldStopAfterCollision = shouldStopAfterCollision;
    }



    public  static MoveCardData getTestMove (){
        return  new MoveCardData("test Attack ","Default",20,30,TargetPattern.singleTile,false,true);
    }

    public  static MoveCardData getFlameThrower(){
        return  new MoveCardData("Flame Thrower","Flame Thrower",400,30,TargetPattern.row,false,true);
    }
    public  static MoveCardData getSlash(){
        return  new MoveCardData("Slash","Slash", 200,30,TargetPattern.column,false,true);
    }
    public  static MoveCardData getBolt(){
        return  new MoveCardData("ThunderBolt","Bolt",500,40,TargetPattern.column,false,true);
    }

    public AttackMessage toMessage(int userID,int posX,int posY){
        return new AttackMessage(this,userID,posX,posY);
    }
}
