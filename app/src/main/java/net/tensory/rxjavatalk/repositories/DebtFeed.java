package net.tensory.rxjavatalk.repositories;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class DebtFeed {

    private static final double ADJUSTMENT = 10000;
    private static final double INITIAL_DEBT_SEED = 100000;

    private BehaviorSubject<Double> lannisterDebt = BehaviorSubject.createDefault(ThreadLocalRandom.current().nextDouble(INITIAL_DEBT_SEED));
    private BehaviorSubject<Double> starkDebt = BehaviorSubject.createDefault(ThreadLocalRandom.current().nextDouble(INITIAL_DEBT_SEED / 2));
    private BehaviorSubject<Double> targaryenDebt = BehaviorSubject.createDefault(ThreadLocalRandom.current().nextDouble(INITIAL_DEBT_SEED / 3));

    public DebtFeed() {
        setupSubjects(lannisterDebt, 21);
        setupSubjects(starkDebt, 29);
        setupSubjects(targaryenDebt, 37);

        // White Walkers don't know what money is, so there's no need to count their debts...
    }

    private void setupSubjects(BehaviorSubject<Double> subject, int intervalSeconds) {
        getTimedEmittedValue(intervalSeconds)
                .map(t -> subject.getValue() + t)
                .subscribe(subject::onNext);
    }

    private Observable<Double> getTimedEmittedValue(long intervalSeconds) {
        return Observable.interval(intervalSeconds, TimeUnit.SECONDS)
                .map(timeInterval -> ThreadLocalRandom.current().nextDouble(ADJUSTMENT));
    }

    public Observable<Double> observeLannisterDebt() {
        return lannisterDebt.toFlowable(BackpressureStrategy.LATEST).toObservable();
    }

    public Observable<Double> observeStarkDebt() {
        return starkDebt.toFlowable(BackpressureStrategy.LATEST).toObservable();
    }

    public Observable<Double> observeTargaryenDebt() {
        return targaryenDebt.toFlowable(BackpressureStrategy.LATEST).toObservable();
    }
}
