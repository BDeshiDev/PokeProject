package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Pokemon{
    String name;
    Type t1,t2;

    private int curHp ;
    public final int maxHp;
    public final int attack;
    public final int defence;
    public final int spAttack;
    public final int spDefence;
    public final int speed;

    public final String frontImage;
    public final String backImage;

    ArrayList<Move> moves;

    public boolean isDead(){
        return curHp <=0;
    }
    public double getHpRaio(){return  curHp / maxHp;}
    public int getCurHp(){return curHp;}
    public int getLevel(){return 100;}//add proper levels later

    public Pokemon(String _name,int _maxHP, int _att, int _def, int _spAtt, int _spdef,int _spd, Type _t1,String _frontImg,String _backImg,ArrayList<Move> moves){

        this(_name,_maxHP,_att,_def, _spAtt, _spdef,_spd,_t1, Type.None,_frontImg,_backImg,moves);
    }


    public Pokemon(String _name,int _maxHP, int _att, int _def, int _spAtt, int _spdef,int _spd, Type _t1, Type _t2,String _frontImg,String _backImg ,ArrayList<Move> _moves){
        name = _name;

        curHp = maxHp = _maxHP;
        attack = _att;
        defence = _def;
        spAttack = _spAtt;
        spDefence = _spdef;
        speed = _spd;

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

    public void takeHit(Move m,int damageBonus,double stabBonus){
        double moveMod = getMoveModifier(m);
        if(moveMod > 1)
            System.out.println("It's SUPER effective!");
        else if(moveMod < 1)
            System.out.println("It's not very effective... ");
        else
            System.out.println( "... ");

        int damage = Math.max((int)((m.power + damageBonus - defence)*moveMod * stabBonus),0);
        curHp -= damage;
        System.out.println(name + " took " +damage + " damage");
        if(curHp <= 0)
            System.out.println(name + " has fainted...");
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
