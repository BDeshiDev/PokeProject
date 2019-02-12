package com.company.RealTime;

import com.company.Settings;
import com.company.networking.BattleProtocol;
import com.company.networking.NetworkConnection;
import com.company.networking.TrainerData;
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
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerRealTime {

    static  String hostName = "192.168.1.1";
    static boolean isLocal = true;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(Settings.realTimePort, 10, isLocal?InetAddress.getByName("127.0.0.1"):InetAddress.getByName(hostName));
            while (true){
                createServers(serverSocket);
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
            System.out.println("could not open server socket");
        }
    }


    public static  void createServers(ServerSocket ss) throws IOException{
        Socket newClient= ss.accept();
        System.out.println("accepted 1");
        Socket newClient2= ss.accept();
        System.out.println("accepted 2");
        NetworkConnection p1 = new NetworkConnection(newClient);
        NetworkConnection p2 = new NetworkConnection(newClient2);
        //requst and transfer info
        p1.writeToConnection.println(BattleProtocol.TrainerInfoRequest);
        p2.writeToConnection.println(BattleProtocol.TrainerInfoRequest);
        Gson gson = new Gson();
        TrainerData t1Info = getTrainerData(p1,gson);
        TrainerData t2Info = getTrainerData(p2,gson);

        p1.writeToConnection.println(t2Info.toJsonData());
        p2.writeToConnection.println(t1Info.toJsonData());

        p1.writeToConnection.println(BattleProtocol.createMessage(new setIdMessage(1,2),BattleProtocol.setIdMessageHeader));
        p2.writeToConnection.println(BattleProtocol.createMessage(new setIdMessage(2,1),BattleProtocol.setIdMessageHeader));

        ConcurrentLinkedDeque<String> inputQueue = new ConcurrentLinkedDeque<>();
        Timer simTimer = new Timer("simulation timer");
        ServerSimulationLoop ssLoop = new ServerSimulationLoop(inputQueue,p1,p2,simTimer,FighterData.convertTrainerData(t1Info),FighterData.convertTrainerData(t2Info));

        ServerReader serverThread1 = new ServerReader(p1,p2,inputQueue,1);
        Thread st = new Thread(serverThread1);
        st.start();
        ServerReader serverThread2 = new ServerReader(p2,p1,inputQueue,2);
        Thread st2 = new Thread(serverThread2);
        st2.start();
    }

    public static  TrainerData getTrainerData(NetworkConnection sender,Gson gson){
        TrainerData retVal = null;
        try {
            String readLine = sender.readFromConnection.readLine();
            if(readLine.startsWith(BattleProtocol.TrainerInfoHeader)){
                String jsonToParse = readLine.substring(BattleProtocol.TrainerInfoHeader.length());
                retVal = gson.fromJson(jsonToParse, TrainerData.class);
            }
        }catch (IOException ioe){
            System.out.println("couldn't get trainer data");
        }
        return  retVal;
    }

}

class ServerSimulationLoop extends TimerTask {
    ConcurrentLinkedDeque<String> clientInputQueue;
    private NetworkConnection p1Connection , p2Connection;
    private Grid simulatedGrid;
    private BattlePlayer p1,p2;
    private Timer timer;

    Gson gson = new Gson();

    private List<AttackDamageTimer> attacksToCheck  = new ArrayList<AttackDamageTimer>();
    List<BattlePlayer> waitList = new ArrayList<>();//list of players we are waiting for
    List<TurnChargeMessage> messages = new ArrayList<>();;

    public static int tickDelay = 20;//in ms


    public ServerSimulationLoop(ConcurrentLinkedDeque<String> clientInputQueue, NetworkConnection p1Connection, NetworkConnection p2Connection,Timer timer,List<FighterData> leftParty,List<FighterData> rightParty) {
        this.clientInputQueue = clientInputQueue;
        this.p1Connection = p1Connection;
        this.p2Connection = p2Connection;
        this.simulatedGrid = new Grid(null);
        this.timer = timer;

        timer.scheduleAtFixedRate(this,0,ServerSimulationLoop.tickDelay);

        p1 = new BattlePlayer(null,simulatedGrid,true,null,leftParty);
        p1.setId(1);
        p2 = new BattlePlayer(null,simulatedGrid,false,null,rightParty);
        p2.setId(2);

        p1.init();
        p2.init();
    }

