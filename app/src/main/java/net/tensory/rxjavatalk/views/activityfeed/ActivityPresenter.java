package net.tensory.rxjavatalk.views.activityfeed;

import android.arch.lifecycle.ViewModel;

import net.tensory.rxjavatalk.providers.BattleProvider;
import net.tensory.rxjavatalk.providers.DebtProvider;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

public class ActivityPresenter extends ViewModel {

    private final Disposable disposable;

    private BehaviorSubject<Object> eventFeed = BehaviorSubject.create();

    public ActivityPresenter(BattleProvider battleProvider, DebtProvider debtProvider) {
        disposable = Observable.merge(battleProvider.observeBattles(), debtProvider.observeDebt())
                               .subscribe(event -> eventFeed.onNext(event));
    }

    Observable<Object> getEventFeed() {
        return eventFeed;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        disposable.dispose();
    }
}