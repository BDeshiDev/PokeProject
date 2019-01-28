package com.company;

import com.company.Pokemon.Moves.Move;
import com.company.Pokemon.Pokemon;
import com.company.Utilities.Debug.Debugger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class MovesListUI {
    private final int maxRowOrCol = 2;//maximum 4 moves but this shouldn't even come in to play normally
    private int row=0,col=0;
    private GridPane grid;

    public MovesListUI(GridPane grid){
        this.grid = grid;
    }

    public void add(Move move, pcTrainer player, Pokemon moveOwnerMon){
        if((row+1) >=maxRowOrCol && (col+1) >=maxRowOrCol){
            Debugger.out("failed to add move: " + move.getName()+ " since move grid is full");

            return;
        }

        Button mButton =  ButtonFactory.getMoveButton(300,80,move);
        grid.add(mButton,col,row);
        mButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                player.setCommand(move,moveOwnerMon);
            }
        });

        col++;
        if(col>=maxRowOrCol){
            row++;
            col = 0;
        }
    }

    public void load(Pokemon pokemonToLoad,pcTrainer player){
        row=0;col=0;
        final ArrayList<Move> moves = pokemonToLoad.getMoves();
        grid.getChildren().clear();//messes up the grid. change later
        for (Move m :moves) {
            add(m,player,pokemonToLoad);
        }
    }

}
