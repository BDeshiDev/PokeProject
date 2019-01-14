package com.company.Pokemon;

import com.company.Utilities.Animation.AnimationFactory;

//#optimize cache the moves once instantiated and return them
public class MoveFactory {
    public  static  Move getAerialAce(){
        return new Move("Aerial Ace", Type.Flying,DamageType.Physical,60,0,999,20, AnimationFactory.getSlashAnimation());
    }
    public static Move getFlameThrower(){
        return new Move("Flame Thrower", Type.Fire,DamageType.Special,85,0,100,15,AnimationFactory.getFlameAnimation());
    }
    public static Move getRazorLeaf(){
        return new Move("Razor Leaf", Type.Grass,DamageType.Special,65,0,100,15,AnimationFactory.getSlashAnimation());
    }
    public static Move getThunder(){
        return new Move("ThunderBolt", Type.Electric,DamageType.Special,120,0,80,5,AnimationFactory.getSlashAnimation());
    }
    public static Move getSurf(){
        return new Move("Surf", Type.Water,DamageType.Special,90,0,100,15,AnimationFactory.getSlashAnimation());
    }
    public static Move getSlam(){
        return new Move("Slam", Type.Normal,DamageType.Physical,75,0,80,10,AnimationFactory.getSlashAnimation());
    }
    public static Move getDebugKo(){
        return new Move("DebugKO", Type.Normal,DamageType.None,9999,6,100,10,AnimationFactory.getSlashAnimation());
    }
}
