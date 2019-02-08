package com.company.RealTime;

import com.company.networking.BattleProtocol;
import com.company.networking.JsonDataAble;

public class koMessage implements JsonDataAble {
    int koId;//who got ko'd

    public koMessage(int koId) {
        this.koId = koId;
    }

    @Override
    public String toJsonData() {
        return BattleProtocol.createMessage(this,BattleProtocol.koMessage);
    }
}
