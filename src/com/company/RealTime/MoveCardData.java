package com.company.RealTime;

import com.company.Pokemon.Moves.DamageType;
import com.company.Pokemon.Type;

import java.util.HashMap;
import java.util.List;

public class MoveCardData {
    public final String attackName;
    public final String animName;
    public final String iconName;
    public final String sfxName;

    int attackDuration;
    public final int baseDamage;
    public final Type elementType;
    public final DamageType damageType;

    public final boolean shouldStopAfterCollision;
    public final boolean canDamageEnemy;
    public final boolean canDamageUser;

    public final TargetPattern targetPattern;
    public final int rowOffset;//how many rows should we skip before targeting, 0 means targeting will start from the row in front
    public final int maxXCount;
    public final int maxYCount;

    public final double chooseCost;//choosing this move will consume chooseCost% of the power up bar
    public final int recoveryAmount;//amount of powerup charge that you gain when you successfully hti an enemy with this move
    public final int chargeBonus;
    public final int recoveryMultForChargeAttack = 5;

    public MoveCardData(String attackName, String animName, String iconName, String sfxName, int attackDuration, int baseDamage, Type elementType, DamageType damageType,
                        TargetPattern targetPattern, boolean shouldStopAfterCollision, boolean canDamageEnemy, boolean canDamageUser,
                        int rowOffset, int maxXCount,int maxYCount,double chooseCost, int recoveryAmount, int chargeBonus) {
        this.attackName = attackName;
        this.animName = animName;
        this.iconName = iconName;
        this.sfxName = sfxName;
        this.attackDuration = attackDuration;
        this.baseDamage = baseDamage;
        this.elementType = elementType;
        this.damageType = damageType;
        this.shouldStopAfterCollision = shouldStopAfterCollision;
        this.canDamageEnemy = canDamageEnemy;
        this.canDamageUser = canDamageUser;
        this.targetPattern = targetPattern;
        this.rowOffset = rowOffset;
        this.maxXCount = maxXCount;
        this.maxYCount = maxYCount;
        this.chooseCost = chooseCost;
        this.recoveryAmount = recoveryAmount;
        this.chargeBonus = chargeBonus;
    }

