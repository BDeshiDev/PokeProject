package com.company.Pokemon;

import com.company.Pokemon.Stats.StatsComponent;
import com.company.Utilities.Debug.Debugger;
import com.company.Utilities.TextHandler.LineHolder;

import java.util.ArrayList;
import java.util.Random;

public class Pokemon{
    public final String name;
    public final Type t1,t2;

    private int curHp ;
    public final StatsComponent stats;

    public final String frontImage;
    public final String backImage;

    ArrayList<Move> moves;

    public boolean isDead(){
        return curHp <=0;
    }
    public double getHpRatio(){return  ((double)(curHp)) / stats.maxHp.getCurVal();}
    public int getCurHp(){return curHp;}
    public int getLevel(){return stats.level.getCurLevel();}

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

    public final ArrayList<Move> getMoves(){
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
}