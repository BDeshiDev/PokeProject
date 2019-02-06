package com.company.Pokemon.Moves;

import com.company.Pokemon.Pokemon;

/*
* used to determine which stat gives bonus power
* */
public enum DamageType {
    Physical,Special,None;
    int getAttackBonus(Pokemon user){
        int retVal = 0;
        switch (this){
            case Physical:
                retVal = user.getAtt();
                break;
            case Special:
                retVal =  user.getSpAtt();
                break;
            case None:
                retVal =   1;
                break;
        }
        return  retVal;
    }
    int getDefenceBonus(Pokemon defender){
        int retVal = 0;
        switch (this){
            case Physical:
                retVal = defender.getDef();
                break;
            case Special:
                retVal =  defender.getSpDef();
                break;
            case None:
                retVal =  1;
                break;
        }
        return  retVal;
    }
}
