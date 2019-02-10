package com.company.RealTime;

import com.company.Pokemon.Moves.DamageType;
import com.company.Pokemon.Type;
import javafx.scene.Node;

import java.util.HashMap;
import java.util.List;

public class MoveCardData {
    private static boolean tr;
    public final String attackName;
    public final String animName;
    public final String iconName= "Assets/PokemonImages/icons/tempIcon.png";
    public final String sfxName ="Assets/SFX/FlamethrowerSFX.mp3";
    int attackDuration;
    public final int baseDamage;
    public final Type elementType;
    public final DamageType damageType;
    public final TargetPattern targetPattern;
    public final boolean shouldStopAfterCollision;
    public final boolean canDamageEnemy;
    public final boolean canDamageUser;
    public final int rowOffset;//how many rows should we skip before targeting, 0 means targeting will start from the row in front
    public final int maxXCount = 3;
    public final int maxYCount = 3;

    public MoveCardData(String attackName, String animName, int attackDuration, int baseDamage, Type elementType, DamageType damageType, TargetPattern targetPattern, boolean shouldStopAfterCollision, boolean canDamageEnemy, boolean canDamageUser, int rowOffset) {
        this.attackName = attackName;
        this.animName = animName;
        this.attackDuration = attackDuration;
        this.baseDamage = baseDamage;
        this.elementType = elementType;
        this.damageType = damageType;
        this.targetPattern = targetPattern;
        this.shouldStopAfterCollision = shouldStopAfterCollision;
        this.canDamageEnemy = canDamageEnemy;
        this.canDamageUser = canDamageUser;
        this.rowOffset = rowOffset;
    }

    public MoveCardData(MoveCardData other) {
        this(other.attackName,other.animName,other.attackDuration,other.baseDamage,other.elementType,other.damageType,
                other.targetPattern,other.shouldStopAfterCollision,other.canDamageEnemy,other.canDamageUser,other.rowOffset);
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
        return  new MoveCardData("test Attack ","Default",20,5,Type.None,DamageType.None,TargetPattern.singleTile,true,true,false,1);
    }

    public  static MoveCardData getFlameThrower(){
        return  new MoveCardData("Flame Thrower","Flame Thrower",400,20,Type.Fire,DamageType.Special,TargetPattern.row,true,true,false,1);
    }
    public  static MoveCardData getSlash(){
        return  new MoveCardData("Slash","Slash", 200,15,Type.Normal,DamageType.Physical,TargetPattern.column,true,true,false,1);
    }
    public  static MoveCardData getBolt(){
        tr = true;
        return  new MoveCardData("ThunderBolt","Bolt",500,20,Type.Electric,DamageType.Physical,TargetPattern.column,true,tr,false,1);
    }

    @Override
    public String toString() {
        return "MoveCardData{" +
                "attackName='" + attackName + '\'' +
                ", animName='" + animName + '\'' +
                ", iconName='" + iconName + '\'' +
                ", baseDamage=" + baseDamage +
                ", targetPattern=" + targetPattern +
                ", shouldStopAfterCollision=" + shouldStopAfterCollision +
                '}';
    }

    public List<Tile> getTargets(Grid targetGrid, AttackMessage attackMessage,BattlePlayer user){
        boolean isMirrored = attackMessage.wasUsedOnLeft != user.isOnLeft;
        int startx =  attackMessage.userPosX;
        int starty = attackMessage.userPosY;
        System.out.println("trying tiles from " + startx +"," +starty );
        return getTargets(targetGrid,isMirrored,startx,starty);
    }


    private List<Tile> getTargets(Grid targetGrid, boolean isMirrored , int startX, int startY){
        startX += rowOffset;
        if(isMirrored)
            startX = targetGrid.mirrorX(startX);

        System.out.println("get tiles from " + startX +"," +startY );
        List<Tile> targets = targetPattern.getTargetTiles(targetGrid.grid,isMirrored,startX,startY,maxXCount,maxYCount,Grid.tileCountX,Grid.tileCountY );
        return targets;
    }

    public AttackMessage toMessage(int userID,boolean wasUsedOnLeft, int posX, int posY){
        return new AttackMessage(this,userID,wasUsedOnLeft,posX,posY);
    }

    public void addDamageTimers(Grid targetGrid,BattlePlayer user, List<AttackDamageTimer> attacksToCheck,AttackMessage attackMessage){
        List<Tile> targets = getTargets(targetGrid,attackMessage,user);
        attacksToCheck.add(new AttackDamageTimer(targets,user.curFighter,attackMessage,this));
    }

}
