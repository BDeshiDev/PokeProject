package com.company.networking;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;


public class ServerThread implements  Runnable {
    NetworkConnection p1,p2;
    ServerSocket serverSocket;

    static final int portToUse = 53015;
    static  String wifiAddress = "192.168.1.1";

    public ServerThread(boolean isLocal , NetworkConnection p1,NetworkConnection p2) throws IOException {
        this.p1 = p1;
        this.p2 = p2;
    }

    public static void createServer(boolean isLocal ,ServerSocket ss) throws  IOException {
        NetworkConnection p1 = new NetworkConnection(ss.accept());
        NetworkConnection p2 = new NetworkConnection(ss.accept());
        ServerThread serverThread = new ServerThread(true,p1,p2);
        Thread st = new Thread(serverThread);
        st.start();
        ServerSocket serverSocket = new ServerSocket(portToUse, 10,isLocal? InetAddress.getByName("127.0.0.1"):InetAddress.getByName(wifiAddress));
        System.out.println("server thread successfull");
    }

    public static void createServer(boolean isLocal) throws  IOException {
        ServerSocket serverSocket = new ServerSocket(portToUse, 10,isLocal? InetAddress.getByName("127.0.0.1"):InetAddress.getByName(wifiAddress));
        createServer(isLocal,serverSocket);
    }

    @Override
    public void run() {
        try {
            System.out.println("server awaits");
            p1.writeToConnection.println(BattleProtocol.TrainerInfoRequest);
            p2.writeToConnection.println(BattleProtocol.TrainerInfoRequest);

            String s1, s2;
            while (true) {
                s1 = p1.readFromConnection.readLine();
                if(s1 == null)
                    throw new  IOException();
                s2 = p2.readFromConnection.readLine();

                if(s2 == null)
                    throw new  IOException();

                System.out.println("s1 " + s1 + " s2 " + s2);
                p1.writeToConnection.println(s2);
                p2.writeToConnection.println(s1);

                if (s1.equals(BattleProtocol.WinSignal) || s1.equals(BattleProtocol.LoseSignal) ||
                        s2.equals(BattleProtocol.WinSignal) || s2.equals(BattleProtocol.LoseSignal)) {
                    System.out.println("server stop");
                    break;
                }
            }

        }catch (IOException e){
            System.out.println("unable to get required players");
        }finally {
            try {
                p1.close();
                p2.close();
            }catch (IOException ioe){
                System.out.println("server close fail");
            }
        }
    }
}