    @Override
    public void run() {
        if(!waitList.isEmpty()){//we're waiting for someone to tell us to stop pausing
            System.out.println("Simulation: waiting for swap event ");
            if (!clientInputQueue.isEmpty()) {
                String newMessage = clientInputQueue.pop();
                if (newMessage.startsWith(BattleProtocol.SwapEventHeader)) {
                    String jsonToParse = newMessage.substring(BattleProtocol.SwapEventHeader.length());
                    SwapMessage sm = gson.fromJson(jsonToParse, SwapMessage.class);
                    if(sm!= null) {
                        BattlePlayer swapper = getPlayerFromTargetId(sm.swapperID);
                        if(swapper != null) {
                            if(sm.idToSwapWith != -1){//essentially if the swap hasn't failed
                                swapper.handleSwap(sm.idToSwapWith);
                                swapper.resetTurn(1);
                                broadcastMessage(newMessage);
                            }
                            waitList.remove(swapper);
                        }
                    }
                }
            }
            //if we have cleared the wait list then tell everyone to resume
            if(waitList.isEmpty()){
                broadcastMessage(BattleProtocol.ResumeOrderHeader);
            }
        }else {
            UpdateTurns();
            if (!clientInputQueue.isEmpty()) {
                String newMessage = clientInputQueue.pop();
                String messageToSend = null;
                if (newMessage.startsWith(BattleProtocol.moveMessageHeader)) {
                    //in cas of valid move update positions on our end and send them over
                    String jsonToParse = newMessage.substring(BattleProtocol.moveMessageHeader.length());
                    moveMessage mm = gson.fromJson(jsonToParse, moveMessage.class);
                    BattlePlayer moveTarget = getPlayerFromTargetId(mm.moveTargetId);
                    if (moveTarget != null) {
                        int dx = mm.dx,dy= mm.dy;
                        if(!moveTarget.isOnLeft)
                            dx = -dx;
                        if (simulatedGrid.isMoveValid(moveTarget.curtile,dx,dy,moveTarget.isOnLeft)) {
                            simulateMove(moveTarget, mm.dx, mm.dy);
                            messageToSend = newMessage;
                        }else{
                            System.out.println("move invalid :"+ mm.moveTargetId + " : from:"  +moveTarget.curtile.x +","+ moveTarget.curtile.y+ " Dir:" +dx + "," +dy);
                        }
                    } else {
                        System.out.println("Server simulation:: wrong move id : " + mm.moveTargetId);
                    }
                } else if (newMessage.startsWith(BattleProtocol.attackMessageHeader)) {
                    String jsonToParse = newMessage.substring(BattleProtocol.attackMessageHeader.length());
                    AttackMessage am = gson.fromJson(jsonToParse, AttackMessage.class);
                    simulateAttack(am);
                    messageToSend = newMessage;
                } else if (newMessage.startsWith(BattleProtocol.SwapRequestHeader)) {
                    System.out.println("Simulation: somebody wants to swap now");
                    String jsonToParse = newMessage.substring(BattleProtocol.SwapRequestHeader.length());
                    SwapMessage sm = gson.fromJson(jsonToParse, SwapMessage.class);
                    if (sm != null) {
                        NetworkConnection swapOrderReceiver = null;
                        if (sm.swapperID == p1.getId()) {
                            swapOrderReceiver = p1Connection;
                            waitList.add(p1);
                        } else if (sm.swapperID == p2.getId()) {
                            swapOrderReceiver = p2Connection;
                            waitList.add(p2);
                        }
                        if (swapOrderReceiver != null) {
                            broadcastMessage(BattleProtocol.PauseOrderMessge);
                            //tell both to pause and tell the swapper to start swapping
                            swapOrderReceiver.writeToConnection.println(newMessage);
                        } else {
                            System.out.println("swapper id mismatch");
                        }
                    }
                }else if(newMessage.startsWith(BattleProtocol.koMessage)){
                        System.out.println("Simulation: somebody got ko'd");
                        String jsonToParse = newMessage.substring(BattleProtocol.koMessage.length());
                        koMessage km = gson.fromJson(jsonToParse, koMessage.class);
                        if (km != null) {
                            NetworkConnection swapOrderReceiver = null;
                            if (km.koId == p1.getId()) {
                                if(p1.canFight()) {
                                    swapOrderReceiver = p1Connection;
                                    waitList.add(p1);
                                }else{
                                    p1Connection.writeToConnection.println(BattleProtocol.LoseSignal);
                                    p2Connection.writeToConnection.println(BattleProtocol.WinSignal);
                                    stopSimulation();
                                }
                            } else if (km.koId == p2.getId()) {
                                if(p2.canFight()) {
                                    swapOrderReceiver = p2Connection;
                                    waitList.add(p2);
                                }else{
                                    p2Connection.writeToConnection.println(BattleProtocol.LoseSignal);
                                    p1Connection.writeToConnection.println(BattleProtocol.WinSignal);
                                    stopSimulation();
                                }
                            }
                            swapOrderReceiver.writeToConnection.println(SwapMessage.createSwapRequest(km.koId,false));
                            broadcastMessage(BattleProtocol.PauseOrderMessge);
                        }
                }else if (newMessage.startsWith(BattleProtocol.turnConfirmHeader)) {
                    String jsonToParse = newMessage.substring(BattleProtocol.turnConfirmHeader.length());
                    TurnConfirmMessage tcm = gson.fromJson(jsonToParse, TurnConfirmMessage.class);
                    if(tcm!= null) {
                        BattlePlayer targetPlayer = getPlayerFromTargetId(tcm.id);
                        if(targetPlayer != null){
                            System.out.println("removing " + targetPlayer.curFighter.name + " remaining " + waitList.size());
                            if(tcm.selectedMoves.size()>0)
                                targetPlayer.resetTurn(tcm.selectedMoves.get(tcm.selectedMoves.size() -1).chooseCost);
                        }
                    }
                }else if(newMessage.startsWith(BattleProtocol.battleFailSignal)){
                    broadcastMessage(BattleProtocol.battleFailSignal);
                    stopSimulation();
                } else
                    System.out.println("swap message parse fail");
                if (messageToSend != null) {
                    broadcastMessage(newMessage);
                }
            }
            if (!attacksToCheck.isEmpty()) {
                List<DamageMessage> damageUpdates = new ArrayList<>();
                for (int i = attacksToCheck.size() - 1; i >= 0; i--) {
                    AttackDamageTimer adt = attacksToCheck.get(i);
                    adt.applyDamage(p1, p2, tickDelay, damageUpdates,this);//warning this checks tiles by refrence so implementing tile.equals is a bad idea for now
                    if (adt.shouldEnd())
                        attacksToCheck.remove(i);
                }
                if (!damageUpdates.isEmpty()) {
                    System.out.println("Simulation: somebody took damage");
                    String messages = BattleProtocol.createMessage(damageUpdates, BattleProtocol.DamageHeader);
                    p1Connection.writeToConnection.println(messages);
                    p2Connection.writeToConnection.println(messages);
                }
            }
        }
    }

