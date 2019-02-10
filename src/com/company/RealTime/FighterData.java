package com.company.RealTime;

import com.company.Pokemon.Moves.DamageType;
import com.company.Pokemon.PokemonSaveData;
import com.company.Pokemon.Type;
import com.company.networking.TrainerData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FighterData {
    public int att,def,spAtt,spDef,speed,maxHp;
    public final int maxSpeed = 10;
    public int curHp = maxHp;
    public String Name;
    public  String imageName;
    public MoveCardData[] moves;
    Type t1,t2;

    public FighterData(int att, int def, int spAtt, int spDef, int speed, int maxHp, String name, String imageName, Type t1, Type t2, MoveCardData... moves) {
        this.att = att;
        this.def = def;
        this.spAtt = spAtt;
        this.spDef = spDef;
        this.speed = speed;
        this.maxHp = maxHp;
        Name = name;
        this.imageName = imageName;
        this.moves = moves;
        this.t1 = t1;
        this.t2 = t2;

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
        this(other.att,other.def,other.spAtt,other.spDef,other.speed,other.maxHp,other.Name,other.imageName,other.t1,other.t2,other.moves);
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
        return  new FighterData(4,3,3,3,4,150,"Charizard", "Assets/PokemonImages/PoGoImages/charz3d.png",
                Type.Fire,Type.Flying,MoveCardData.getFlameThrower(),MoveCardData.getSlash());
    }

    public static FighterData getDummy2(){
        return  new FighterData(3,3,3,3,2,150,"Pikachu", "Assets/PokemonImages/PoGoImages/pika3d.png",Type.Electric,Type.None,MoveCardData.getBolt(),MoveCardData.getTestMove());
    }
    public void reset(){
        curHp = maxHp;
    }


    public double getMoveModifier(MoveCardData move){
        return  getMoveModifier(move.elementType);
    }
    public double getMoveModifier(Type other){
        return t1.getModifier(other) * t2.getModifier(other);
    }
    public double getStabBoost(Type type){
        double retVal = 1;
        if(type == t1 || type == t2)
            retVal *=1.5;
        return  retVal;
    }

    public int calculateDamage(FighterData attacker, Type elementType, DamageType damageType,int baseDamage){//defender == this
        double mod = this.getMoveModifier(elementType);
        double stabBoost = attacker.getStabBoost(elementType);

        return (int)Math.ceil(baseDamage * mod * stabBoost *damageType.getAttackBonus(attacker)/damageType.getDefenceBonus(this));//deal atleast 1 damage
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
