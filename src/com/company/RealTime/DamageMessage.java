package com.company.RealTime;

public class DamageMessage {
    int damagedPlayerID;
    int damageAmount;
    boolean isGuaranteedKill;//if we want to ensure that someone is dead from the server

    public DamageMessage(int damagedPlayerID, int damageAmount, boolean isGuaranteedKill) {
        this.damagedPlayerID = damagedPlayerID;
        this.damageAmount = damageAmount;
        this.isGuaranteedKill = isGuaranteedKill;
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
