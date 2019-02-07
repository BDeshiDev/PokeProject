package com.company.RealTime;

import com.company.Settings;
import com.company.networking.BattleProtocol;
import com.company.networking.NetworkConnection;
import javafx.animation.AnimationTimer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
                ServerReader serverThread1 = new ServerReader(p1,p2,1);
                Thread st = new Thread(serverThread1);
                st.start();
                ServerReader serverThread2 = new ServerReader(p2,p1,2);
                Thread st2 = new Thread(serverThread2);
                st2.start();
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
            System.out.println("could not open server socket");
        }
    }
}

class ServerSimulationLoop extends AnimationTimer{
    ConcurrentLinkedDeque<String> clientInputQueue;
    NetworkConnection p1Connection , p2Connection;

    public ServerSimulationLoop(ConcurrentLinkedDeque<String> clientInputQueue, NetworkConnection p1Connection, NetworkConnection p2Connection) {
        this.clientInputQueue = clientInputQueue;
        this.p1Connection = p1Connection;
        this.p2Connection = p2Connection;
    }

    @Override
    public void handle(long now) {
        if(!clientInputQueue.isEmpty()){
            String newMessage = clientInputQueue.pop();
            p1Connection.writeToConnection.println(newMessage);
            p2Connection.writeToConnection.println(newMessage);
        }
    }
}
class ServerReader implements  Runnable{
    NetworkConnection p1,p2;
    int id;

    public ServerReader(NetworkConnection p1, NetworkConnection p2, int id) {
        this.p1 = p1;
        this.p2 = p2;
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


                p1.writeToConnection.println(readLine);
                p2.writeToConnection.println(readLine);
            }
            //System.out.println("closing socket");
            // serverSocket.close();
        }catch (Exception e){
            System.out.println("unable to get read in readThread#"+id);
        }
    }
}
