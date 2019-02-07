package com.company.RealTime;

import com.company.Settings;
import com.company.networking.BattleProtocol;
import com.company.networking.NetworkConnection;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ServerRealTime {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(Settings.realTimePort, 10, InetAddress.getLocalHost());
            while (true){
                Socket newClient= serverSocket.accept();
                System.out.println("accepted 1");
                Socket newClient2= serverSocket.accept();
                System.out.println("accepted 2");
                NetworkConnection p1 = new NetworkConnection(newClient);
                NetworkConnection p2 = new NetworkConnection(newClient2);
                p1.writeToConnection.println(BattleProtocol.createMessage(new setIdMessage(1,2),BattleProtocol.setIdMessageHeader));
                p2.writeToConnection.println(BattleProtocol.createMessage(new setIdMessage(2,1),BattleProtocol.setIdMessageHeader));

                ConcurrentLinkedDeque<String> inputQueue = new ConcurrentLinkedDeque<>();
                ServerSimulationLoop ssLoop = new ServerSimulationLoop(inputQueue,p1,p2);
                Timer simTimer =  new Timer("simulation timer");
                simTimer.scheduleAtFixedRate(ssLoop,0,ServerSimulationLoop.tickDelay);

                ServerReader serverThread1 = new ServerReader(p1,p2,inputQueue,1);
                Thread st = new Thread(serverThread1);
                st.start();
                ServerReader serverThread2 = new ServerReader(p2,p1,inputQueue,2);
                Thread st2 = new Thread(serverThread2);
                st2.start();
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
            System.out.println("could not open server socket");
        }
    }
}

class ServerSimulationLoop extends TimerTask {
    ConcurrentLinkedDeque<String> clientInputQueue;
    private NetworkConnection p1Connection , p2Connection;
    private Grid simulatedLeftGrid,simulatedRightGrid;
    private BattlePlayer p1,p2;

    Gson gson = new Gson();

    private List<AttackDamageTimer> attacksToCheck  = new ArrayList<AttackDamageTimer>();

    public static int tickDelay = 20;//in ms


    public ServerSimulationLoop(ConcurrentLinkedDeque<String> clientInputQueue, NetworkConnection p1Connection, NetworkConnection p2Connection) {
        this.clientInputQueue = clientInputQueue;
        this.p1Connection = p1Connection;
        this.p2Connection = p2Connection;
        this.simulatedLeftGrid = new Grid(null,false);
        this.simulatedRightGrid = new Grid(null,true);
        p1 = new BattlePlayer(null,simulatedLeftGrid,null);
        p1.setId(1);
        p2 = new BattlePlayer(null,simulatedRightGrid,null);
        p2.setId(2);
    }

    @Override
    public void run() {
        if(!clientInputQueue.isEmpty()){
            String newMessage = clientInputQueue.pop();
            String messageToSend = null;
            if(newMessage.startsWith(BattleProtocol.moveMessageHeader)){
                //in cas of valid move update positions on our end and send them over
                String jsonToParse = newMessage.substring(BattleProtocol.moveMessageHeader.length());
                moveMessage mm = gson.fromJson(jsonToParse,moveMessage.class);
                BattlePlayer moveTarget = getPlayerFromTargetId(mm.moveTargetId);
                if(moveTarget != null){
                    if(simulatedLeftGrid.isMoveValid(moveTarget.curtile,mm.dx,mm.dy)){
                        simulateMove(moveTarget,mm.dx,mm.dy);
                        messageToSend = newMessage;
                    }
                }else{
                    System.out.println("Server simulation:: wrong move id : " + mm.moveTargetId);
                }
            }else if(newMessage.startsWith(BattleProtocol.attackMessageHeader)){
                String jsonToParse = newMessage.substring(BattleProtocol.attackMessageHeader.length());
                AttackMessage am = gson.fromJson(jsonToParse,AttackMessage.class);
                simulateAttack(am);
                messageToSend = newMessage;
            }
            if(messageToSend != null) {
                p1Connection.writeToConnection.println(newMessage);
                p2Connection.writeToConnection.println(newMessage);
            }
        }
        if(!attacksToCheck.isEmpty()){
            List<DamageMessage> damageUpdates = new ArrayList<>();
            for(int i = attacksToCheck.size()-1;i>=0;i--){
                AttackDamageTimer adt = attacksToCheck.get(i);
                adt.applyDamage(p1,p2,tickDelay,damageUpdates);
                if(adt.shouldEnd())
                    attacksToCheck.remove(i);
            }
            if(!damageUpdates.isEmpty()){
                System.out.println("Simulation: somebody took damage");
                String messages = BattleProtocol.createMessage(damageUpdates,BattleProtocol.DamageHeader);
                p1Connection.writeToConnection.println(messages);
                p2Connection.writeToConnection.println(messages);
            }
        }
    }

    public BattlePlayer getPlayerFromTargetId(int targetId){
        if(targetId == p1.getId()){
            return p1;
        }else if(targetId == p2.getId()){
            return p2;
        }
        return null;
    }
    public void printSimulation() {
        for (Tile[] tArr :simulatedLeftGrid.grid) {
            for(Tile t: tArr){
                System.out.print(t +(t == p1.curtile?"p1":"  ") );
            }
            System.out.println();
        }
        for (Tile[] tArr :simulatedRightGrid.grid) {
            for(Tile t: tArr){
                System.out.println(t +(t == p2.curtile?"p2":"  ") );
            }
        }
    }

    public void simulateMove(BattlePlayer bp,int dx, int dy){
        bp.setMove(dx,dy);
        //printSimulation();
    }

    public void simulateAttack(AttackMessage am){
        if(am.userID == p1.getId()){
            am.addDamageTimers(simulatedLeftGrid,simulatedRightGrid,attacksToCheck);
        }else if(am.userID == p2.getId()){
            am.addDamageTimers(simulatedRightGrid,simulatedLeftGrid,attacksToCheck);
        }else{
            System.out.println("simulation: invalid attack user id " + am.userID);
        }

    }

}
class ServerReader implements  Runnable{
    NetworkConnection p1,p2;
    ConcurrentLinkedDeque<String> inputQueue;
    int id;

    public ServerReader(NetworkConnection p1, NetworkConnection p2, ConcurrentLinkedDeque<String> inputQueue, int id) {
        this.p1 = p1;
        this.p2 = p2;
        this.inputQueue = inputQueue;
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("new server game thread#"+id);
        try {
            while(true) {

                System.out.println("reading once");
                String readLine = p1.readFromConnection.readLine();
                System.out.println("s" + id + ": " + readLine);

                inputQueue.push(readLine);
            }
            //System.out.println("closing socket");
            // serverSocket.close();
        }catch (Exception e){
            System.out.println("unable to get read in readThread#"+id);
        }
    }
}
