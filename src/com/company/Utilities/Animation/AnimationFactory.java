package com.company.Utilities.Animation;

import javafx.util.Duration;

import java.util.HashMap;

public class AnimationFactory {
    private static HashMap<String,AnimationData> animMap = new HashMap<>();
    static {
        animMap.put("Flame Thrower",getFlameAnimation());
        animMap.put("Slash",getSlashAnimation());
        animMap.put("PokeChange",getPokeChangeAnim());
        animMap.put("Water",getPokeChangeAnim());
        animMap.put("Bolt",getBoltAnim());
        animMap.put("Ice",getIce());
        animMap.put("Grass",getGrassAnim());
        animMap.put("Poison",getPoison());
        animMap.put("Psych",getPsych());
        animMap.put("Wind",getWind());
        animMap.put("Default", getGrassAnim());
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

    public static AnimationData getPoison(){
        return  new AnimationData("Assets/Animations/poison.png", new Duration(700),
                6 ,1, 6, 0, 0,117, 102);

    }

    public static AnimationData getPsych(){
        return  new AnimationData("Assets/Animations/poison.png", new Duration(700),
                6 ,1, 6, 0, 0,124, 124);

    }

    public static AnimationData getWind(){
        return  new AnimationData("Assets/Animations/wind.png", new Duration(700),
                5 ,1, 5, 0, 0,73, 81);

    }

    public static AnimationData getIce(){
        return  new AnimationData("Assets/Animations/ice.png", new Duration(700),
                5 ,1, 5, 0, 0,81, 72);

    }

    public static AnimationData getGrassAnim(){
        return  new AnimationData("Assets/Animations/grass.png", new Duration(700),
                5 ,1, 6, 0, 0,81, 72);

    }


    public static AnimationData getHotFixAnim(){//for whatever reason imageviews resize if they had no images previously,use this to avoid that
        return  new AnimationData("Assets/Animations/blueExplosion.png", new Duration(70),
                5 ,1, 6, 0, 0,113, 100);

    }

}
