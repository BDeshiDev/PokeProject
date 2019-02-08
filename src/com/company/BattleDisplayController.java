package com.company;

import com.company.RealTime.FighterData;
import com.company.RealTime.HpUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class BattleDisplayController {

    @FXML
    private Label NameLabel;

    @FXML
    private Label lvLabel;

    @FXML
    private ProgressBar hpBar;

    @FXML
    private Label hpLabel;

    public BattleDisplayController() { }

    public BattleDisplayController(Label NameLabel,Label lvLabel, ProgressBar hpBar, Label hpLabel) {
        this.hpBar = hpBar;
        this.hpLabel = hpLabel;
        this.NameLabel = NameLabel;
        this.lvLabel = lvLabel;
    }

    public void update(String name,int curHp, int maxHp){
        update(curHp,maxHp);
        NameLabel.setText(name);;
    }

    public void update(String name,int level,int curHp, int maxHp){
        update(name, curHp, maxHp);
        lvLabel.setText(Integer.toString(level));
    }
    public void update(FighterData fighterData) {
        update(fighterData.curHp,fighterData.maxHp);
    }
    public void update(int curHp, int maxHp){
        hpLabel.setText("hp " + curHp + " / " + maxHp);
        hpBar.setProgress((double)curHp/ maxHp);
    }
}
