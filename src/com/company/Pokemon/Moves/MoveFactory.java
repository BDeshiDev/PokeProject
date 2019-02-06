package com.company.Pokemon.Moves;

import com.company.Pokemon.Type;
import com.company.Utilities.Animation.AnimationFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

//#optimize cache the moves once instantiated and return them
public class MoveFactory {

    private static HashMap<String,Move> moveMap = new HashMap<>();
    static {
        Move m = getAerialAce();
        moveMap.put(m.getName(),m);
        m = getFlameThrower();
        moveMap.put(m.getName(),m);
        m = getRazorLeaf();
        moveMap.put(m.getName(),m);
        m = getThunder();
        moveMap.put(m.getName(),m);
        m = getSurf();
        moveMap.put(m.getName(),m);
        m = getSlam();
        moveMap.put(m.getName(),m);
        m = getDebugKo();
        moveMap.put(m.getName(),m);
        /*
        FileWriter writer=null;
        try {
            writer=new FileWriter("C:\\Users\\USER\\IdeaProjects\\PokeProject\\src\\com\\company\\Pokemon\\Moves\\moveFactory.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();;
        String s=gson.toJson(moveMap.values());
        try {
            writer.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(s);
        */
    }
    public static  Move getMoveByName(String moveName){
        if(moveMap.containsKey(moveName))
            return moveMap.get(moveName);
        else
            return null;
    }

    public  static  Move getAerialAce(){
        return new Move("Aerial Ace", Type.Flying,DamageType.Physical,60,0,999,20, AnimationFactory.getSlashAnimation(),
                "C:\\Users\\fahim\\IdeaProjects\\PokeProject\\src\\Assets\\SFX\\FlamethrowerSFX.mp3");
    }
    public static Move getFlameThrower(){
        return new Move("Flame Thrower", Type.Fire,DamageType.Special,85,0,100,15,AnimationFactory.getFlameAnimation()
                ,"C:\\Users\\fahim\\IdeaProjects\\PokeProject\\src\\Assets\\SFX\\FlamethrowerSFX.mp3");
    }
    public static Move getRazorLeaf(){
        return new Move("Razor Leaf", Type.Grass,DamageType.Special,65,0,100,15,AnimationFactory.getSlashAnimation()
         ,"C:\\Users\\fahim\\IdeaProjects\\PokeProject\\src\\Assets\\SFX\\FlamethrowerSFX.mp3");
    }
    public static Move getThunder(){
        return new Move("ThunderBolt", Type.Electric,DamageType.Special,120,0,80,5,AnimationFactory.getSlashAnimation()
                ,"C:\\Users\\fahim\\IdeaProjects\\PokeProject\\src\\Assets\\SFX\\FlamethrowerSFX.mp3");
    }
    public static Move getSurf(){
        return new Move("Surf", Type.Water,DamageType.Special,90,0,100,15,AnimationFactory.getSlashAnimation()
                ,"C:\\Users\\fahim\\IdeaProjects\\PokeProject\\src\\Assets\\SFX\\FlamethrowerSFX.mp3");
    }
    public static Move getSlam(){
        return new Move("Slam", Type.Normal,DamageType.Physical,75,0,80,10,AnimationFactory.getSlashAnimation()
                ,"C:\\Users\\fahim\\IdeaProjects\\PokeProject\\src\\Assets\\SFX\\FlamethrowerSFX.mp3");
    }
    public static Move getDebugKo(){
        return new Move("DebugKO", Type.Normal,DamageType.None,9999,6,100,10,AnimationFactory.getSlashAnimation()
                ,"C:\\Users\\fahim\\IdeaProjects\\PokeProject\\src\\Assets\\SFX\\FlamethrowerSFX.mp3");
    }
}
