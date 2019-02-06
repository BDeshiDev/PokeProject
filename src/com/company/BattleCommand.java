package com.company;

import com.company.networking.JsonDataAble;

/*
* Extended version with sorting capability specifically for commands in case we want to reuse executables some where.
* */
public abstract class BattleCommand implements BattleExecutable, Comparable<BattleCommand>, JsonDataAble {
    public abstract int getPriority();
    public abstract int getSpeed();

    @Override
    public int compareTo(BattleCommand other) {
        int retVal;

        if(this.getPriority() != other.getPriority())
            return (other.getPriority() - this.getPriority());
        else
            return ( other.getSpeed() - this.getSpeed());
    }
}
