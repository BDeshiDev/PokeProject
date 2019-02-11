package com.company.RealTime;

import com.company.Pokemon.PokemonSaveData;
import com.company.Pokemon.Stats.Level;
import com.company.Settings;
import com.company.networking.BattleProtocol;
import com.company.networking.NetworkConnection;
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

public class TestAIClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader=new FXMLLoader(getClass().getResource("BattleScreen.fxml"));
        Parent root = loader.load();
        BattleScreenController controller=loader.getController();

        primaryStage.setTitle("ai gridTest ");
        primaryStage.setScene(new Scene(root,1200,800));
        primaryStage.show();

        TrainerData trainerData = new TrainerData("Gory",new PokemonSaveData("Pikachu", Level.maxLevel),new PokemonSaveData("Charizard", Level.maxLevel));
        TrainerData enemyData = null;

        Gson gson = new Gson();
        Socket socket = new Socket(InetAddress.getLocalHost(),Settings.realTimePort);
        System.out.println("in ");
        NetworkConnection nc = new NetworkConnection(socket);
        String readLine = nc.readFromConnection.readLine();

        if(readLine.startsWith(BattleProtocol.TrainerInfoRequest)){
            nc.writeToConnection.println(trainerData.toJsonData());
            enemyData = ServerRealTime.getTrainerData(nc,gson);
        }

        Grid grid = new Grid(controller.getPlayerGridParent());

        List<FighterData> playerParty = FighterData.convertTrainerData(trainerData);
        List<FighterData> enemyParty = FighterData.convertTrainerData(enemyData);

        NetworkedGridAI ai = new NetworkedGridAI(grid,true,nc ,controller.getPlayerDisplay(),controller.getTurnBar(),playerParty);

        BattlePlayer enemy = new BattlePlayer(new ImageView(),grid,false,controller.getEnemyDisplay(),enemyParty);
        GridReader reader = new GridReader(nc,ai,enemy);
        new Thread(reader).start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}