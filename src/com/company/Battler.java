package com.company;

import com.company.Pokemon.Pokemon;

import java.util.ArrayList;

/*
* New interface for anything that can participate in battle
* Created so that we can have battles with wild mons
* */
public interface Battler {

    void heal();

    int getDefeatXp();

    Pokemon getStagedPokemon();

    boolean canFight();

    void prepareForBattle(BattleSlot ownedSlot, BattleSlot enemySlot);

    void endBattle();

    void prepTurn();

    boolean hasCommandBeforeTurnEnd();

    BattleCommand getCommandToExecuteBeforeTurnEnd();

    ArrayList<BattleCommand> getCommands();

    Boolean hasFinalizedCommands();

    void endTurnPrep();

    boolean canEndTurn();

    String getName();
}
