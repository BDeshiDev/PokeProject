package com.company;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TitleController {
    private Stage curStage;

    public TitleController() {
        System.out.println("title constructed");
    }

    public void setCurStage(Stage curStage) {
        this.curStage = curStage;
    }

    public void Start(){
        Pokemon poke1 = PokemonFactory.getBlastoise();
        Pokemon poke2 = PokemonFactory.getCharizard();
        Pokemon poke3 = PokemonFactory.getVenasaur();
        Pokemon poke4 = PokemonFactory.getPidgeot();

        pcTrainer ash = new pcTrainer("Ash",poke1,poke3);
        aiTrainer gary = new aiTrainer("Gary",poke2,poke4);

        BattleController battle = new BattleController(ash,gary);
        battle.begin(curStage);
    }
}
