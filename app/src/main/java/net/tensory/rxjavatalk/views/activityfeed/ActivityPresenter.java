package net.tensory.rxjavatalk.views.activityfeed;

import android.arch.lifecycle.ViewModel;

import net.tensory.rxjavatalk.providers.BattleProvider;
import net.tensory.rxjavatalk.providers.DebtProvider;

import io.reactivex.Observable;

class ActivityPresenter extends ViewModel {

    private final BattleProvider battleProvider;
    private final DebtProvider debtProvider;

    ActivityPresenter(BattleProvider battleProvider, DebtProvider debtProvider) {
        this.battleProvider = battleProvider;
        this.debtProvider = debtProvider;
    }

    Observable<Object> getEventFeed() {
        return Observable.merge(battleProvider.observeBattles(), debtProvider.observeDebt());
    }

}