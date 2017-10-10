package net.tensory.rxjavatalk.house;

import android.arch.lifecycle.ViewModel;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.subjects.BehaviorSubject;

public class HouseViewModel extends ViewModel {

    private final BehaviorSubject<String> ratingsSubject = BehaviorSubject.create();
    private final BehaviorSubject<String> debtSubject = BehaviorSubject.create();
    private final BehaviorSubject<String> soldiersSubject = BehaviorSubject.create();
    private final BehaviorSubject<String> dragonsSubject = BehaviorSubject.create();

    public Flowable<String> observeRating() {
        return ratingsSubject.toFlowable(BackpressureStrategy.LATEST);
    }

    public Flowable<String> observeDebt() {
        return debtSubject.toFlowable(BackpressureStrategy.LATEST);
    }

    public Flowable<String> observeSoldiers() {
        return soldiersSubject.toFlowable(BackpressureStrategy.LATEST);
    }

    public Flowable<String> observeDragons() {
        return dragonsSubject.toFlowable(BackpressureStrategy.LATEST);
    }
}
