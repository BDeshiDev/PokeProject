package com.company;

import com.company.Exploration.PostBattleController;
import com.company.Pokemon.Pokemon;
import com.company.Utilities.Debug.Debugger;
import com.company.networking.NetworkedPlayer;
import com.company.networking.RealtimeNetworkScreen;
import com.company.networking.turnedNetWorkController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import pokemap.MergedExploration;

import java.io.File;
import java.io.IOException;

public class TitleController implements PokeScreen {
    private Stage curStage;
    private SaveData curSave;
    MediaPlayer mediaPlayer;


    @FXML
    private StackPane stack;

    @FXML
    private Label titleName;

    @FXML
    private AnchorPane anchor;

    @FXML
    private Button realBattleButton;

    @FXML
    private Pane scrollParent;

    @FXML
    private Button StartButton;

    @FXML
    private Button recordsButton;

    MergedExploration me = new MergedExploration(this);

    static Scene titleScene;

    turnedNetWorkController networkScree = new turnedNetWorkController(this);
    networkedPostBattle networkedPostBattle = new networkedPostBattle();
    RealtimeNetworkScreen realNetwork = new RealtimeNetworkScreen(this,networkedPostBattle);
    BattleRecordsController recorder = new BattleRecordsController();

    public TitleController()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartScreen.fxml"));
        loader.setController(this);
        try {
            Parent root =loader.load();
            titleScene =new Scene(root,Settings.windowWidth,Settings.windowLength);
            Debugger.out("title constructed");
        }catch (IOException ioe){
            System.out.println("couldn't create title screen");
            System.exit(-1);
        }
    }
    public void setCurStage(Stage curStage) {
        this.curStage = curStage;
    }

    @Override
    public void begin(Stage primaryStage, SaveData s, PokeScreen prevScreen) {
        curStage = primaryStage;
        this.curSave = s;
        String path = "src/Assets/titleBGM.mp3";

        realBattleButton.setOnAction(event -> {
            realNetwork.begin(curStage,curSave,this);
            mediaPlayer.stop();
        });
        recordsButton.setOnAction(event -> {
            recorder.begin(curStage,curSave,this);
        });

        //Instantiating Media class
        Media media = new Media(new File(path).toURI().toString());

        //Instantiating MediaPlayer class
        mediaPlayer = new MediaPlayer(media);

        //by setting this property to true, the audio will be played
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        curStage.setScene(titleScene);
    }

    @Override
    public void exitScreen() {
        System.exit(0);
    }

    public void Start(){
        me.begin(curStage,curSave,this);
        mediaPlayer.stop();
    }

    public void testBattle(){
        Pokemon poke1 = PokemonFactory.getBlastoise().toPokemon();
        Pokemon poke2 = PokemonFactory.getCharizard().toPokemon();
        Pokemon poke3 = PokemonFactory.getVenasaur().toPokemon();
        Pokemon poke4 = PokemonFactory.getPidgeot().toPokemon();

        pcTrainer ash = new pcTrainer("Ash",poke3,poke1);
        aiTrainer gary = new aiTrainer("Gary",poke2,poke4);

        BattleController battle = new BattleController();
        battle.begin(curStage,ash,gary,this,new PostBattleController(),curSave);
    }
}

