package com.company.Exploration;

import com.company.Pokemon.Pokemon;
import com.company.Settings;
import com.company.Utilities.Debug.Debugger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PokemonStorageController {
    @FXML
    private HBox partyPane;

    @FXML
    private FlowPane storagePane;

    @FXML
    private Button exitButton;

    public final int monsPerRow = 6;
    public ArrayList<Pokemon> party;

    boolean wantsToExit;

    public void begin(Stage primaryStage, ArrayList<Pokemon> party){
        this.party = party;
        wantsToExit = false;
        updateStoragePane();
        updatePartyPane();
        primaryStage.setTitle("Storage room");
    }

    public void updateStoragePane(){
        storagePane.getChildren().clear();
        for (Pokemon p:PokemonStorage.storedMonList) {
            StorageViewUnit svw =createPokeDisplay(p,storagePane);
            if(p != null) {
                Button transferButton = svw.getTransferButton();
                transferButton.setText("Withdraw");
                transferButton.setOnAction(event -> withDrawMonFromStorage(party, p));
            }
        }
    }

    public void updatePartyPane(){
        partyPane.getChildren().clear();
        for (Pokemon p:party) {
            StorageViewUnit svw =createPokeDisplay(p,partyPane);
            if(p != null) {
                Button transferButton = svw.getTransferButton();
                transferButton.setText("deposit");
                transferButton.setOnAction(event -> depositMonFromParty(party, p));
            }
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

    public StorageViewUnit createPokeDisplay(Pokemon p,Pane parent){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StorageViewUnitFxml.fxml"));
            Parent r = loader.load();
            StorageViewUnit svw = loader.getController();
            svw.setPokemon(p);
            parent.getChildren().add(r);
            return svw;
        }catch (IOException ioe){
            System.out.println("load fail");
        }
        return  null;
    }

    public void tryToExit(){
        wantsToExit = true;
    }

    public boolean readyToExit(){
        return wantsToExit;
    }

}
