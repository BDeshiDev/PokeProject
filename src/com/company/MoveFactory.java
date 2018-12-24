package com.company;

public class MoveFactory {
    public  static  Move getAerialAce(){
        return new Move("Aerial Ace", Type.Flying,60,0,999,20);
    }
    public static Move getFlameThrower(){
        return new Move("Flame Thrower", Type.Fire,85,0,100,15);
    }
    public static Move getRazorLeaf(){
        return new Move("Razor Leaf", Type.Grass,65,0,100,15);
    }
    public static Move getThunder(){
        return new Move("ThunderBolt", Type.Electric,120,0,80,5);
    }
    public static Move getSurf(){
        return new Move("Surf", Type.Water,90,0,100,15);
    }
    public static Move getSlam(){
        return new Move("Slam", Type.Normal,75,0,80,10);
    }
}
