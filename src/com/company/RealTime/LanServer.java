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
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedDeque;

public class LanServer {

    static  String hostName = "192.168.1.1";
    static boolean isLocal = true;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(Settings.realTimePort, 10,  InetAddress.getByName(hostName));
            while (true){
                ServerRealTime.createServers(serverSocket);
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
            System.out.println("could not open server socket");
        }
    }



}
