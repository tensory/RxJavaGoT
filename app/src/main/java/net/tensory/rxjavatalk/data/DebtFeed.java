package net.tensory.rxjavatalk.data;

import net.tensory.rxjavatalk.providers.DebtProvider;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class DebtFeed {

    private BehaviorSubject<Double> lannisterDebt = BehaviorSubject.create();
    private BehaviorSubject<Double> starkDebt = BehaviorSubject.create();
    private BehaviorSubject<Double> targaryenDebt = BehaviorSubject.create();

    public DebtFeed() {
        getTimedEmittedValue(3)
                .subscribe(lannisterDebt::onNext);

        getTimedEmittedValue(8)
                .subscribe(starkDebt::onNext);

        getTimedEmittedValue(14)
                .subscribe(targaryenDebt::onNext);

        // White Walkers don't know what money is, so there's no need to count their debts...
    }

    private Observable<Double> getTimedEmittedValue(long intervalSeconds) {
        return Observable.interval(intervalSeconds, TimeUnit.SECONDS)
                .map(timeInterval ->
                        ThreadLocalRandom.current().nextDouble(DebtProvider.MAX_DEBT_GOLD));
    }

    public Observable<Double> getLannisterDebt() {
        return lannisterDebt.toFlowable(BackpressureStrategy.LATEST).toObservable();
    }

    public Observable<Double> getStarkDebt() {
        return starkDebt.toFlowable(BackpressureStrategy.LATEST).toObservable();
    }

    public Observable<Double> getTargaryenDebt() {
        return targaryenDebt.toFlowable(BackpressureStrategy.LATEST).toObservable();
    }
}
