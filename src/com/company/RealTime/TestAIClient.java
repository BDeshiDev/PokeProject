package com.company.RealTime;

import com.company.Settings;
import com.company.networking.NetworkConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TestAIClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader=new FXMLLoader(getClass().getResource("BattleScreen.fxml"));
        Parent root = loader.load();
        BattleScreenController controller=loader.getController();

        primaryStage.setTitle("ai gridTest ");
        primaryStage.setScene(new Scene(root,1200,800));
        primaryStage.show();

        Grid playerGrid = new Grid(controller.getPlayerGridParent(),false);
        Grid enemyGrid = new Grid(controller.getEnmeyGridParent(),true);

        Socket socket = new Socket(InetAddress.getLocalHost(),Settings.realTimePort);
        System.out.println("in ");
        NetworkConnection nc = new NetworkConnection(socket);

        List<FighterData> party1 = new ArrayList<>();
        party1.add(FighterData.getDummy1());
        party1.add(FighterData.getDummy2());
        List<FighterData> party2 = new ArrayList<>();
        party2.add(FighterData.getDummy2());
        party2.add(FighterData.getDummy1());//#FIX FIX FIX IT ASAP read this from socket

        NetworkedGridAI ai = new NetworkedGridAI(playerGrid,nc, "Assets/PokemonImages/pika3d.png",controller.getPlayerDisplay(),party1);

        BattlePlayer enemy = new BattlePlayer(new ImageView("Assets/PokemonImages/charz3d.png"),enemyGrid,controller.getEnemyDisplay(),party2);
        GridReader reader = new GridReader(nc,ai,enemy);
        new Thread(reader).start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}