    public double calculatePowerUpRecovery(double damageMod,boolean wasCharged){
        return  recoveryAmount * damageMod * (wasCharged?recoveryMultForChargeAttack:1);
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

        cardMap.put("Water Gun",new MoveCardData("Water Gun","Water","Assets/Animations/Icon/wind-blue-1.png","src/Assets/SFX/Water Gun.mp3",300,4,
                Type.Water,DamageType.Special,TargetPattern.singleTile,true,true,false,3,3,3,100,
                5,5));
        cardMap.put("Surf",new MoveCardData("Surf","Water","Assets/Animations/Icon/wind-blue-1.png","src/Assets/SFX/Water Gun.mp3",600,40,
                Type.Water,DamageType.Special,TargetPattern.column,true,true,false,3,3,3,70,
                40,5));
        cardMap.put("Ice beam",new MoveCardData("Ice beam","Ice","Assets/Animations/Icon/wind-blue-1.png","src/Assets/SFX/Ice Beam.mp3",600,30,
                Type.Ice,DamageType.Special,TargetPattern.row,true,true,false,1,3,3,70,
                40,5));
        cardMap.put("Slam",new MoveCardData("Slam","Wind","Assets/Animations/Icon/wind-blue-1.png","src/Assets/SFX/Slam.mp3",600,35,
                Type.Normal,DamageType.Physical,TargetPattern.column,true,true,false,1,3,3,50,
                40,5));
        cardMap.put("Scratch",new MoveCardData("Scratch","Wind","Assets/Animations/Icon/wind-blue-1.png","src/Assets/SFX/Slam.mp3",200,4,
                Type.Normal,DamageType.Physical,TargetPattern.singleTile,true,true,false,3,3,3,100,
                5,5));
        cardMap.put("Vine Whip",new MoveCardData("Vine Whip","Grass","Assets/Animations/Icon/leaf-acid-3.png","src/Assets/SFX/Vine Whip.mp3",200,4,
                Type.Grass,DamageType.Physical,TargetPattern.singleTile,true,true,false,3,3,3,100,
                5,5));
        cardMap.put("Vine Storm",new MoveCardData("Vine Storm","Grass","Assets/Animations/Icon/leaf-acid-3.png","src/Assets/SFX/Vine Whip.mp3",400,30,
                Type.Grass,DamageType.Special,TargetPattern.column,true,true,false,1,3,3,50,
                40,5));

        cardMap.put("Poison Needle",new MoveCardData("Poison Needle","Poison","Assets/Animations/Icon/explosion-magenta-3.png","src/Assets/SFX/Poison Sting.mp3",400,30,
                Type.Poison,DamageType.Physical,TargetPattern.row,true,true,false,1,2,3,50,
                40,5));

        cardMap.put("WhirlWind",new MoveCardData("WhirlWind","Wind","Assets/Animations/Icon/wind-sky-3.png","src/Assets/SFX/Gust.mp3",400,30,
                Type.Flying,DamageType.Special,TargetPattern.column,true,true,false,3,2,3,50,
                40,5));
        cardMap.put("Wing Attack",new MoveCardData("Wing Attack","Wind","Assets/Animations/Icon/wind-sky-3.png","src/Assets/SFX/Gust.mp3",400,30,
                Type.Flying,DamageType.Physical,TargetPattern.column,true,true,false,1,2,3,50,
                40,5));

        cardMap.put("Aerial Ace",new MoveCardData("Aerial Ace","Wind","Assets/Animations/Icon/wind-sky-3.png","src/Assets/SFX/Gust.mp3",400,30,
                Type.Flying,DamageType.Physical,TargetPattern.row,true,true,false,1,2,3,50,
                40,5));

        cardMap.put("Psychic",new MoveCardData("Psychic","Psych","Assets/Animations/Icon/runes-magenta-3.png","src/Assets/SFX/Confusion.mp3",400,50,
                Type.Psychic,DamageType.Special,TargetPattern.column,true,true,false,3,2,3,80,
                40,5));

        cardMap.put("PsyBeam",new MoveCardData("PsyBeam","Psych","Assets/Animations/Icon/runes-magenta-3.png","src/Assets/SFX/Confusion.mp3",400,35,
                Type.Psychic,DamageType.Special,TargetPattern.row,true,true,false,2,2,3,80,
                40,5));

        cardMap.put("ShockWave",new MoveCardData("ShockWave","Bolt","Assets/Animations/Icon/lighting-royal-3.png","src/Assets/SFX/Thunder Shock.mp3",400,35,
                Type.Electric,DamageType.Special,TargetPattern.row,true,true,false,2,2,3,80,
                40,5));
        cardMap.put("Punch",new MoveCardData("Punch","Wind","Assets/Animations/Icon/wind-blue-1.png","src/Assets/SFX/Slam.mp3",600,30,
                Type.Normal,DamageType.Physical,TargetPattern.row,true,true,false,1,2,3,40,
                40,5));
        cardMap.put("Iron Tail",new MoveCardData("Slam","Wind","Assets/Animations/Icon/wind-blue-1.png","src/Assets/SFX/Slam.mp3",600,35,
                Type.Steel,DamageType.Physical,TargetPattern.column,true,true,false,1,3,3,50,
                40,5));


        cardMap.put("Poison sting",new MoveCardData("Poison sting","Poison","Assets/Animations/Icon/explosion-magenta-3.png","src/Assets/SFX/Poison Sting.mp3",400,4,
                Type.Poison,DamageType.Physical,TargetPattern.singleTile,true,true,false,3,3,3,100,
                5,5));
        cardMap.put("Gust",new MoveCardData("Gust","Wind","Assets/Animations/Icon/wind-sky-3.png","src/Assets/SFX/Gust.mp3",400,4,
                Type.Flying,DamageType.Special,TargetPattern.singleTile,true,true,false,3,3,3,100,
                5,5));

        cardMap.put("Confusion",new MoveCardData("Confusion","Psych","Assets/Animations/Icon/runes-magenta-3.png","src/Assets/SFX/Confusion.mp3",400,4,
                Type.Psychic,DamageType.Special,TargetPattern.singleTile,true,true,false,3,3,3,100,
                5,5));

        cardMap.put("Thunder Shock",new MoveCardData("Thunder Shock","Bolt","Assets/Animations/Icon/lighting-royal-3.png","src/Assets/SFX/Thunder Shock.mp3",400,4,
                Type.Electric,DamageType.Special,TargetPattern.singleTile,true,true,false,3,3,3,100,
                5,5));

        cardMap.put("Ember",new MoveCardData("Ember","Flame Thrower","Assets/Animations/Icon/fireball-red-3.png","src/Assets/SFX/FlamethrowerSFX.mp3",400,4,
                Type.Fire,DamageType.Special,TargetPattern.singleTile,true,true,false,3,3,3,100,
                5,5));
    }
    public static MoveCardData  getCardByName(String name){
        return cardMap.getOrDefault(name,null);
    }

    public  static MoveCardData getTestMove (){
        return  new MoveCardData("Test Attack","Default", "Assets/PokemonImages/icons/tempIcon.png","src/Assets/SFX/FlamethrowerSFX.mp3",
                20,5,Type.None,DamageType.None,TargetPattern.singleTile,
                true,true,false,1,3,3,100,20,10);
    }

    public  static MoveCardData getFlameThrower(){
        return  new MoveCardData("Flame Thrower","Flame Thrower", "Assets/Animations/Icon/fireball-red-3.png","src/Assets/SFX/FlamethrowerSFX.mp3",
                400,20,Type.Fire,DamageType.Special,TargetPattern.row,
                true,true,false,1,3,3,70,50,0);
    }
    public  static MoveCardData getSlash(){
        return  new MoveCardData("Slash","Slash", "Assets/PokemonImages/icons/tempIcon.png","src/Assets/SFX/Slam.mp3", 200,15,
                Type.Normal,DamageType.Physical,TargetPattern.column,
                true,true,false,1,3,3,50,30,0);
    }
    public  static MoveCardData getBolt(){
        return  new MoveCardData("ThunderBolt","Bolt", "Assets/PokemonImages/icons/tempIcon.png","src/Assets/SFX/Thunder Shock.mp3",
                500,20,Type.Electric,DamageType.Physical,TargetPattern.column,
                true,true,false,1,3,3,70,50,0);
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

    public AttackMessage toMessage(int userID,boolean wasUsedOnLeft,boolean wasCharged ,int posX, int posY){
        return new AttackMessage(this,userID,wasCharged,wasUsedOnLeft,posX,posY);
    }

    public void addDamageTimers(Grid targetGrid,BattlePlayer user, List<AttackDamageTimer> attacksToCheck,AttackMessage attackMessage){
        List<Tile> targets = getTargets(targetGrid,attackMessage,user);
        attacksToCheck.add(new AttackDamageTimer(targets,attackMessage,this,user));
    }

}
