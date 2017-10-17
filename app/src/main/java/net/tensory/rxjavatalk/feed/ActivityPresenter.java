package net.tensory.rxjavatalk.feed;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.util.Pair;

import net.tensory.rxjavatalk.injection.AppComponent;
import net.tensory.rxjavatalk.models.House;
import net.tensory.rxjavatalk.providers.BattleProvider;
import net.tensory.rxjavatalk.providers.DebtProvider;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class ActivityPresenter extends ViewModel {

    private final DebtProvider debtProvider;

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
        this.debtProvider = debtProvider;

        // Example 1: Only one feed
//        battleProvider.observeBattles()
//                .subscribe(battle -> eventFeed.onNext(battle));

        // Example 2: Combine the Battle and Debt feeds
        Observable.merge(battleProvider.observeBattles(), observeDebt())
                  .subscribe(event -> eventFeed.onNext(event));
    }

    Observable<Object> getEventFeed() {
        return eventFeed;
    }


    private Observable<Pair<House, Double>> observeDebt() {
        return Observable.empty();
//        return Observable.merge(
//                Observable.just(House.LANNISTER)
//                .flatMap(house -> debtProvider.observeDebt(house))
//                .map(debtValue -> new Pair<>(house, debtValue)),
//                debtProvider.observeDebt(House.LANNISTER)
//                        .map(debtValue -> new Pair<>(House.LANNISTER, debtValue)),
//                debtProvider.observeDebt(House.STARK)
//                        .map(debtValue -> new Pair<>(House.STARK, debtValue)),
//                debtProvider.observeDebt(House.LANNISTER)
//                        .map(debtValue -> new Pair<>(House.TARGARYEN, debtValue)));
    }

}