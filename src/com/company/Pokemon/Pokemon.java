package com.company.Pokemon;

import com.company.Pokemon.Moves.Move;
import com.company.Pokemon.Stats.StatsComponent;
import com.company.PokemonFactory;
import com.company.Settings;
import com.company.Utilities.Debug.Debugger;
import com.company.Utilities.TextHandler.LineHolder;

import java.util.ArrayList;
import java.util.Random;

public class Pokemon{

    static int nextId = 1;

    public  final String name;
    public  final Type t1,t2;
    public final int uniqueId;

    private int curHp;
    public final StatsComponent stats;

    public final String frontImage;

    private boolean canFight = true;

    ArrayList<Move> moves;

    public final EvolutionData evoData;

    public boolean isDead(){
        return curHp <=0 || !canFight;
    }

    public double getHpRatio(){return  ((double)(curHp)) / stats.maxHp.getCurVal();}
    public int getCurHp(){return curHp;}
    public int getLevel(){return stats.level.getCurLevel();}
    public int getAtt(){return stats.attack.getCurVal();}
    public int getDef(){return stats.defence.getCurVal();}
    public int getSpAtt(){return stats.spAttack.getCurVal();}
    public int getSpDef(){return stats.spDefence.getCurVal();}
    public int getSpeed(){return stats.speed.getCurVal();}
    public int getMaxHp(){return stats.maxHp.getCurVal();}
    public int getCurXp(){return  stats.level.getCurXP();}
    public int getXpRatio(){return  stats.level.getCurXP()/stats.level.getXpToNext();}

    public int getDefeatXp(){return  10*stats.level.getCurLevel();}//test value for level up screen

    public Pokemon applyXp(int amount, LineHolder lineHolder){//apply xp and return the evoloved mon if conditions are met,else return this
        lineHolder.push(name + " has received " + amount + " Xp." );
        int prevLevel = stats.level.getCurLevel();
        stats.addXp(amount);
        if(prevLevel != stats.level.getCurLevel()){
            lineHolder.push(name + " has reached LV." + getLevel() + " !!!");
        }
        if(evoData.canEvolve(stats.level)){
            Pokemon evolvedForm = PokemonFactory.getMonByName(evoData.monToEvolveTo,stats.level.getCurLevel());
            lineHolder.push(name + " has evolved into " + evolvedForm.name  + " !!!");
            return evolvedForm;
        }else
            return  this;
    }

    public void forceKo (){
        canFight = false;
    }
    public boolean canCatch(){
        return true;//modify calculation later
    }
    public Pokemon(PokemonData data, int level){
        this(data.name,new StatsComponent(data.stats,level), data.t1,data.t2,data.frontImage,data.evoData,data.moves);
    }
    public Pokemon(String _name,int level, int hpAtMaxLevel, int hpBase,
                   int attAtMaxLevel, int attBase, int defAtMaxLevel, int defBase,
                   int spAttAtMaxLevel, int spAttBase, int spDefAtMaxLevel, int spDefBase, int speedAtMaxLevel, int speedBase,
                   Type _t1,String _frontImg,EvolutionData evoData,ArrayList<Move> _moves){
        this(_name,level,hpBase,hpAtMaxLevel,attAtMaxLevel,attBase, defAtMaxLevel, defBase,
                spAttAtMaxLevel,spAttBase, spDefAtMaxLevel,spDefBase, speedAtMaxLevel,speedBase
                ,_t1, Type.None,_frontImg,evoData,_moves);
    }
    public Pokemon(String _name,int curLevel,int baseHp,int hpAtMaxLvl,
                   int baseAtt,int attAtMaxLvl,
                   int baseDef,int defAtMaxLvl,
                   int baseSpAtt,int spAttAtMaxLvl,
                   int baseSpDef,int spDefAtMaxLvl,
                   int baseSpeed,int speedAtMaxLvl,
                    Type _t1, Type _t2,String _frontImg ,EvolutionData evoData,ArrayList<Move> _moves){
        this( _name,new StatsComponent(curLevel,baseHp,hpAtMaxLvl, baseAtt,attAtMaxLvl, baseDef,defAtMaxLvl,
                baseSpAtt,spAttAtMaxLvl, baseSpDef,spDefAtMaxLvl, baseSpeed,speedAtMaxLvl),
                _t1, _t2, _frontImg,evoData ,_moves);

    }
    public Pokemon(String _name,StatsComponent stats, Type _t1, Type _t2,String _frontImg,EvolutionData evoData,ArrayList<Move> _moves){
        this.stats = stats;
        this.evoData = evoData;
        curHp =  stats.maxHp.getCurVal();
        name = _name + (Settings.IsDebugOn?new Random().nextInt(50):"");
        t1 = _t1;
        t2 = _t2;

        uniqueId = nextId;
        nextId++;

        moves = _moves;

        frontImage =_frontImg;
    }


    public void printTurn(){
        System.out.println(name + " HP: " + curHp);
        System.out.println("Moves :");
        int i=0;
        for (Move m:moves) {
            System.out.println(++i + ". " + m.getName());
        }
    }

    public Move getMove(int moveNo){
        if(moveNo > moves.size() || moveNo <1)
            moveNo = 1;
        return  moves.get(moveNo-1);
    }

    public Move getRandomMove(Random rng){
        int randomIndex = rng.nextInt(moves.size());//0 =min to move.size-1 = max no mod necessary
        return  moves.get(randomIndex);
    }

    public  ArrayList<Move> getMoves(){
        return moves;
    }

    public void takeDamage(int damage, LineHolder streamToAppendTo){
        curHp -= damage;
        Debugger.out(name + " took " +damage + " damage");
        streamToAppendTo.push(name + " took " +damage + " damage");
        if(curHp <= 0)
            streamToAppendTo.push(name + " has fainted...");
    }

    public double getMoveModifier(Move move){
        return  getMoveModifier(move.type);
    }
    public double getMoveModifier(Type other){
        return t1.getModifier(other) * t2.getModifier(other);
    }

    public double getStabBoost(Move move){
        double retVal = 1;
        if(move.type == t1 || move.type == t2)
            retVal *=1.5;
        return  retVal;
    }

    public void heal(){
        canFight = true;
        curHp = stats.maxHp.getCurVal();
        for (Move m:moves) {
            m.resetPp();
        }
    }
}
