package net.tensory.rxjavatalk.models;

public class Combatant {
    private House house;
    private double debt;


    public Combatant(House house, double debt) {
        this.house = house;
        this.debt = debt;
    }

//    public Observable<Integer> getCreditScore() {
//        return Observable.combineLatest(
//                Observable.just(debt),
//                (debt, armySize, dragonCount) -> (int)
//                        ((dragonCount * 1000) + ((armySize / debt) * 100)));
//    }

    public String getName() {
        return house.name();
    }
}
