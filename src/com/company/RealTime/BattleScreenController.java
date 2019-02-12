package com.company.RealTime;

import com.company.BattleDisplayController;
import com.company.PokeScreen;
import com.company.SaveData;
import com.company.Settings;
import com.company.Utilities.Debug.Debugger;
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
import javafx.stage.Stage;
import javafx.util.Callback;

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
    private Pane gridViewParent;
    @FXML
    private VBox ChoiceBoxPane;

    @FXML
    private ListView<MoveCardData> CarcChoiceBox;

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
    private ListView<MoveCardData> selectedCardList;

    @FXML
    private ProgressBar TurnBar;

    @FXML
    private GridPane playerGridPane;

    BattleDisplayController playerDisplay;
    BattleDisplayController enemyDisplay;

    public Scene battleScene;
    Stage primaryStage;
    SaveData s;
    PokeScreen prevScreen;

    public  void initialize(){
        playerDisplay = new BattleDisplayController(NameLabel,lvLabel,hpBar,hpLabel,iconPreview);
        enemyDisplay = new BattleDisplayController(NameLabel1,lvLabel1,hpBar1,hpLabel1,iconPreview1);

        Callback<ListView<MoveCardData>, ListCell<MoveCardData>> cellFactory = new Callback<ListView<MoveCardData>, ListCell<MoveCardData>>() {
            @Override
            public ListCell<MoveCardData> call(ListView<MoveCardData> lv) {
                return new ListCell<MoveCardData>() {
                    @Override
                    public void updateItem(MoveCardData item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            ImageView iconImage = new ImageView(new Image(item.iconName));
                            //iconImage.setScaleX(.25);
                            //iconImage.setScaleY(.25);
                            VBox cardBox = new VBox(0,iconImage,new Label(item.attackName));
                            //cardBox.setPrefSize(40,400);
                            setGraphic( cardBox);
                        }
                    }
                };
            }
        };
        CarcChoiceBox.setFixedCellSize(200);
        CarcChoiceBox.setCellFactory(cellFactory);
        selectedCardList.setCellFactory(cellFactory);

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

        primaryStage.setTitle("Real time battle");
        primaryStage.setScene(battleScene);
        primaryStage.show();
    }

    @Override
    public void exitScreen() {
        if(prevScreen == null)
            System.exit(-5);
        prevScreen.begin(primaryStage, s,null);
    }

    public ListView<MoveCardData> getCarcChoiceBox() {
        return CarcChoiceBox;
    }

    public ListView<MoveCardData> getSelectedCardList() {
        return selectedCardList;
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

    public GridPane getPlayerGridPane() {
        return playerGridPane;
    }


}
