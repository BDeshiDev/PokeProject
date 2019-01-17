package com.company.Exploration;

import com.company.Pokemon.Pokemon;
import com.company.Settings;
import com.company.Utilities.Debug.Debugger;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import java.util.List;

public class PokemonStorageController {
    @FXML
    private HBox partyPane;

    @FXML
    private FlowPane storagePane;

    @FXML
    private Button exitButton;

    public final int monsPerRow = 6;
    public List<Pokemon> party;

    boolean wantsToExit;

    public void begin(Stage primaryStage, List<Pokemon> party){
        this.party = party;
        wantsToExit = false;
        updateStoragePane();
        updatePartyPane();
        primaryStage.setTitle("Storage room");
    }

    public void updateStoragePane(){
        storagePane.getChildren().clear();
        for (Pokemon p:PokemonStorage.storedMonList) {
            Button storageActionButton = new Button("Add to party");
            storageActionButton.setOnAction(event -> withDrawMonFromStorage(party,p));
            storagePane.getChildren().add(createPokeDisplay(p,storageActionButton));
        }
    }

    public void updatePartyPane(){
        partyPane.getChildren().clear();
        for (Pokemon p:party) {
            Button storageActionButton = new Button("Deposit");
            storageActionButton.setOnAction(event -> depositMonFromParty(party,p));
            partyPane.getChildren().add(createPokeDisplay(p,storageActionButton));
        }
    }

    public void depositMonFromParty(List<Pokemon> party, Pokemon monToDeposit){
        if(party.size()<=1) {
            Debugger.out("Can't Deposit last pokemon on party");
            return;
        }
        if(party.contains(monToDeposit)){
            party.remove( monToDeposit);
            updatePartyPane();
            PokemonStorage.storedMonList.add(monToDeposit);
            updateStoragePane();
        }else{
            Debugger.out("party does not contain " + monToDeposit.name);
        }
    }

    public void withDrawMonFromStorage(List<Pokemon> party, Pokemon monToWithDraw){
        if(PokemonStorage.storedMonList.contains(monToWithDraw)){
            if(party.size() >=Settings.maxPartySize){
                System.out.println("party full");
            }else {
                PokemonStorage.storedMonList.remove(monToWithDraw);
                updateStoragePane();
                party.add(monToWithDraw);
                updatePartyPane();
            }
        }else{
            Debugger.out("Storage does not contain " + monToWithDraw.name);
        }
    }

    public Pane createPokeDisplay(Pokemon p, Button storageActionButton ){
        VBox pokeDisplay = new VBox();
        pokeDisplay.setMinSize(storagePane.getWidth()/monsPerRow,storagePane.getHeight()/monsPerRow);
        pokeDisplay.getChildren().addAll(new Label(p.name),new Label("Level: " + p.getLevel()),
                                         storageActionButton,new Button("Status"));
        return  pokeDisplay;
    }

    public void tryToExit(){
        wantsToExit = true;
    }

    public boolean readyToExit(){
        return wantsToExit;
    }

}
