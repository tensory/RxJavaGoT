package net.tensory.rxjavatalk.feed;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import net.tensory.rxjavatalk.injection.AppComponent;
import net.tensory.rxjavatalk.providers.BattleProvider;
import net.tensory.rxjavatalk.providers.DebtProvider;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class ActivityPresenter extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final AppComponent appComponent;

        public Factory(AppComponent appComponent) {
            this.appComponent = appComponent;
        }

        @Override
        public ActivityPresenter create(Class modelClass) {
            return new ActivityPresenter(
                    appComponent.providesBattles(), appComponent.providesDebts());
        }
    }

    private BehaviorSubject<Object> eventFeed = BehaviorSubject.create();

    public ActivityPresenter(BattleProvider battleProvider, DebtProvider debtProvider) {
        // Example 1: Only one feed
//        battleProvider.observeBattles()
//                .subscribe(battle -> eventFeed.onNext(battle));

        // Example 2: Combine the Battle and Debt feeds
        Observable.merge(battleProvider.observeBattles(), debtProvider.observeDebt())
                .subscribe(o -> eventFeed.onNext(o));
    }

    Observable<Object> getEventFeed() {
        return eventFeed;
    }

}