package com.company;

import java.util.*;

public class Attack implements Comparable<Attack> {//so that we can sort easily
    private final Pokemon user;
    private final Pokemon target;
    private final Move move;

    public Attack(Pokemon user, Pokemon target, Move m) {
        this.user = user;
        this.target = target;
        this.move = m;
    }

    public int compareTo(Attack other){// for sorting
        int retVal;
        if(move.priority != other.move.priority)
            return (other.move.priority - move.priority);
        else
            return ( other.user.speed - this.user.speed);
    }

    public void execute(){
        if(!user.isDead())
            move.use(user,target);
    }
}
