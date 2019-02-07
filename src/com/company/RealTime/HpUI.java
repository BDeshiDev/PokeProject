package com.company.RealTime;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class HpUI {
    ProgressBar hpBar;
    Label hpLabel;

    public HpUI(ProgressBar hpBar, Label hpLabel) {
        this.hpBar = hpBar;
        this.hpLabel = hpLabel;
    }

    public void update(int curHp, int maxHp){
        hpLabel.setText("hp " + curHp + " / " + maxHp);
        hpBar.setProgress((double)curHp/ maxHp);
    }
}
