package net.tensory.rxjavatalk.models;

public class HouseBattleResult {
    private House house;

    private int currentArmySize;

    public HouseBattleResult(House house, int currentArmySize) {
        this.house = house;
        this.currentArmySize = currentArmySize;
    }

    public House getHouse() {
        return house;
    }

    public int getCurrentArmySize() {
        return currentArmySize;
    }

    @Override
    public String toString() {
        return "HouseBattleResult{" +
                "house=" + house +
                ", currentArmySize=" + currentArmySize +
                '}';
    }
}
