package com.company.RealTime;

import com.company.BattleDisplayController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class BattleScreenController {

    @FXML
    private Label NameLabel;

    @FXML
    private Pane enmeyGridParent;

    @FXML
    private VBox ChoiceBoxPane;

    @FXML
    private ProgressBar hpBar;

    @FXML
    private ProgressBar hpBar1;

    @FXML
    private FlowPane MoveCardView;

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
    private HBox SelectedMoveBox;

    BattleDisplayController playerDisplay;
    BattleDisplayController enemyDisplay;
    public  void initialize(){
        playerDisplay = new BattleDisplayController(NameLabel,lvLabel,hpBar,hpLabel);
        enemyDisplay = new BattleDisplayController(NameLabel1,lvLabel1,hpBar1,hpLabel1);
        lvLabel.setText("");//we won't have levels
        lvLabel1.setText("");
    }


    public void toggleChoiceBox(boolean shouldBeOn){
        ChoiceBoxPane.setVisible(shouldBeOn);
        ChoiceBoxPane.setDisable(!shouldBeOn);
    }

    public HBox getSelectedMoveBox() {
        return SelectedMoveBox;
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

    public FlowPane getMoveCardView() {
        return MoveCardView;
    }
}
