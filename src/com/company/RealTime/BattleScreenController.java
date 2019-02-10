package com.company.RealTime;

import com.company.BattleDisplayController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class BattleScreenController {
    @FXML
    private Label NameLabel;

    @FXML
    private Pane enmeyGridParent;

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

    BattleDisplayController playerDisplay;
    BattleDisplayController enemyDisplay;
    public  void initialize(){
        playerDisplay = new BattleDisplayController(NameLabel,lvLabel,hpBar,hpLabel);
        enemyDisplay = new BattleDisplayController(NameLabel1,lvLabel1,hpBar1,hpLabel1);

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
                            // assume MyDataType.getSomeProperty() returns a string
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

    public Pane getEnmeyGridParent() {
        return enmeyGridParent;
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


}
