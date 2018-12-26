package com.company;

import com.company.Utilities.Animation.AnimationFactory;

//#optimize cache the moves once instantiated and return them
public class MoveFactory {
    public  static  Move getAerialAce(){
        return new Move("Aerial Ace", Type.Flying,60,0,999,20, AnimationFactory.getSlashAnimation());
    }
    public static Move getFlameThrower(){
        return new Move("Flame Thrower", Type.Fire,85,0,100,15,AnimationFactory.getFlameAnimation());
    }
    public static Move getRazorLeaf(){
        return new Move("Razor Leaf", Type.Grass,65,0,100,15,AnimationFactory.getSlashAnimation());
    }
    public static Move getThunder(){
        return new Move("ThunderBolt", Type.Electric,120,0,80,5,AnimationFactory.getSlashAnimation());
    }
    public static Move getSurf(){
        return new Move("Surf", Type.Water,90,0,100,15,AnimationFactory.getSlashAnimation());
    }
    public static Move getSlam(){
        return new Move("Slam", Type.Normal,75,0,80,10,AnimationFactory.getSlashAnimation());
    }
}
