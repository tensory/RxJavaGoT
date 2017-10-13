package net.tensory.rxjavatalk.models;

public class Combatant {
    private House house;

    private double debt;
    private int armySize;
    private int dragonCount;


    public Combatant(House house, double debt, int armySize, int dragonCount) {
        this.house = house;
        this.debt = debt;
        this.armySize = armySize;
        this.dragonCount = dragonCount;
    }

    public String getName() {
        return house.name();
    }

    public double getDebt() {
        return debt;
    }

    public int getArmySize() {
        return armySize;
    }

    public int getDragonCount() {
        return dragonCount;
    }
}
