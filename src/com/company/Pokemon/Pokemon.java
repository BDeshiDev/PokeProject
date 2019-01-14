package com.company.Pokemon;

import com.company.Pokemon.Stats.StatsComponent;
import com.company.Utilities.Debug.Debugger;
import com.company.Utilities.TextHandler.LineHolder;
import com.sun.org.glassfish.external.statistics.Stats;

import java.net.StandardSocketOptions;
import java.util.ArrayList;
import java.util.Random;

public class Pokemon{
    public final String name;
    public final Type t1,t2;

    private int curHp;
    public final StatsComponent stats;

    public final String frontImage;
    public final String backImage;

    private boolean canFight = true;

    ArrayList<Move> moves;

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
    public int getCurLevel(){return  stats.level.getCurLevel();}
    public int getXpRatio(){return  stats.level.getCurXP()/stats.level.getXpToNext();}

    public int getDefeatXp(){return  10*stats.level.getCurLevel();}//test value for level up screen

    public void applyXp(int amount, LineHolder lineHolder){
        lineHolder.push(name + " has received " + amount + " Xp." );
        int prevLevel = stats.level.getCurLevel();
        stats.addXp(amount);
        if(prevLevel != stats.level.getCurLevel()){
            lineHolder.push(name + " has reached LV." + getCurLevel() + " !!!");
        }
    }

    public void forceKo (){
        canFight = false;
    }
    public boolean canCatch(){
        return true;//modify calculation later
    }

    public Pokemon(String _name,int level, int hpAtMaxLevel, int hpBase,
                   int attAtMaxLevel, int attBase,
                   int defAtMaxLevel, int defBase,
                   int spAttAtMaxLevel, int spAttBase,
                   int spDefAtMaxLevel, int spDefBase,
                   int speedAtMaxLevel, int speedBase,
                   Type _t1,String _frontImg,String _backImg ,ArrayList<Move> _moves){
        this(_name,level,hpBase,hpAtMaxLevel,attAtMaxLevel,attBase, defAtMaxLevel, defBase,
                spAttAtMaxLevel,spAttBase, spDefAtMaxLevel,spDefBase, speedAtMaxLevel,speedBase
                ,_t1, Type.None,_frontImg,_backImg,_moves);
    }


    public Pokemon(String _name,int curLevel,int baseHp,int hpAtMaxLvl,
                   int baseAtt,int attAtMaxLvl,
                   int baseDef,int defAtMaxLvl,
                   int baseSpAtt,int spAttAtMaxLvl,
                   int baseSpDef,int spDefAtMaxLvl,
                   int baseSpeed,int speedAtMaxLvl,
                    Type _t1, Type _t2,String _frontImg,String _backImg ,ArrayList<Move> _moves){
        name = _name;
        stats = new StatsComponent(curLevel,baseHp,hpAtMaxLvl, baseAtt,attAtMaxLvl, baseDef,defAtMaxLvl,
                             baseSpAtt,spAttAtMaxLvl, baseSpDef,spDefAtMaxLvl, baseSpeed,speedAtMaxLvl);
        curHp =  stats.maxHp.getCurVal();

        t1 = _t1;
        t2 = _t2;

        moves = _moves;

        frontImage =_frontImg;
        backImage = _backImg;
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
