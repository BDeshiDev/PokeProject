package com.company;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class  Battle{
    Trainer t1,t2;
    int curTurn;

    boolean canRun = true;
    boolean canUseItems = true;

    public Battle (Trainer _t1, Trainer _t2){
        t1=_t1;
        t2 =_t2;
        curTurn = 0;
    }

    public boolean isOver(){
        return t1.canFight() && t2.canFight();
    }

    public void resolve(){
        System.out.println(t1.name + "  VS  " +  t2.name + "!!!");

        Scanner cin = new Scanner(System.in);
        while(isOver()){
            ArrayList<Attack> attacksList  = new ArrayList<>();
            attacksList.add(t1.takeTurn(cin,t2.getCurPokemon()));
            attacksList.add(t2.takeTurn(cin,t1.getCurPokemon()));

            Collections.sort(attacksList);
            for (Attack a :attacksList) {
                a.execute();
            }
        }
        //calculate results
        if(!t1.canFight() && !t2.canFight())
            System.out.println("result: Draw");
        else if(!t2.canFight())
            System.out.println("Result: " + t1.name + " wins");
        else
            System.out.println("Result: " + t2.name + " wins");
    }

}
