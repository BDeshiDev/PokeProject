package com.company;

import com.company.RealTime.FighterData;
import com.company.RealTime.HpUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import  javafx.scene.image.ImageView;
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

    private ImageView iconPreview;

    public BattleDisplayController() { }

    public BattleDisplayController(Label NameLabel,Label lvLabel, ProgressBar hpBar, Label hpLabel,ImageView iconPreview) {
        this.hpBar = hpBar;
        this.hpLabel = hpLabel;
        this.NameLabel = NameLabel;
        this.lvLabel = lvLabel;
        this.iconPreview = iconPreview;
    }

    public void update(String name,int curHp, int maxHp){
        update(curHp,maxHp);
        NameLabel.setText(name);
    }

    public void update(String name,int level,int curHp, int maxHp){
        update(name, curHp, maxHp);
        lvLabel.setText(Integer.toString(level));
    }
    public void update(FighterData fighterData) {
        update(fighterData.name,fighterData.curHp,fighterData.maxHp);
        iconPreview.setImage(new Image(fighterData.icon));
    }
    public void update(int curHp, int maxHp){
        hpLabel.setText("hp " + curHp + " / " + maxHp);
        hpBar.setProgress((double)curHp/ maxHp);
    }
}
