package com.company;
/*
* used to determine which stat gives bonus power
* */
public enum DamageType {
    Physical,Special,None;
    int getAttackBonus(Pokemon user){
        int retVal = 0;
        switch (this){
            case Physical:
                retVal = user.stats.attack.getCurVal();
                break;
            case Special:
                retVal =  user.stats.spAttack.getCurVal();
                break;
            case None:
                retVal =   0;
                break;
        }
        return  retVal;
    }
    int getDefenceBonus(Pokemon defender){
        int retVal = 0;
        switch (this){
            case Physical:
                retVal = defender.stats.defence.getCurVal();
                break;
            case Special:
                retVal =  defender.stats.spDefence.getCurVal();
                break;
            case None:
                retVal =   0;
                break;
        }
        return  retVal;
    }
}
