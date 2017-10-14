package net.tensory.rxjavatalk.models;

public class HouseBattleResult {

    private House house;

    private int currentArmySize;
    private int currentDragonCount;

    public HouseBattleResult(House house, int currentArmySize, int currentDragonCount) {
        this.house = house;
        this.currentArmySize = currentArmySize;
        this.currentDragonCount = currentDragonCount;
    }

    public House getHouse() {
        return house;
    }

    public int getCurrentArmySize() {
        return currentArmySize;
    }

    public int getCurrentDragonCount() {
        return currentDragonCount;
    }

    @Override
    public String toString() {
        return "HouseBattleResult{" +
                "house=" + house +
                ", currentArmySize=" + currentArmySize +
                ", currentDragonCount=" + currentDragonCount +
                '}';
    }
}
