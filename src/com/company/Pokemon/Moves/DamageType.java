package com.company.Pokemon.Moves;

import com.company.Pokemon.Pokemon;
import com.company.RealTime.FighterData;

/*
* used to determine which stat gives bonus power
* */
public enum DamageType {
    Physical,Special,None;
    public int getAttackBonus(Pokemon user){
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

    public int getAttackBonus(FighterData user){
        int retVal = 0;
        switch (this){
            case Physical:
                retVal = user.att;
                break;
            case Special:
                retVal =  user.spAtt;
                break;
            case None:
                retVal =   1;
                break;
        }
        return  retVal;
    }
    public int getDefenceBonus(FighterData defender){
        int retVal = 0;
        switch (this){
            case Physical:
                retVal = defender.def;
                break;
            case Special:
                retVal =  defender.spDef;
                break;
            case None:
                retVal =  1;
                break;
        }
        return  retVal;
    }
    public int getDefenceBonus(Pokemon defender){
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
