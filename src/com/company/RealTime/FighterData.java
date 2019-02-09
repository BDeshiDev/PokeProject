package com.company.RealTime;

import com.company.Pokemon.PokemonSaveData;
import com.company.networking.TrainerData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FighterData {
    public int att,def,spAtt,spDef,speed,stamina,maxHp;
    public final int maxSpeed = 10;
    public int curHp = maxHp;
    public String Name;
    public  String imageName;
    public MoveCardData[] moves;

    public FighterData(int att, int def, int spAtt, int spDef, int speed, int stamina, int maxHp, String name, String imageName, MoveCardData... moveNames) {
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

    public boolean canFight(){
        return  this.curHp>0;
    }
    public void takeDamage(int damage,boolean isGuaranteedKill){
        if(isGuaranteedKill)
            curHp =0;
        else
            curHp = Math.max(curHp-damage,0);
    }

    private static HashMap<String,FighterData> fighterMap = new HashMap<>();
    static {
        FighterData temp = getDummy1();
        fighterMap.put(temp.Name,temp);
        temp = getDummy2();
        fighterMap.put(temp.Name,temp);
    }
    public  static  FighterData getByName(String name){
        if(fighterMap.containsKey(name))
            return  new FighterData(fighterMap.get(name));
        return null;
    }

    public FighterData(FighterData other) {
        this(other.att,other.def,other.spAtt,other.spDef,other.speed,other.stamina,other.maxHp,other.Name,other.imageName,other.moves);
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
        return  new FighterData(4,3,3,3,4,100,150,"Charizard", "Assets/PokemonImages/PoGoImages/charz3d.png",MoveCardData.getFlameThrower(),MoveCardData.getSlash());
    }

    public static FighterData getDummy2(){
        return  new FighterData(3,3,3,3,2,100,150,"Pikachu", "Assets/PokemonImages/PoGoImages/pika3d.png",MoveCardData.getBolt(),MoveCardData.getTestMove());
    }
    public void reset(){
        curHp = maxHp;
    }

    @Override
    public String toString() {
        return "FighterData{" +
                /*"att=" + att +
                ", def=" + def +
                ", spAtt=" + spAtt +
                ", spDef=" + spDef +
                ", speed=" + speed +
                ", stamina=" + stamina +*///skip non important ones
                ", maxHp=" + maxHp +
                ", curHp=" + curHp +
                ", Name='" + Name + '\'' +
                ", imageName='" + imageName + '\'' +
                ", moves=" + Arrays.toString(moves) +
                '}';
    }
}
