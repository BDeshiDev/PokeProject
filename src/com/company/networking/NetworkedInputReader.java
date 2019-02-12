package com.company.networking;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;

class NetworkedInputReader implements  Runnable{
    private BufferedReader reader;
    private NetworkedEnemy readingEnemy;


    public NetworkedInputReader(BufferedReader reader, NetworkedEnemy readingEnemy) {
        this.reader = reader;
        this.readingEnemy = readingEnemy;
    }


    @Override
    public void run() {
        Gson gson = new Gson();
            try {
                while(!readingEnemy.canStopReading){
                    String readLine = reader.readLine();
                    System.out.println("networker reads: " +readLine);
                    if(readLine.startsWith(BattleProtocol.AttackCommandHeader)){
                        String jsonToRead = readLine.substring(BattleProtocol.AttackCommandHeader.length());
                        AttackCommandData acd = gson.fromJson(jsonToRead,AttackCommandData.class);
                        readingEnemy.setSelectedCommand(acd);
                    }else if(readLine.startsWith(BattleProtocol.SwapCommandHeader)){
                        String jsonToRead = readLine.substring(BattleProtocol.SwapCommandHeader.length());
                        swapCommandData scd = gson.fromJson(jsonToRead,swapCommandData.class);
                        readingEnemy.setSelectedCommand(scd);
                    }else if (readLine.startsWith(BattleProtocol.TurnEndOkay)){
                        readingEnemy.okayToEndTurn = true;
                    }else{
                        System.out.println("wrong message from networked opponent");
                        //readingEnemy.readFailed = true;
                }}
            }catch(IOException | NullPointerException e ){
                e.printStackTrace();
                readingEnemy.readFailed = true;
                System.out.println("Failed to read enemy input from server");
            }

        System.out.println("terminating enemy Read thread");
    }
}
