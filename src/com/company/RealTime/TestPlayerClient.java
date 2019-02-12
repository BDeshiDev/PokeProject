package com.company.RealTime;

import com.company.Pokemon.PokemonSaveData;
import com.company.Pokemon.Stats.Level;
import com.company.SaveData;
import com.company.Settings;
import com.company.TitleController;
import com.company.networkedPostBattle;
import com.company.networking.BattleProtocol;
import com.company.networking.NetworkConnection;
import com.company.networking.RealtimeNetworkScreen;
import com.company.networking.TrainerData;
import com.google.gson.Gson;
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

public class TestPlayerClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BattleScreenController controller = new BattleScreenController();
        controller.begin(primaryStage, SaveData.newGameData(),new TitleController());

        TrainerData trainerData = new TrainerData("Ash",new PokemonSaveData("Charizard", Level.maxLevel),new PokemonSaveData("Pikachu", Level.maxLevel));
        TrainerData enemyData = null;
        Grid grid = new Grid(controller.getPlayerGridParent());
        Gson gson = new Gson();
        Socket socket = new Socket("127.0.0.1",Settings.realTimePort);
        System.out.println("in ");
        NetworkConnection nc = new NetworkConnection(socket);
        String readLine = nc.readFromConnection.readLine();

        if(readLine.startsWith(BattleProtocol.TrainerInfoRequest)){
            nc.writeToConnection.println(trainerData.toJsonData());
            enemyData = ServerRealTime.getTrainerData(nc,gson);
        }

        List<FighterData> playerParty = FighterData.convertTrainerData(trainerData);
        List<FighterData> enemyParty = FighterData.convertTrainerData(enemyData);

        NetworkedGridPlayer player  = new NetworkedGridPlayer(new ImageView(),grid,true,controller.battleScene,controller.getPlayerDisplay(),playerParty,new NetworkConnection(socket),controller);
        BattlePlayer enemy = new BattlePlayer(new ImageView(),grid,false,controller.getEnemyDisplay(),enemyParty);
        GridReader reader = new GridReader(nc,player,enemy,new RealtimeNetworkScreen(new TitleController(), new networkedPostBattle()));
        new Thread(reader).start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}



