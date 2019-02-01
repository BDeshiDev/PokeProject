package pokemap;

import com.company.*;
import com.company.Exploration.*;
import com.company.Pokemon.Pokemon;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
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
    PlayerEntity player;

    boolean run,up,down,left,right;
    boolean testingAgainstTrainers = false;
    Directions direction;

    int runSpeed = 3;

    SaveData currentSave;
    Stage primaryStage;

    PokeScreen prevScreen;

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

        forestMap =new Map(new File(currentSave.mapName));
        player =new PlayerEntity(currentSave, new ImageView());
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

        primaryStage.setTitle("Player on Rush.");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void stopExploration(){
        timer.stop();
        removeListeners(primaryStage.getScene());
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
        //bc.begin(primaryStage,player.getPcTrainer(),enemy);
    }
    public void startBattle(WildMon enemy){
        stopExploration();
        BattleController bc =new BattleController();
        //bc.begin(primaryStage,player.getPcTrainer(),enemy);
    }
    /*
    private aiTrainer curChallenger;
    private BattleController curBattle = new BattleController();
    private PostBattleController xpScreenController = new PostBattleController();
    private PokemonStorageController storageController;

    private Stage primaryStage;
    private Scene myScene;

    private boolean wantsToBattle;
    private boolean wantsToSeeStatus;
    private boolean wantsToFightWildMon;
    private boolean wantsToGoToNextLevel;

    ExplorationState explorationState;



    public void fightWildMons(){
        wantsToFightWildMon = true;
    }


    public void startWildMonBattle(){
        System.out.println("getting next wild mon...");
        int monIndex = new Random().nextInt(possibleEncounters.size());
        WildMon newWildmon = new WildMon( possibleEncounters.get(monIndex).toPokemon());
        newWildmon.heal();
        curBattle.begin(primaryStage,player,newWildmon);
    }

    public void onExplorationComplete(){
        System.out.println("No stages left to load");
        mainLoop.stop();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreditsScene.fxml"));
            Parent root = loader.load();
            CreditsController cc= loader.getController();
            cc.init(primaryStage);
            primaryStage.setScene(new Scene(root, Settings.windowWidth, Settings.windowLength));
            primaryStage.setTitle("Ending Screen");
            primaryStage.show();
        }catch(IOException ioe){
            System.out.println("failed to load credit screen");
            System.exit(-1);//#TODO move to a creditScreen class
        }
    }

    class  ExplorationLoop extends  AnimationTimer{
        @Override
        public void handle(long now) {
            switch (explorationState){
                case Exploring:
                    if(wantsToBattle){
                        wantsToBattle = false;
                        explorationState = ExplorationController.ExplorationState.WaitingForBattleEnd;
                        getNextChallenger();
                    }else if(wantsToFightWildMon){
                        wantsToFightWildMon = false;
                        explorationState = ExplorationController.ExplorationState.WaitingForBattleEnd;
                        startWildMonBattle();
                    } else if(wantsToGoToNextLevel){
                        wantsToGoToNextLevel = false;
                        loadLevelFromStack();
                    }else if(wantsToSeeStatus){
                        wantsToSeeStatus= false;
                        explorationState = ExplorationController.ExplorationState.waitingForStatusScreen;
                        loadStatusScreen();
                    }
                    break;
                case WaitingForBattleEnd:
                    if( curBattle.isComplete()){
                        BattleResult newResult = curBattle.getResult();
                        if(newResult.playerWon) {
                            System.out.println("player won");
                        }
                        else{
                            if(curChallenger != null)
                                remainingChallengers.push(curChallenger);//if we don't win we fight the same trainer again
                            System.out.println("player lost");
                        }
                        System.out.println(newResult);
                        explorationState = ExplorationController.ExplorationState.WaitingForExpScreen;
                        System.out.println("entering post battle screen");
                        xpScreenController.begin(primaryStage,newResult,player);
                    }
                    break;
                case WaitingForExpScreen:
                    if(xpScreenController.readyToExit){
                        updateChallengerText();
                        player.heal();
                        if(remainingChallengers.isEmpty()) {
                            nextStageButton.setDisable(false);
                            fightButton.setDisable(true);
                        }
                        primaryStage.setScene(myScene);
                        primaryStage.setTitle("Exploration Test");
                        explorationState = ExplorationController.ExplorationState.Exploring;
                    }
                    break;
                case waitingForStatusScreen:
                    if(storageController.readyToExit()){
                        primaryStage.setScene(myScene);
                        primaryStage.setTitle("Exploration Test");
                        explorationState = ExplorationController.ExplorationState.Exploring;
                    }
                    break;
            }
        }

        @Override
        public void stop() {
            super.stop();
            System.out.println("exiting exploration loop");
        }
    }
    ExplorationController.ExplorationLoop mainLoop = new ExplorationController.ExplorationLoop();


    public void init(){
        explorationState = ExplorationController.ExplorationState.Exploring;

        this.player = player;
        this.primaryStage = primaryStage;
        this.myScene = sceneToUse;

        primaryStage.setScene(myScene);
        primaryStage.setTitle("Exploration Test");

        Collections.addAll(levelsLeft,stagesToLoad);
        loadLevelFromStack();
        nextStageButton.setDisable(true);

        mainLoop.start();
    }


    public void startNextBattle(){
        wantsToBattle =true;
    }
    public void goToNextStage(){
        wantsToGoToNextLevel = true;
    }

    public void goToStatus(){
        wantsToSeeStatus = true;
    }

    public void loadStatusScreen(){
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("PokemonStorageScreen.fxml"));
            Scene scene=new Scene(new Pane(), Settings.windowWidth,Settings.windowLength);
            scene.setRoot(loader.load());
            storageController=loader.getController();
            storageController.begin(primaryStage,player.getPcTrainer().getParty());

            primaryStage.setTitle("Storage");
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch(IOException ioe){
            ioe.printStackTrace();
            System.out.println("failed to load storage Screen");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {

        System.out.println("help us");
        launch(args);
    }*/
}
