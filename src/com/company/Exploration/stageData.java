package com.company.Exploration;

import com.company.aiTrainer;

class stageData{
   aiTrainer[] challengers;
   String stageName;
   //private stageData nextStage; add later

   public stageData(String stageName,aiTrainer... challengers) {
       this.challengers = challengers;
       this.stageName = stageName;
   }
}
