package com.company;

import com.company.Pokemon.Pokemon;
import com.company.Utilities.Animation.AnimationData;
import com.company.Utilities.Animation.AnimationFactory;
import com.company.Utilities.Debug.Debugger;

public class CatchCommand extends TrainerCommand {
    pcTrainer player;
    BattleResult battleResult;

    public CatchCommand(pcTrainer player,BattleResult battleResult) {
        super(player, AnimationFactory.getPokeChangeAnim(), "Catching", false);
        this.player = player;
        this.battleResult = battleResult;
    }


    @Override
    public void callBack() {
        if (player.hasPokeBalls()) {
            Pokemon catchResult = player.enemySlot.tryCatch();
            if (catchResult != null)
                battleResult.addCaughtMon(catchResult);
        }else
            Debugger.out("insufficent pokeBalls");//shouldn't happen really
    }

    @Override
    public String toJsonData() {
        return null;//Catching is disabled on networked battles to begin with
    }
}
