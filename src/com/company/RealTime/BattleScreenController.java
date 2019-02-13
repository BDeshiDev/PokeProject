package com.company.RealTime;

import com.company.BattleDisplayController;
import com.company.PokeScreen;
import com.company.SaveData;
import com.company.Settings;
import com.company.Utilities.Debug.Debugger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BattleScreenController implements PokeScreen {
    @FXML
    private Label NameLabel;

    @FXML
    private ImageView iconPreview1;

    @FXML
    private ImageView iconPreview;

    @FXML
    private VBox gridViewParent;
    @FXML
    private VBox ChoiceBoxPane;


    @FXML
    private ProgressBar hpBar;

    @FXML
    private ProgressBar hpBar1;

    @FXML
    private Label hpLabel1;

    @FXML
    private Pane PlayerGridParent;

    @FXML
    private FlowPane SwapParentPane;

    @FXML
    private Label hpLabel;

    @FXML
    private Label NameLabel1;

    @FXML
    private Button exitButton;

    @FXML
    private ImageView BackgroundImage;

    @FXML
    private Label lvLabel;

    @FXML
    private Label lvLabel1;

    @FXML
    private ImageView loadingImage;

    @FXML
    private ProgressBar TurnBar;

    @FXML
    private GridPane playerGridPane;


    @FXML
    private HBox cardChoiceParent;

    @FXML
    private HBox slectedCardParent;

    public HBox getCardChoiceParent() {
        return cardChoiceParent;
    }

    public HBox getSlectedCardParent() {
        return slectedCardParent;
    }

    BattleDisplayController playerDisplay;
    BattleDisplayController enemyDisplay;

    public Scene battleScene;
    Stage primaryStage;
    SaveData s;
    PokeScreen prevScreen;
    MediaPlayer mediaPlayer;

    public  void initialize(){
        playerDisplay = new BattleDisplayController(NameLabel,lvLabel,hpBar,hpLabel,iconPreview);
        enemyDisplay = new BattleDisplayController(NameLabel1,lvLabel1,hpBar1,hpLabel1,iconPreview1);
        iconPreview.setScaleX(-1);

        lvLabel.setText("");//we won't have levels
        lvLabel1.setText("");
    }

    public BattleScreenController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BattleScreen.fxml"));
        loader.setController(this);
        try {
            Parent root =loader.load();
            battleScene =new Scene(root, Settings.windowWidth,Settings.windowLength);
            Debugger.out("battle constructed");
        }catch (IOException ioe){
            System.out.println("couldn't create battle screen");
            System.exit(-1);
        }
    }

    @Override
    public void begin(Stage primaryStage, SaveData s, PokeScreen prevScreen) {
        this.primaryStage = primaryStage;
        this.prevScreen = prevScreen;
        this.s=s;

        Media media=new Media(new File("src/Assets/battleBGM.mp3").toURI().toString());
        mediaPlayer=new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        primaryStage.setTitle("Real time battle");
        primaryStage.setScene(battleScene);


        primaryStage.show();
    }

    @Override
    public void exitScreen() {
        mediaPlayer.stop();
        if(prevScreen == null)
            System.exit(-5);
        prevScreen.begin(primaryStage, s,null);
    }

    public void toggleChoiceBox(boolean shouldBeOn){
        ChoiceBoxPane.setVisible(shouldBeOn);
        ChoiceBoxPane.setDisable(!shouldBeOn);
    }

    public ProgressBar getTurnBar() {
        return TurnBar;
    }


    public Button getExitButton() {
        return exitButton;
    }

    public FlowPane getSwapParentPane() {
        return SwapParentPane;
    }

    public Pane getGridViewParent() {
        return gridViewParent;
    }

    public Pane getPlayerGridParent() {
        return PlayerGridParent;
    }

    public BattleDisplayController getPlayerDisplay() {
        return playerDisplay;
    }

    public BattleDisplayController getEnemyDisplay() {
        return enemyDisplay;
    }

    public ImageView getLoadingImage() {
        return loadingImage;
    }

    public GridPane getPlayerGridPane() {
        return playerGridPane;
    }


}
