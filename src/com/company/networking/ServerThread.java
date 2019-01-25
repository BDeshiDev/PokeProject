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
            serverSocket = new ServerSocket(portToUse, 10, InetAddress.getLocalHost());
        }catch (Exception e){
            System.out.println("unable to create server");
        }
    }

    @Override
    public void run() {
        try {
            p1 = new NetworkConnection(serverSocket.accept());
            p2 = new NetworkConnection(serverSocket.accept());

            p1.writeToConnection.println("ready to start battle");
            p2.writeToConnection.println("ready to start battle");

            String s1,s2;
            do{
                s1 = p1.readFromConnection.readLine();
                s2= p2.readFromConnection.readLine();
                p1.writeToConnection.println(s2);
                p2.writeToConnection.println(s1);
            }while(!s1.equals("button 3") || !s2.equals("button 3"));
            p1.writeToConnection.println("WIN");
            p2.writeToConnection.println("WIN");
        }catch (Exception e){
            System.out.println("unable to get required players");
        }
    }
}

