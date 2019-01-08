package com.company.Exploration;

import com.company.Trainer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class ExplorationController {
    @FXML
    private Button StatusButton;

    @FXML
    private Button fightButton;

    @FXML
    private Label RemainingTrainerCountLabel;

    @FXML
    private Label NextChallengerNameLabel;

    @FXML
    private Label StageTitleLabel;

    @FXML
    private Button nextStageButton;

    public void loadStage(stageData stageToLoad){
        RemainingTrainerCountLabel.setText(Integer.toString(stageToLoad.challengers.length));
        StageTitleLabel.setText(stageToLoad.stageName);
    }


}

class stageData{
    Trainer[] challengers;
    String stageName;
    //private stageData nextStage; add later

    public stageData(String stageName,Trainer... challengers) {
        this.challengers = challengers;
        this.stageName = stageName;
    }
}
