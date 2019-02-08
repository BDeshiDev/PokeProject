package com.company.RealTime;

import com.company.PokeTab;
import com.company.Settings;
import com.company.networking.NetworkConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.Socket;

public class TestAIClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader=new FXMLLoader(getClass().getResource("BattleScreen.fxml"));
        Parent root = loader.load();
        BattleScreenController controller=loader.getController();

        primaryStage.setTitle("ai gridTest ");
        primaryStage.setScene(new Scene(root, Settings.windowWidth,Settings.windowLength));
        primaryStage.show();

        Grid playerGrid = new Grid(controller.getPlayerGridParent(),false);
        Grid enemyGrid = new Grid(controller.getEnmeyGridParent(),true);

        Socket socket = new Socket(InetAddress.getLocalHost(),Settings.realTimePort);
        System.out.println("in ");
        NetworkConnection nc = new NetworkConnection(socket);
        NetworkedGridAI ai = new NetworkedGridAI(playerGrid,nc, "Assets/charizardOoverWorld.png",controller.getPlayerHpUI());

        BattlePlayer enemy = new BattlePlayer(new ImageView("Assets/CharzOverWorldleft.png"),enemyGrid,controller.getEnemyHpUI());
        GridReader reader = new GridReader(nc,ai,enemy);
        new Thread(reader).start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}