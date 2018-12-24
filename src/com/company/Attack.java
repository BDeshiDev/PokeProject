package com.company;

import java.util.*;

public class Attack implements Comparable<Attack> {//so that we can sort easily
    private final Pokemon user;
    private final Pokemon target;
    private final Move move;

    public Attack(Pokemon user, Pokemon target, Move m) {//used for holding move data once moves are finalized
        this.user = user;
        this.target = target;
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
            return ( other.user.speed - this.user.speed);
    }
    //I am changing in test purpose to learn github commit
    public void execute(){
        if(!user.isDead())
            move.use(user,target);
    }
}
