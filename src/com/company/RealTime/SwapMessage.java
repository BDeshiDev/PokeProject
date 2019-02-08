package com.company.RealTime;

import com.company.networking.BattleProtocol;
import com.company.networking.JsonDataAble;

public class SwapMessage implements JsonDataAble {
    int swapperID;
    int idToSwapWith=-1;
    boolean isCancellable = false;

    public SwapMessage(int swapperID, boolean isCancellable) {
        this.swapperID = swapperID;
        this.isCancellable = isCancellable;
    }

    public SwapMessage(int swapperID, int idToSwapWith, boolean isCancellable) {
        this.swapperID = swapperID;
        this.idToSwapWith = idToSwapWith;
        this.isCancellable = isCancellable;
    }

    public  static  String createSwapRequest(int swapperID,boolean isCancellable){
        return BattleProtocol.createMessage(new SwapMessage(swapperID,isCancellable),BattleProtocol.SwapRequestHeader);
    }

    public  static  String createSwapEventMessage(int swapperID,int idToSwapWith,boolean isCancellable){
        return BattleProtocol.createMessage(new SwapMessage(swapperID,idToSwapWith,isCancellable),BattleProtocol.SwapEventHeader);
    }

    @Override
    public String toString() {
        return "SwapMessage{" +
                "swapperID=" + swapperID +
                ", idToSwapWith=" + idToSwapWith +
                '}';
    }

    @Override
    public String toJsonData() {
        return BattleProtocol.createMessage(this,BattleProtocol.SwapRequestHeader);
    }
}
