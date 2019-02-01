package com.company.networking;
import java.io.*;
import java.net.Socket;


public class NetworkConnection {
    Socket socket;
    PrintWriter writeToConnection;
    BufferedReader readFromConnection;

    public NetworkConnection(Socket socket) {
        this.socket = socket;
        try {
            this.writeToConnection = new PrintWriter(socket.getOutputStream(),true);
            this.readFromConnection = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException ioe){
            System.out.println("failed to setup connection");
        }
    }
}
