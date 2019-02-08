package com.company.RealTime;

import com.company.Pokemon.PokemonSaveData;
import com.company.networking.TrainerData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FighterData {
    public int att,def,spAtt,spDef,speed,stamina,maxHp;
    public int curHp = maxHp;
    public String Name;
    public  String imageName;
    public String[] moves;

    public FighterData(int att, int def, int spAtt, int spDef, int speed, int stamina, int maxHp, String name, String imageName,String... moveNames) {
        this.att = att;
        this.def = def;
        this.spAtt = spAtt;
        this.spDef = spDef;
        this.speed = speed;
        this.stamina = stamina;
        this.maxHp = maxHp;
        Name = name;
        this.imageName = imageName;
        moves =moveNames;
        reset();
    }

    private static HashMap<String,FighterData> fighterMap = new HashMap<>();
    static {
        FighterData temp = getDummy1();
        fighterMap.put(temp.Name,temp);
        temp = getDummy2();
        fighterMap.put(temp.Name,temp);
    }
    public  static  FighterData getByName(String name){
        return fighterMap.getOrDefault(name,null);
    }

    public static List<FighterData> convertTrainerData(TrainerData td){
        List<FighterData> retVal = new ArrayList<>();
        for (PokemonSaveData ps: td.pokemonSaves) {
            FighterData fd = FighterData.getByName(ps.name);
            if(fd!= null)
                retVal.add(fd);
            else
                System.err.println("pokemon name " + ps.name + " has no fighter data");
        }
        return  retVal;
    }
    public static FighterData getDummy1(){
        return  new FighterData(4,3,3,3,4,100,150,"Charizard", "Assets/PokemonImages/PoGoImages/charz3d.png");
    }

    public static FighterData getDummy2(){
        return  new FighterData(3,3,3,3,2,100,150,"Pikachu", "Assets/PokemonImages/PoGoImages/pika3d.png");
    }
    public void reset(){
        curHp = maxHp;
    }
}
