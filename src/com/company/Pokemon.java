package com.company;

import java.util.ArrayList;
import java.util.List;

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


    List<Move> moves;

    public boolean isDead(){
        return curHp <=0;
    }

    public Pokemon(String _name,int _maxHP, int _att, int _def, int _spAtt, int _spdef,int _spd, Type _t1,Move..._moves){

        this(_name,_maxHP,_att,_def, _spAtt, _spdef,_spd,_t1, Type.None);
    }


    public Pokemon(String _name,int _maxHP, int _att, int _def, int _spAtt, int _spdef,int _spd, Type _t1, Type _t2 ,Move... _moves){
        name = _name;

        curHp = maxHp = _maxHP;
        attack = _att;
        defence = _def;
        spAttack = _spAtt;
        spDefence = _spdef;
        speed = _spd;

        t1 = _t1;
        t2 = _t2;

        moves = new ArrayList<>();
        for (Move m:_moves) {
            moves.add(m);
        }
    }

    public void printTurn(){
        System.out.println(name + " HP: " + curHp);
        System.out.println("Moves :");
        int i=0;
        for (Move m:moves) {
            System.out.println(++i + ". " + m.getName());
        }
    }

    public Attack receiveCommand(int moveNo,Pokemon target){
        if(moveNo > moves.size() || moveNo <1)
            moveNo = 1;

        return  new Attack(this,target,moves.get(moveNo-1));
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
