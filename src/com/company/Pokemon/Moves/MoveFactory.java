package com.company.Pokemon.Moves;

import com.company.Pokemon.Type;
import com.company.Utilities.Animation.AnimationFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.zip.GZIPOutputStream;

//#optimize cache the moves once instantiated and return them
public class MoveFactory {

    private static HashMap<String,Move> moveMap = new HashMap<>();
    static {
//        Move m = getAerialAce();
//        moveMap.put(m.getName(),m);
//        m = getFlameThrower();
//        moveMap.put(m.getName(),m);
//        m = getRazorLeaf();
//        moveMap.put(m.getName(),m);
//        m = getThunder();
//        moveMap.put(m.getName(),m);
//        m = getSurf();
//        moveMap.put(m.getName(),m);
//        m = getSlam();
//        moveMap.put(m.getName(),m);
//        m = getDebugKo();
//        moveMap.put(m.getName(),m);
//
//        JsonWriter jw=null;
//        try {
//            jw=new JsonWriter(new FileWriter("C:\\Users\\USER\\IdeaProjects\\PokeProject\\src\\com\\company\\Pokemon\\Moves\\moveFactory.txt"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Gson gson=new Gson();
//        try {
//            JsonWriter writer = new JsonWriter(new FileWriter("C:\\Users\\USER\\IdeaProjects\\PokeProject\\src\\com\\company\\Pokemon\\Moves\\moveFactory.txt"));
//            writer.setIndent("  ");
//            gson.toJson(moveMap.values(),moveMap.values().getClass(), writer);
//            writer.flush();
//            writer.close();
//        }catch (IOException ioe){
//            System.out.println("save failed");
//        }

        FileReader fr=null;
        try {
            fr=new FileReader(new File("src/com/company/Pokemon/Moves/moveFactory.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Cant load file");
        }
        Gson gson=new Gson();
        Move moves[]=gson.fromJson(fr,Move[].class);
        System.out.println(moves);
        System.out.println(moveMap.values().getClass());
        for (Move m:
             moves) {
            moveMap.put(m.getName(),m);
            System.out.println(m.getName());
        }


    }
    public static  Move getMoveByName(String moveName){
        if(moveMap.containsKey(moveName))
            return moveMap.get(moveName);
        else
            return null;
    }

    public  static  Move getAerialAce(){
        return new Move("Aerial Ace", Type.Flying,DamageType.Physical,60,0,999,20, AnimationFactory.getSlashAnimation(),"j");
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
