package com.company.RealTime;

import com.company.Exploration.PokemonStorage;
import com.company.Exploration.PokemonStorageController;
import com.company.Pokemon.Pokemon;
import com.company.PokemonFactory;
import com.company.Settings;
import com.company.networking.NetworkConnection;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class TestPlayerClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane gridParent = new Pane();
        Grid playerGrid = new Grid(gridParent);
        Pane enemyGridParent = new Pane();
        Grid enemyGrid = new Grid(enemyGridParent);

        HBox root  = new HBox(gridParent,enemyGridParent);
        Scene scene=new Scene(root, Settings.windowWidth,Settings.windowLength);

        primaryStage.setTitle("playerTest");
        primaryStage.setScene(scene);
        primaryStage.show();

        Socket socket = new Socket(InetAddress.getLocalHost(),Settings.realTimePort);
        System.out.println("in ");
        NetworkConnection nc = new NetworkConnection(socket);
        NetworkedGridPlayer player  = new NetworkedGridPlayer(new ImageView("Assets/MapImages/heroright.png"),playerGrid,scene,new NetworkConnection(socket));
        BattlePlayer enemy = new BattlePlayer(new ImageView("Assets/MapImages/heroleft.png"),enemyGrid);
        GridReader reader = new GridReader(nc,playerGrid,player,enemy);
        new Thread(reader).start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}



