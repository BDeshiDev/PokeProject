package com.company.networking;

import com.company.SwapCommand;
import com.company.Trainer;

public class swapCommandData implements JsonDataAble{
    int swapNo;

    public swapCommandData(int swapNo) {
        this.swapNo = swapNo;
    }

    @Override
    public String toJsonData() {
        return BattleProtocol.createMessage(this,BattleProtocol.SwapCommandHeader);
    }

    public SwapCommand toSwapCommand(Trainer swapper){
        return new SwapCommand(swapper,swapNo);
    }
}
