package com.company.RealTime;

public class DamageMessage {
    int damagedPlayerID;
    int damageAmount;
    boolean isGuaranteedKill;//if we want to ensure that someone is dead from the server
    double damageMod;//effective or super effective

    public DamageMessage(int damagedPlayerID, int damageAmount, boolean isGuaranteedKill, double damageMod) {
        this.damagedPlayerID = damagedPlayerID;
        this.damageAmount = damageAmount;
        this.isGuaranteedKill = isGuaranteedKill;
        this.damageMod = damageMod;
    }


    @Override
    public String toString() {
        return "DamageMessage{" +
                "damagedPlayerID=" + damagedPlayerID +
                ", damageAmount=" + damageAmount +
                ", isGuaranteedKill=" + isGuaranteedKill +
                '}';
    }
}

