package com.company.Exploration;

import com.company.Pokemon.Pokemon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PokemonStorage {

    public static final ObservableList<Pokemon> storedMonList = FXCollections.observableArrayList();

    private  PokemonStorage(){};//To prevent instantiation

    public static void addMon(Pokemon... p){ Collections.addAll(storedMonList,p); }

    public static Pokemon getMon(int monIndex){
        if(monIndex >= storedMonList.size()){
            System.out.println("Trying to access pokemon out of storage bounds");
            return  null;
        }else{
            Pokemon retVal = storedMonList.get(monIndex);
            storedMonList.remove(monIndex);
            return  retVal;
        }
    }
}
