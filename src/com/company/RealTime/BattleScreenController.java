package com.company.RealTime;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
    private Label hpLabel1;

    @FXML
    private Pane PlayerGridParent;

    @FXML
    private Label hpLabel;

    @FXML
    private Button GiveUpButton;

    @FXML
    private Label NameLabel1;

    @FXML
    private Label lvLabel;

    @FXML
    private Label lvLabel1;

    @FXML
    private Button SwapButton;

    private  HpUI playerHpUI;
    private  HpUI enemyHpUI;
    public  void initialize(){
        playerHpUI = new HpUI(hpBar,hpLabel);
        enemyHpUI = new HpUI(hpBar1,hpLabel1);
    }

    public void setPlayerUI(String name,int lv,int curHp,int maxHp){
        updateUI(NameLabel,name,lvLabel,lv,playerHpUI,curHp,maxHp);
    }

    public void setEnemyUI(String name,int lv,int curHp,int maxHp){
        updateUI(NameLabel1,name,lvLabel1,lv,enemyHpUI,curHp,maxHp);
    }
    private void updateUI(Label _nameLabel ,String name,Label _lvLabel,int lv, HpUI hpUI,int curHp,int maxHp){
        _nameLabel.setText(name);
        _lvLabel.setText(Integer.toString(lv));
        hpUI.update(curHp,maxHp);
    }

    public void toggleChoiceBox(boolean shouldBeOn){
        ChoiceBoxPane.setVisible(shouldBeOn);
        ChoiceBoxPane.setDisable(!shouldBeOn);
    }

    public Button getGiveUpButton() {
        return GiveUpButton;
    }

    public Button getSwapButton() {
        return SwapButton;
    }

    public Pane getEnmeyGridParent() {
        return enmeyGridParent;
    }

    public Pane getPlayerGridParent() {
        return PlayerGridParent;
    }

    public HpUI getPlayerHpUI() {
        return playerHpUI;
    }

    public HpUI getEnemyHpUI() {
        return enemyHpUI;
    }
}
