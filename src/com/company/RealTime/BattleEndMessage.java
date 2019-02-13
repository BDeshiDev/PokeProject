package com.company.RealTime;

import com.company.networking.BattleProtocol;
import com.company.networking.JsonDataAble;


public class BattleEndMessage implements JsonDataAble {

    final int userID;
    String battleResult;

    public BattleEndMessage(int userID, String battleResult) {
        this.userID = userID;
        this.battleResult = battleResult;
    }



    @Override
    public String toJsonData() {
        return BattleProtocol.createMessage(this,BattleProtocol.battleEndHeader);
    }

}
