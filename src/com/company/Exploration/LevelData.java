package com.company.Exploration;

import com.company.Pokemon.PokemonData;
import com.company.WildMon;
import com.company.aiTrainer;

import java.util.ArrayList;
import java.util.List;

class LevelData {
   List<aiTrainer> challengers = new ArrayList<>();
   List<PokemonData> possibleEncounters = new ArrayList<>();
   String stageName;

   public LevelData(String stageName, List<aiTrainer> challengers, List<PokemonData> possibleEncounters) {
       this.challengers = challengers;
       this.possibleEncounters = possibleEncounters;
       this.stageName = stageName;
   }
}
