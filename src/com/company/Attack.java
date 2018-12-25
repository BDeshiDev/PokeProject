package com.company;

import java.util.*;

public class Attack implements Comparable<Attack> {//so that we can sort easily
    private final Pokemon user;
    private BattleSlot targettedSlot;
    private int targetIndex;
    private final Move move;

    public Attack(Pokemon user, Move m,BattleSlot targetedSlot) {//used for holding move data once moves are finalized
        this.user = user;
        this.targettedSlot = targetedSlot;
        this.move = m;
    }


    public int compareTo(Attack other){// for sorting
        int retVal;
        if(move==null)
            System.out.println(this.user.name+ " is null");
        if(other.move==null)
            System.out.println(other.user.name+ " is null");

        if(move.priority != other.move.priority)
            return (other.move.priority - move.priority);
        else
            return ( other.user.stats.speed.getCurVal() - this.user.stats.speed.getCurVal());
    }
    //I am changing in test purpose to learn github commit
    public void execute(){
        if(!user.isDead())
            move.use(user,targettedSlot.getCurPokemon());
    }

    @Override
    public String toString() {
        return "Attack{" +
                "user=" + user.name +
                ", move=" + move.getName() +
                '}';
    }
}
