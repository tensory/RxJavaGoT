package net.tensory.rxjavatalk.models;

import io.reactivex.Observable;

public class HouseAssetProfile {
    private House house;
    private int armySize;
    private int dragonCount;

    public HouseAssetProfile(House house, int armySize, int dragonCount) {
        this.house = house;
        this.armySize = armySize;
        this.dragonCount = dragonCount;
    }

    public static Observable<Double> getAssetRating(HouseAssetProfile houseAssets) {
        return Observable.zip(
                Observable.just(houseAssets.getArmySize()),
                Observable.just(houseAssets.getDragonCount()),
                (dragons, armySize) ->
                        (dragons * 10000.0) + (0.35 * armySize)
        )
                ;
    }

    public int getArmySize() {
        return armySize;
    }

    public void setArmySize(int armySize) {
        this.armySize = armySize;
    }

    public int getDragonCount() {
        return dragonCount;
    }

    public void setDragonCount(int dragonCount) {
        this.dragonCount = dragonCount;
    }
}
