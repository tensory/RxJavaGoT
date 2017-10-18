package net.tensory.rxjavatalk.views.house;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.graphics.drawable.Drawable;

import net.tensory.rxjavatalk.R;
import net.tensory.rxjavatalk.models.Battle;
import net.tensory.rxjavatalk.models.House;
import net.tensory.rxjavatalk.models.HouseBattleResult;
import net.tensory.rxjavatalk.providers.BattleProvider;
import net.tensory.rxjavatalk.providers.CreditRatingProvider;
import net.tensory.rxjavatalk.providers.DebtProvider;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

class HousePresenter extends ViewModel {

    private final House house;
    private final DebtProvider debtProvider;
    private final CreditRatingProvider creditRatingProvider;

    private final BehaviorSubject<Integer> soldiersSubject = BehaviorSubject.create();

    private final BehaviorSubject<Integer> dragonsSubject = BehaviorSubject.create();

    private final Disposable disposable;

    HousePresenter(House house,
                   BattleProvider battleProvider,
                   DebtProvider debtProvider,
                   CreditRatingProvider creditRatingProvider) {
        this.house = house;
        this.debtProvider = debtProvider;
        this.creditRatingProvider = creditRatingProvider;

        disposable = battleProvider.observeBattles()
                                   .filter(this::wereWeInTheBattle)
                                   .map(this::getHouseBattleResult)
                                   .subscribe(this::updateFromBattles);
    }

    private boolean wereWeInTheBattle(Battle battle) {
        return battle.getWinner().getHouse() == house || battle.getLoser().getHouse() == house;
    }

    private HouseBattleResult getHouseBattleResult(Battle battle) {
        boolean isWinningHouse = house.equals(battle.getWinner().getHouse());
        return isWinningHouse ? battle.getWinner() : battle.getLoser();
    }

    private void updateFromBattles(HouseBattleResult houseBattleResult) {
        emitOnChange(soldiersSubject, houseBattleResult.getCurrentArmySize());

        emitOnChange(dragonsSubject, houseBattleResult.getCurrentDragonCount());
    }

    private void emitOnChange(BehaviorSubject<Integer> behaviorSubject, int currentCount) {
        Integer value = behaviorSubject.getValue();
        final boolean hasChanged = value == null || !value.equals(currentCount);

        if (hasChanged) {
            behaviorSubject.onNext(currentCount);
        }
    }

    Observable<Integer> observeSoldiers() {
        return soldiersSubject.toFlowable(BackpressureStrategy.LATEST).toObservable();
    }

    Observable<Integer> observeDragons() {
        return dragonsSubject.toFlowable(BackpressureStrategy.LATEST).toObservable();
    }

    Observable<Double> observeRating() {
        return creditRatingProvider.observeCreditRating(house);
    }

    Observable<Double> observeDebt() {
        return debtProvider.observeDebt(house);
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

    Drawable getShield(final Context context) {
        switch (house) {
            case STARK:
                return context.getResources().getDrawable(R.drawable.house_stark_shield, null);
            case TARGARYEN:
                return context.getResources().getDrawable(R.drawable.house_targaryen, null);
            case LANNISTER:
                return context.getResources().getDrawable(R.drawable.house_lannister_shield, null);
            case NIGHT_KING:
                return context.getResources().getDrawable(R.drawable.night_king, null);
        }

        return null;
    }
}
