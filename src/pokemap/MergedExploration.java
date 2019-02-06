package pokemap;

import com.company.*;
import com.company.Exploration.*;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class MergedExploration extends Application implements  PokeScreen{
    String mapLocation = "C:\\Users\\USER\\IdeaProjects\\PokeProject\\src\\pokemap\\ForestMap.txt";
    Map forestMap;
    String explorationBGM = "src/Assets/mapBGM.mp3";

    PlayerEntity player;
    MediaPlayer mediaPlayer;

    boolean run,up,down,left,right;
    boolean testingAgainstTrainers = false;
    Directions direction;

    int runSpeed = 3;

    SaveData currentSave;
    Stage primaryStage;

    PokeScreen prevScreen;
    PostBattleController postBattleController = new PostBattleController();

    @Override
    public void exitScreen() {
        System.out.println("exiting exploration screen");
        prevScreen.begin(primaryStage,currentSave,this);// essentially infinite
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        begin(primaryStage,SaveData.newGameData(),this);
    }

    public void begin(Stage primaryStage,SaveData saveData,PokeScreen prevScreen){
        this.primaryStage = primaryStage;
        this.currentSave = saveData;
        this.prevScreen = prevScreen;

        System.out.println(currentSave.mapName);
        forestMap =new Map(new File(currentSave.mapName));
        player =new PlayerEntity(currentSave, new ImageView());
        player.resetProbablity();

        Group group=forestMap.setMap();
        group.getChildren().add(player.getImageOfEntity());
        PerspectiveCamera camera=new PerspectiveCamera(true);

        camera.layoutXProperty().bind(player.getImageOfEntity().layoutXProperty());
        camera.layoutYProperty().bind(player.getImageOfEntity().layoutYProperty());
        camera.setTranslateZ(-300);
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setFieldOfView(35);

        Scene scene=new Scene(group, Settings.windowWidth,
                Settings.windowLength, Color.FORESTGREEN);
        scene.setCamera(camera);

        addListeners(scene);
        timer.start();
        Media media=new Media(new File(explorationBGM).toURI().toString());
        mediaPlayer=new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        primaryStage.setTitle("Pokemon RGB");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void stopExploration(){
        timer.stop();
        removeListeners(primaryStage.getScene());
        run=left=right=up=down=false;
        mediaPlayer.stop();
    }

    public void addListeners(Scene scene){
        scene.setOnKeyPressed(pressEvent);
        scene.setOnKeyReleased(releaseEvent);
    }

    public void removeListeners(Scene scene){
        scene.setOnKeyPressed(null);
        scene.setOnKeyReleased(null);
    }

    AnimationTimer timer=new AnimationTimer() {
        @Override
        public void handle(long now) {
            int dx=0,dy=0;
            if (up) dy-=1;
            if (down) dy += 1;
            if (left) dx -= 1;
            if (right) dx += 1;
            if (run) {
                dx *= runSpeed;
                dy *= runSpeed;
            }
            player.Shift(forestMap,dx,dy,direction);
            if(player.gettingPokemonProbability(forestMap) >=1){
                player.resetProbablity();
                save();
                if(testingAgainstTrainers){
                    int randIndex = new Random().nextInt(forestMap.trainerDatas.length);
                    aiTrainer enemy = new aiTrainer(forestMap.trainerDatas[randIndex]);
                    System.out.println("fighting " + enemy.name);
                    startBattle(enemy);
                }else {
                    int randIndex = new Random().nextInt(forestMap.possibleEncounters.length);
                    WildMon wildmon = new WildMon(PokemonFactory.getMonByName(forestMap.possibleEncounters[randIndex]));
                    if(wildmon == null) {
                        System.out.println("invlaid wild encounter");
                    }else {
                        System.out.println("fighting " + wildmon.getName());
                        startBattle(wildmon);
                    }
                }
            }
        }
    };
    EventHandler<KeyEvent> pressEvent = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()){
                case R: run=true;  break;
                case UP: {up=true;direction=Directions.UP; break;}
                case DOWN:{ down=true;direction=Directions.DOWN; break;}
                case LEFT:{ left=true;direction=Directions.LEFT; break;}
                case RIGHT:{ right=true;direction=Directions.RIGHT; break;}
            }

        }
    };
    EventHandler<KeyEvent> releaseEvent = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()){
                case R: run=false; break;
                case UP: up=false; break;
                case DOWN: down=false; break;
                case LEFT: left=false; break;
                case RIGHT: right=false; break;
                case T: testingAgainstTrainers = !testingAgainstTrainers; break;
                case S: save();break;
                case L:
                    System.out.println("loading");
                    load();
                    break;
                case U:
                    exitScreen();
                    break;
            }
        }
    };


    public void save(){
        currentSave.updateSaveLocally(player.getEntityPosition(),player.getPcTrainer());
        System.out.println("auto saved");
        Gson gson = new Gson();
        try {
            JsonWriter writer = new JsonWriter(new FileWriter("SaveData.txt"));
            writer.setIndent("  ");
            gson.toJson(currentSave, SaveData.class, writer);
            writer.flush();
            writer.close();
        }catch (IOException ioe){
            System.out.println("save failed");
        }
    }

    public void load(){
        Gson gson = new Gson();
        try {
            currentSave = gson.fromJson(new FileReader("SaveData.txt"),SaveData.class);
            stopExploration();
            begin(primaryStage,currentSave,prevScreen);
        }catch (IOException ioe){
            System.out.println("load failed");
        }
    }

    public void startBattle(aiTrainer enemy){
        stopExploration();
        BattleController bc =new BattleController();
        bc.begin(primaryStage,player.getPcTrainer(),enemy,this,postBattleController,currentSave);
    }
    public void startBattle(WildMon enemy){
        stopExploration();
        save();
        BattleController bc =new BattleController();
        bc.begin(primaryStage,player.getPcTrainer(),enemy,this,postBattleController,currentSave);
    }

}
