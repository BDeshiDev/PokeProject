package com.company.networking;

import java.net.ServerSocket;

public class MainServer {
    public static void main(String[] args) {
        try {
            ServerThread serverThread = new ServerThread();
            Thread st =new Thread(serverThread);
            st.start();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("couldn't host server");
        }
    }
}
