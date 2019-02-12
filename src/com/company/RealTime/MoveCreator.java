package com.company.RealTime;

import com.company.Pokemon.Moves.DamageType;
import com.company.Pokemon.PokemonData;
import com.company.Pokemon.Type;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;

public class MoveCreator {

    @FXML
    private TextField recoveryAMountfield;

    @FXML
    private TextField powField;

    @FXML
    private TextField sfxField;

    @FXML
    private ComboBox<Type> elementCOmbo;

    @FXML
    private TextField nameField;

    @FXML
    private TextField aNimField;

    @FXML
    private Button makeBUtton;

    @FXML
    private ComboBox<DamageType> damageType;

    @FXML
    private TextField costField;

    public void initialize(){
        ObservableList<Type> elements =  FXCollections.observableArrayList();
        elements.addAll(Type.values());
        elementCOmbo.setItems(elements);

        ObservableList<DamageType> damages =  FXCollections.observableArrayList();
        damages.addAll((DamageType.values()));
        damageType.setItems(damages);

        makeBUtton.setOnAction(event -> {

//        Gson gson=new Gson();
//        try {
//            JsonWriter writer = new JsonWriter(new FileWriter("src/com/company/Pokemon/Moves/.txt"));
//            writer.setIndent("  ");
//            gson.toJson(MoveCardData.cardMap.values(),MoveCardData.cardMap.values().getClass(), writer);
//            writer.flush();
//            writer.close();
//        }catch (IOException ioe){
//            System.out.println("write failed...");
//        }
//
//        FileReader fr=null;
//        try {
//            fr=new FileReader(new File("src/com/company/Pokemon/Moves/pokemonFactory.txt"));
//        } catch (FileNotFoundException e) {
//            System.out.println("Cant load file");
//        }
//
//        PokemonData datas[]=gson.fromJson(fr,PokemonData[].class);
//        System.out.println(MoveCardData.cardMap.values().getClass());
//        for (PokemonData m:
//                datas) {
//            pokeMap.put(m.name,m);
//        }
        });
    }

}
