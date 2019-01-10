package com.company.Exploration;

import com.company.aiTrainer;

import java.util.ArrayList;
import java.util.List;

class StageData {
   List<aiTrainer> challengers = new ArrayList<>();
   String stageName;
   //private StageData nextStage; add later

   public StageData(String stageName, aiTrainer... challengers) {
       for (aiTrainer ait:challengers) {
           this.challengers.add(ait);
       }
       this.stageName = stageName;
   }
}
