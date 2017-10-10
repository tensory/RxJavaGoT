package net.tensory.rxjavatalk.models;

import io.reactivex.Observable;

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

    public Observable<Integer> getCreditScore() {
        return Observable.combineLatest(
                Observable.just(debt),
                Observable.just(armySize),
                Observable.just(dragonCount),
                (debt, armySize, dragonCount) -> (int)
                        ((dragonCount * 1000) + ((armySize / debt) * 100)));
    }

    public String getName() {
        return house.name();
    }
}
