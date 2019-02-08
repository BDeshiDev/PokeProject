package com.company.Utilities.Animation;

import javafx.util.Duration;

import java.util.HashMap;

public class AnimationFactory {
    private static HashMap<String,AnimationData> animMap = new HashMap<>();
    static {
        animMap.put("Flame Thrower",getFlameAnimation());
        animMap.put("Slash",getSlashAnimation());
        animMap.put("PokeChange",getPokeChangeAnim());
        animMap.put("Bolt",getBoltAnim());
        animMap.put("Default",getPlaceholder());
    }

    public static AnimationData getAnimByName(String name){
        if(animMap.containsKey(name))
            return  animMap.get(name);
        return  animMap.get("Default");
    }
    public static AnimationData getFlameAnimation(){
        return  new AnimationData("Assets/Animations/11 - Copie.png", new Duration(500),
                4, 1, 4, 0, 0,82, 100);
    }
    public static AnimationData getSlashAnimation(){
        return  new AnimationData("Assets/Animations/4.png", new Duration(500),
                5 ,1, 5, 0, 0,102, 100);
    }

    public static AnimationData getPokeChangeAnim(){
        return  new AnimationData("Assets/Animations/blueExplosion.png", new Duration(700),
                6 ,1, 6, 0, 0,113, 100);

    }

    public static AnimationData getBoltAnim(){
        return  new AnimationData("Assets/Animations/Bolt.png", new Duration(700),
                6 ,1, 6, 0, 0,140, 86);

    }

    public static AnimationData getPlaceholder(){
        return  new AnimationData("Assets/Animations/grass.png", new Duration(700),
                5 ,1, 6, 0, 0,81, 72);

    }

}
