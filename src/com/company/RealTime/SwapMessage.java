package com.company.RealTime;

import com.company.networking.BattleProtocol;

public class SwapMessage {
    int swapperID;
    int idToSwapWith=-1;

    public SwapMessage(int swapperID) {
        this.swapperID = swapperID;
    }

    public SwapMessage(int swapperID, int idToSwapWith) {
        this.swapperID = swapperID;
        this.idToSwapWith = idToSwapWith;
    }

    public  static  String createSwapRequest(int swapperID){
        return BattleProtocol.createMessage(new SwapMessage(swapperID),BattleProtocol.SwapRequestHeader);
    }

    public  static  String createSwapEventMessage(int swapperID,int idToSwapWith){
        return BattleProtocol.createMessage(new SwapMessage(swapperID,idToSwapWith),BattleProtocol.SwapEventHeader);
    }

    @Override
    public String toString() {
        return "SwapMessage{" +
                "swapperID=" + swapperID +
                ", idToSwapWith=" + idToSwapWith +
                '}';
    }
}
