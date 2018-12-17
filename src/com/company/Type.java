package com.company;

import javax.swing.text.StyledEditorKit;

enum Type{
    Fire,Water,Electric,Ice,Rock,Ground,Grass,Bug,Dragon,
    Flying,Psychic,Dark,Fighting,Normal,Ghost,Steel,Poison,
    None;//dummy for single type mons.Always put this at the end since that's how the array is structured

    //choose between this or switch case
    static double[][] weaknessTable ={//horizontal row = defending type, vertical = attack type
                //fire,wate,elec,ice ,rock,grou,gras,bug,drag,Fly,Psyc,Dark,Figh,Norm,Ghos,Stee,Pois,none,
        /*fire  */{.5,   .5,   1,  2,  .5,   1,   2,   2,  .5,   1,  1,   1,   1,  1,  1,   1,   1,   1},
        /*water */{ 2,   .5,   1,  1,   2,   2,  .5,   1,  .5,   1,  1,   1,   1,  1,  1,   1,   1,   1},
        /*elec  */{ 1,    2,  .5,  1,   1,   0,  .5,   1,  .5,   2,  1,   1,   1,  1,  1,   1,   1,   1},
        /*Ice   */{.5,   .5,   1, .5,   1,   2,   2,   1,   2,   2,  1,   1,   1,  1,  1,  .5,   1,   1},
        /*Rock  */{ 2,    1,   1,  2,   1,  .5,   1,   2,   1,   2,  1,   1,   2,  1,  1,  .5,   1,   1},
        /*ground*/{ 2,    1,   2,  1,   2,   1,  .5,  .5,   1,   0,  1,   1,   1,  1,  1,   2,   2,   1},
        /*Grass */{.5,    1,   1, .5,   2,   2,  .5,  .5,  .5,  .5,  1,   1,   1,  1,  1,  .5,  .5,   1},
    };

    //incomplete, replace with a completed static array or a spreadsheet or just complete this
    public Double getModifier(Type other){
        double retVal = 1;
        switch(this){
            case Fire:
                if(other == Water || other == Rock || other == Ground)
                    retVal = 2;
                else if(other == Grass || other == Fire || other == Bug)
                    retVal = .5;
                break;
            case Water:
                if(other == Grass || other == Electric )
                    retVal = 2;
                else if(other == Ice || other == Water || other == Fire)
                    retVal = .5;
                break;
            case Grass:
                if(other == Fire || other == Bug || other == Poison || other  == Flying || other == Ice)
                    retVal = 2;
                else if(other == Grass || other == Electric || other == Ground)
                    retVal = .5;
                break;
            case Electric:
                if(other == Ground)
                    retVal = 2;
                else if(other == Electric || other == Flying || other == Steel)
                    retVal = .5;
                break;
            case Poison:
                if(other == Ground || other == Psychic)
                    retVal = 2;
                else if(other == Bug || other == Grass || other == Fighting || other == Poison)
                    retVal = .5;
                break;
            case Flying:
                if(other == Electric )
                    retVal = 2;
                else if(other == Bug || other == Grass || other == Fighting )
                    retVal = .5;
                else if(other == Ground)
                    retVal = 0;
                break;
            default:
                System.out.println("I'm too lazy to implement " + this);
                retVal = 1;
                break;
        }
        return retVal;
    }



}
