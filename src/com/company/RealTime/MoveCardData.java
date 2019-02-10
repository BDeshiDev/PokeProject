package com.company.RealTime;

import com.company.Pokemon.Moves.DamageType;
import com.company.Pokemon.Type;

import java.util.HashMap;
import java.util.List;

public class MoveCardData {
    public final String attackName;
    public final String animName;
    public final String iconName= "Assets/PokemonImages/icons/tempIcon.png";
    public final String sfxName ="Assets/SFX/FlamethrowerSFX.mp3";
    int attackDuration;
    public final int baseDamage;
    public final Type elementType;
    public final DamageType damageType;
    public final TargetPattern targetPattern;
    public final boolean shouldTargetOwnGrid;
    public final boolean shouldStopAfterCollision;
    public final boolean canDamageEnemy = true;
    public final boolean canDamageUser =false;
    public final int rowOffset = 0;//how many rows should we skip before targeting, 0 means targeting will start from the row in front

    public MoveCardData(String attackName, String animName, int attackDuration, int baseDamage, Type elementType, DamageType damageType, TargetPattern targetPattern, boolean shouldTargetOwnGrid, boolean shouldStopAfterCollision) {
        this.attackName = attackName;
        this.animName = animName;
        this.attackDuration = attackDuration;
        this.baseDamage = baseDamage;
        this.elementType = elementType;
        this.damageType = damageType;
        this.targetPattern = targetPattern;
        this.shouldTargetOwnGrid = shouldTargetOwnGrid;
        this.shouldStopAfterCollision = shouldStopAfterCollision;
    }

    public MoveCardData(MoveCardData other) {
        this(other.attackName,other.animName,other.attackDuration,other.baseDamage,other.elementType,other.damageType,other.targetPattern,other.shouldTargetOwnGrid,other.shouldStopAfterCollision);
    }

    public static final HashMap<String,MoveCardData> cardMap = new HashMap<>();
    static {
        MoveCardData temp = getTestMove();
        cardMap.put(temp.attackName,temp);
        temp = getBolt();
        cardMap.put(temp.attackName,temp);
        temp = getFlameThrower();
        cardMap.put(temp.attackName,temp);
        temp = getSlash();
        cardMap.put(temp.attackName,temp);
    }
    public static MoveCardData  getCardByName(String name){
        return cardMap.getOrDefault(name,null);
    }

    public  static MoveCardData getTestMove (){
        return  new MoveCardData("test Attack ","Default",20,5,Type.None,DamageType.None,TargetPattern.singleTile,false,true);
    }

    public  static MoveCardData getFlameThrower(){
        return  new MoveCardData("Flame Thrower","Flame Thrower",400,20,Type.Fire,DamageType.Special,TargetPattern.row,false,true);
    }
    public  static MoveCardData getSlash(){
        return  new MoveCardData("Slash","Slash", 200,15,Type.Normal,DamageType.Physical,TargetPattern.column,false,true);
    }
    public  static MoveCardData getBolt(){
        return  new MoveCardData("ThunderBolt","Bolt",500,20,Type.Electric,DamageType.Physical,TargetPattern.column,false,true);
    }

    @Override
    public String toString() {
        return "MoveCardData{" +
                "attackName='" + attackName + '\'' +
                ", animName='" + animName + '\'' +
                ", iconName='" + iconName + '\'' +
                ", baseDamage=" + baseDamage +
                ", targetPattern=" + targetPattern +
                ", shouldTargetOwnGrid=" + shouldTargetOwnGrid +
                ", shouldStopAfterCollision=" + shouldStopAfterCollision +
                '}';
    }

    public List<Tile> getTargets(Grid userGrid, Grid enemyGrid,AttackMessage attackMessage){
        int startx = shouldTargetOwnGrid?attackMessage.userPosX:enemyGrid.mirrorX(attackMessage.userPosX);
        int starty = attackMessage.userPosY;
        Grid targetGrid = shouldTargetOwnGrid?userGrid:enemyGrid;

        return getTargets(targetGrid,startx,starty);
    }

    private List<Tile> getTargets(Grid targetGrid,int startX, int startY){
        System.out.println("get tiles from " + startX +"," +startY );
        List<Tile> targets = targetPattern.getTargetTiles(targetGrid,startX,startY );
        return targets;
    }

    public AttackMessage toMessage(int userID, int posX, int posY){
        return new AttackMessage(this,userID,posX,posY);
    }

    public void addDamageTimers(Grid userGrid, Grid enemyGrid,FighterData user, List<AttackDamageTimer> attacksToCheck,AttackMessage attackMessage){
        int startx = shouldTargetOwnGrid?attackMessage.userPosX:enemyGrid.mirrorX(attackMessage.userPosX);
        int starty = attackMessage.userPosY;
        Grid targetGrid = shouldTargetOwnGrid?userGrid:enemyGrid;
        List<Tile> targets = getTargets(targetGrid,startx,starty);
        attacksToCheck.add(new AttackDamageTimer(targets,user,attackMessage,this));
    }

}
