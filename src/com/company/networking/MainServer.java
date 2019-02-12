package com.company.networking;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class MainServer {
    public static void main(String[] args) {
        try {
            ServerThread.createServer(true);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("couldn't host server");
        }
    }
}

