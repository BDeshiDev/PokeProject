package com.company.Pokemon.Moves;

import com.company.Pokemon.Type;
import com.company.Utilities.Animation.AnimationFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import java.io.*;
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
        moveMap.put("Wing Attack", new Move("Wing Attack", Type.Flying,DamageType.Physical,80,0,100,20,AnimationFactory.getWind()
                ,"src/Assets/SFX/Slam.mp3" ));
        moveMap.put("Slam", new Move("Slam", Type.Normal,DamageType.Physical,90,0,100,20,AnimationFactory.getWind()
                ,"src/Assets/SFX/Slam.mp3" ));
        moveMap.put("Cross Poison", new Move("Cross Poison", Type.Poison,DamageType.Physical,80,0,100,20,AnimationFactory.getPoison()
                ,"src/Assets/SFX/Poison Sting.mp3" ));
        moveMap.put("Poison Needle", new Move("Poison Needle", Type.Bug,DamageType.Physical,70,0,100,20,AnimationFactory.getPoison()
                ,"src/Assets/SFX/Poison Sting.mp3" ));
        moveMap.put("Thunder Bolt", new Move("Thunder Bolt", Type.Electric,DamageType.Special,90,0,100,20,AnimationFactory.getBoltAnim()
                ,"src/Assets/SFX/Thunder Shock.mp3" ));
        moveMap.put("Iron tail", new Move("Iron tail", Type.Steel,DamageType.Special,70,0,100,20,AnimationFactory.getWind()
                ,"src/Assets/SFX/Slam.mp3" ));
        moveMap.put("Fire Blast", new Move("Fire Blast", Type.Fire,DamageType.Special,120,0,100,20,AnimationFactory.getFlameAnimation()
                ,"src/Assets/SFX/FlamethrowerSFX.mp3" ));
        moveMap.put("Ice Beam", new Move("Ice Beam", Type.Ice,DamageType.Special,90,0,100,20,AnimationFactory.getIce()
                ,"src/Assets/SFX/Ice Beam.mp3" ));
        moveMap.put("Vine Storm",new Move("Vine Storm", Type.Grass,DamageType.Special,100,0,100,15,AnimationFactory.getGrassAnim()
                ,"src/Assets/SFX/Vine Whip.mp3"));

//        JsonWriter jw=null;
//        Gson gson=new Gson();
//        try {
//            JsonWriter writer = new JsonWriter(new FileWriter("src/com/company/Pokemon/Moves/moveFactory.txt"));
//            writer.setIndent("  ");
//            gson.toJson(moveMap.values(),moveMap.values().getClass(), writer);
//            writer.flush();
//            writer.close();
//        }catch (IOException ioe){
//            System.out.println("write failed...");
//        }

/*
        FileReader fr=null;
        try {
            fr=new FileReader(new File("src/com/company/Pokemon/Moves/moveFactory.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Cant load file");
        }

        Gson gson=new Gson();
        Move moves[]=gson.fromJson(fr,Move[].class);
        System.out.println(moveMap.values().getClass());
        for (Move m:
                moves) {
            moveMap.put(m.getName(),m);
        }*/
    }
    public static  Move getMoveByName(String moveName){
        if(moveMap.containsKey(moveName))
            return moveMap.get(moveName);
        else
            return null;
    }

    public  static  Move getAerialAce(){
        return new Move("Aerial Ace", Type.Flying,DamageType.Physical,60,0,999,20, AnimationFactory.getSlashAnimation()
                ,"src/Assets/SFX/Slam.mp3" );
    }
    public static Move getFlameThrower(){
        return new Move("Flame Thrower", Type.Fire,DamageType.Special,85,0,100,15,AnimationFactory.getFlameAnimation()
                ,"src/Assets/SFX/FlamethrowerSFX.mp3");
    }
    public static Move getRazorLeaf(){
        return new Move("Razor Leaf", Type.Grass,DamageType.Special,65,0,100,15,AnimationFactory.getSlashAnimation()
                ,"src/Assets/SFX/Vine Whip.mp3");
    }
    public static Move getThunder(){
        return new Move("ThunderBolt", Type.Electric,DamageType.Special,120,0,80,5,AnimationFactory.getBoltAnim()
                ,"src/Assets/SFX/Thunder Shock.mp3");
    }
    public static Move getSurf(){
        return new Move("Surf", Type.Water,DamageType.Special,90,0,100,15,AnimationFactory.getIce()
                ,"src/Assets/SFX/Water Gun.mp3");
    }
    public static Move getSlam(){
        return new Move("Slam", Type.Normal,DamageType.Physical,75,0,80,10,AnimationFactory.getWind()
                ,"src/Assets/SFX/Slam.mp3");
    }
    public static Move getDebugKo(){
        return new Move("DebugKO", Type.Normal,DamageType.None,9999,6,100,10,AnimationFactory.getSlashAnimation()
                ,"src/Assets/SFX/FlamethrowerSFX.mp3");
    }
}