    public void updateTurn(BattlePlayer player, double amount){
        System.out.println("updating by anount " + amount);
        player.increaseTurn(amount);
        messages.add(new TurnChargeMessage(player.getTurnCharge(),player.getId(),player.readyForTurn()));
    }
    private void UpdateTurns() {
        if(p1 == null || p2 == null)
            return;// why is this even happening

        if(!p1.readyForTurn()) {
            updateTurn(p1,p1.calculateChargeFromTicks(tickDelay));
        }
        if(!p2.readyForTurn()) {
            updateTurn(p2,p2.calculateChargeFromTicks(tickDelay));
        }
        String json =TurnChargeMessage.convertUpdateToMessage(messages);
        //System.out.println(json);
        broadcastMessage(json);
        messages.clear();
    }

    public void stopSimulation(){
        timer.cancel();
        timer.purge();
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
        for (Tile[] tArr :simulatedGrid.grid) {
            for(Tile t: tArr){
                System.out.print(t +(t == p1.curtile?"p1":"  ") + (t == p2.curtile?"P2":"" ));
            }
            System.out.println();
        }
    }

    public void simulateMove(BattlePlayer bp,int dx, int dy){
        bp.setMove(dx,dy);
        //printSimulation();
    }

    public void simulateAttack(AttackMessage am){
        if(am.userID == p1.getId()){
            am.toMoveCard().addDamageTimers(simulatedGrid,p1,attacksToCheck,am);
        }else if(am.userID == p2.getId()){
            am.toMoveCard().addDamageTimers(simulatedGrid,p2,attacksToCheck,am);
        }else{
            System.out.println("simulation: invalid attack userData id " + am.userID);
        }

    }

    public void broadcastMessage(String m){
        p1Connection.writeToConnection.println(m);
        p2Connection.writeToConnection.println(m);
    }

}
class ServerReader implements  Runnable{
    NetworkConnection p1,p2;
    ConcurrentLinkedDeque<String> inputQueue;
    int id;

    AtomicBoolean hasReadFailed;

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
            // System.out.println("closing socket");
            // serverSocket.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("unable to get read in readThread#"+id);
        }
    }
}
