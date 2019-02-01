package com.company.networking;

import com.company.*;
import com.company.Pokemon.Moves.MoveFactory;
import com.company.Pokemon.Pokemon;
import com.company.Utilities.Animation.AnimationFactory;

public class AttackCommandData implements  JsonDataAble {
    String moveName;
    public  AttackCommandData(AttackCommand ac){
        this.moveName = ac.move.getName();
    }

    public AttackCommand toAttackCommand(Pokemon user, BattleSlot slot){
        return  new AttackCommand(user, MoveFactory.getMoveByName(moveName),slot);
    }

    public String toJsonData(){
        return  BattleProtocol.createMessage(this,BattleProtocol.AttackCommandHeader);
    }
}

