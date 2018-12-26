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
        /*Bug   */{.5,    1,   1,  1,   2,   1,   2,   1,   1,  .5,  2,   2,   1,  1, .5,  .5,  .5,   1},
        /*Dragon*/{ 1,    1,   1,  1,   1,   1,   1,   1,   2,   1,  1,   1,   1,  1,  1,  .5,   1,   1},
        /*flying*/{ 1,    1,  .5,  1,  .5,   1,   2,   2,   1,   1,  1,   1,   2,  1,  1,  .5,   1,   1},
        /*Psych */{ 1,    1,   1,  1,   1,   1,   1,   1,   1,   1, .5,   1,   2,  1,  1,  .5,   2,   1},
        /*Dark  */{ 1,    1,   1,  1,   1,   1,   1,   1,   1,   1,  2,  .5,  .5,  1,  2,  .5,   1,   1},
        /*fight */{ 1,    1,   1,  2,   2,   1,   1,  .5,   1,  .5, .5,   2,   1,  2,  0,   2,  .5,   1},
        /*Normal*/{ 1,    1,   1,  1,  .5,   1,   1,   1,   1,   1,  1,   1,   1,  1,  0,  .5,   1,   1},
        /*Ghost */{ 1,    1,   1,  1,   1,   1,   1,   1,   1,   1,  2,  .5,   1,  0,  2,   1,   1,   1},
        /*Steel */{.5,   .5,  .5,  2,   2,   1,   1,   1,   1,   1,  1,   1,   1,  1,  1,  .5,   1,   1},
        /*Poison*/{ 1,    1,   1,  1,  .5,  .5,   2,   2,   1,   1,  1,   1,   2,  1, .5,   0,  .5,   1},
        /*None  */{ 1,    1,   1,  1,   1,   1,   1,   1,   1,   1,  1,   1,   1,  1,  1,   1,   1,   1},
    };

    public Double getModifier(Type other) {
        //System.out.println(other+" against "+ this);
        return weaknessTable[other.ordinal()][this.ordinal()];
    }

    //old method,In case we want to compare
    /*
    public Double getModifier(Type other){
        double retVal = 1;
        switch(this){
            case Fire:
                if(other == Water || other == Rock || other == Ground)
                    retVal = 2;
                else if(other == Grass || other == Fire || other == Bug)
                    retVal = .5;
                break;
            default:
                System.out.println("I'm too lazy to implement " + this);
                retVal = 1;
                break;
        }
        return retVal;
    }
    */


}
