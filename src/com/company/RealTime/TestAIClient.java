package com.company.RealTime;

import com.company.Settings;
import com.company.networking.NetworkConnection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.Socket;

public class TestAIClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane gridParent = new Pane();
        Grid playerGrid = new Grid(gridParent);
        Pane enemyGridParent = new Pane();
        Grid enemyGrid = new Grid(enemyGridParent);

        HBox root  = new HBox(gridParent,enemyGridParent);
        Scene scene=new Scene(root, Settings.windowWidth,Settings.windowLength);

        primaryStage.setTitle("ai gridTest");
        primaryStage.setScene(scene);
        primaryStage.show();
        Socket socket = new Socket(InetAddress.getLocalHost(),Settings.realTimePort);
        System.out.println("in ");
        NetworkConnection nc = new NetworkConnection(socket);
        NetworkedGridAI ai = new NetworkedGridAI(playerGrid,nc,"Assets/MapImages/heroleft.png");

        BattlePlayer enemy = new BattlePlayer(new ImageView("Assets/MapImages/heroleft.png"),enemyGrid);
        GridReader reader = new GridReader(nc,playerGrid,ai,enemy);
        new Thread(reader).start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}