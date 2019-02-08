package com.company.RealTime;

import com.company.Settings;
import com.company.networking.NetworkConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.Socket;

public class TestPlayerClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("BattleScreen.fxml"));
        Parent root = loader.load();
        BattleScreenController controller=loader.getController();

        primaryStage.setTitle("Player gridTest ");
        Scene s = new Scene(root, Settings.windowWidth,Settings.windowLength);
        primaryStage.setScene(s);
        primaryStage.show();

        Grid playerGrid = new Grid(controller.getPlayerGridParent(),false);
        Grid enemyGrid = new Grid(controller.getEnmeyGridParent(),true);

        Socket socket = new Socket(InetAddress.getLocalHost(),Settings.realTimePort);
        System.out.println("in ");
        NetworkConnection nc = new NetworkConnection(socket);
        NetworkedGridPlayer player  = new NetworkedGridPlayer(new ImageView("Assets/charizardOoverWorld.png"),playerGrid,s,controller.getPlayerHpUI(),new NetworkConnection(socket),controller);
        BattlePlayer enemy = new BattlePlayer(new ImageView("Assets/CharzOverWorldleft.png"),enemyGrid,controller.getEnemyHpUI());
        GridReader reader = new GridReader(nc,player,enemy);
        new Thread(reader).start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}



