package net.tensory.rxjavatalk.house;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.graphics.drawable.Drawable;

import net.tensory.rxjavatalk.R;
import net.tensory.rxjavatalk.injection.AppComponent;
import net.tensory.rxjavatalk.models.Battle;
import net.tensory.rxjavatalk.models.House;
import net.tensory.rxjavatalk.models.HouseBattleResult;
import net.tensory.rxjavatalk.providers.BattleProvider;
import net.tensory.rxjavatalk.providers.CreditRatingProvider;
import net.tensory.rxjavatalk.providers.DebtProvider;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;

public class HousePresenter extends ViewModel {

    private final BehaviorSubject<Double> ratingsSubject = BehaviorSubject.create();

    private final BehaviorSubject<Double> debtSubject = BehaviorSubject.create();

    private final BehaviorSubject<Integer> soldiersSubject = BehaviorSubject.create();

    private final BehaviorSubject<Integer> dragonsSubject = BehaviorSubject.create();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final House house;
    private final BattleProvider battleProvider;
    private final DebtProvider debtProvider;
    private final CreditRatingProvider creditRatingProvider;

    public static class Factory implements ViewModelProvider.Factory {

        private final House house;
        private final AppComponent appComponent;

        public Factory(House house, AppComponent appComponent) {
            this.house = house;
            this.appComponent = appComponent;
        }

        @Override
        public HousePresenter create(Class modelClass) {
            return new HousePresenter(
                    house,
                    appComponent.providesBattles(),
                    appComponent.providesDebts(),
                    appComponent.providesCreditRatings()
            );
        }
    }

    private HousePresenter(House house,
                           BattleProvider battleProvider,
                           DebtProvider debtProvider,
                           CreditRatingProvider creditRatingProvider) {
        this.house = house;
        this.battleProvider = battleProvider;
        this.debtProvider = debtProvider;
        this.creditRatingProvider = creditRatingProvider;

        subscribeToBattles();

        subscribeToDebtFeed();

        subscribeToCreditRating();
    }

    private void subscribeToBattles() {
        compositeDisposable.add(battleProvider.observeBattles()
                                              .filter(this::wereWeInTheBattle)
                                              .map(this::getHouseBattleResult)
                                              .subscribe(this::updateFromBattles));
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

    private void subscribeToDebtFeed() {
        compositeDisposable.add(debtProvider.observeDebt(house)
                                            .subscribe(debtSubject::onNext));
    }

    private void subscribeToCreditRating() {
        compositeDisposable.add(creditRatingProvider.observeCreditRating(house)
                                                    .subscribe(ratingsSubject::onNext));
    }

    public Observable<Double> observeRating() {
        return ratingsSubject.toFlowable(BackpressureStrategy.LATEST).toObservable();
    }

    public Observable<Double> observeDebt() {
        return debtSubject.toFlowable(BackpressureStrategy.LATEST).toObservable();
    }

    public Observable<Integer> observeSoldiers() {
        return soldiersSubject.toFlowable(BackpressureStrategy.LATEST).toObservable();
    }

    public Observable<Integer> observeDragons() {
        return dragonsSubject.toFlowable(BackpressureStrategy.LATEST).toObservable();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
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
