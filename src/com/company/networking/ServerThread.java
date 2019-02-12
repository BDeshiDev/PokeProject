package com.company.networking;
import java.net.InetAddress;
import java.net.ServerSocket;


public class ServerThread implements  Runnable {
    NetworkConnection p1,p2;
    ServerSocket serverSocket;

    static final int portToUse = 50015;

    public ServerThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public ServerThread() {
        try {
            serverSocket = new ServerSocket(portToUse, 10,InetAddress.getByName("172.26.11.207"));
        }catch (Exception e){
            System.out.println("unable to create server");
        }
    }

    @Override
    public void run() {
        try {
            while(true) {
                p1 = new NetworkConnection(serverSocket.accept());
                p2 = new NetworkConnection(serverSocket.accept());

                p1.writeToConnection.println(BattleProtocol.TrainerInfoRequest);
                p2.writeToConnection.println(BattleProtocol.TrainerInfoRequest);

                String s1, s2;
                while (true) {
                    s1 = p1.readFromConnection.readLine();
                    s2 = p2.readFromConnection.readLine();
                    System.out.println("s1 " + s1 + " s2 " + s2);
                    p1.writeToConnection.println(s2);
                    p2.writeToConnection.println(s1);

                    if (s1.equals(BattleProtocol.WinSignal) || s1.equals(BattleProtocol.LoseSignal) ||
                            s2.equals(BattleProtocol.WinSignal) || s2.equals(BattleProtocol.LoseSignal)) {
                        System.out.println("server stop");
                        break;
                    }
                }
            }
            //System.out.println("closing socket");
           // serverSocket.close();
        }catch (Exception e){
            System.out.println("unable to get required players");
        }
    }
}

