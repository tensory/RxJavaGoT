package net.tensory.rxjavatalk.data;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class DebtFeed {

    private static final double MAX_DEBT_GOLD = 1000000;

    private BehaviorSubject<Double> lannisterDebt = BehaviorSubject.create();
    private BehaviorSubject<Double> starkDebt = BehaviorSubject.create();
    private BehaviorSubject<Double> targaryenDebt = BehaviorSubject.create();

    public DebtFeed() {
        getTimedEmittedValue(3)
                .subscribe(lannisterDebt::onNext);

        getTimedEmittedValue(14)
                .subscribe(starkDebt::onNext);

        getTimedEmittedValue(8)
                .subscribe(targaryenDebt::onNext);

        // White Walkers don't know what money is, so there's no need to count their debts...
    }

    private Observable<Double> getTimedEmittedValue(long intervalSeconds) {
        return Observable.interval(intervalSeconds, TimeUnit.SECONDS)
                .map(timeInterval ->
                        ThreadLocalRandom.current().nextDouble(MAX_DEBT_GOLD));
    }

    public BehaviorSubject<Double> getLannisterDebt() {
        return lannisterDebt;
    }

    public BehaviorSubject<Double> getStarkDebt() {
        return starkDebt;
    }

    public BehaviorSubject<Double> getTargaryenDebt() {
        return targaryenDebt;
    }
}
