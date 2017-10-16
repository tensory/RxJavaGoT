package net.tensory.rxjavatalk.house;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.graphics.drawable.Drawable;

import net.tensory.rxjavatalk.R;
import net.tensory.rxjavatalk.injection.AppComponent;
import net.tensory.rxjavatalk.models.Battle;
import net.tensory.rxjavatalk.models.House;
import net.tensory.rxjavatalk.providers.BattleProvider;
import net.tensory.rxjavatalk.providers.DebtProvider;
import net.tensory.rxjavatalk.providers.HouseAssetProfileProvider;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;

public class HousePresenter extends ViewModel {

    private final BehaviorSubject<String> ratingsSubject = BehaviorSubject.create();
    private final BehaviorSubject<Double> debtSubject = BehaviorSubject.create();
    private final BehaviorSubject<Integer> soldiersSubject = BehaviorSubject.create();
    private final BehaviorSubject<Integer> dragonsSubject = BehaviorSubject.create();

    private final CompositeDisposable compositeDisposable;

    private final House house;
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
                    appComponent.providesHouseAssetProfile(),
                    appComponent.providesBattles(),
                    appComponent.providesDebts());
        }
    }

    private HousePresenter(House house,
                           HouseAssetProfileProvider assetProfileProvider,
                           BattleProvider battleProvider,
                           DebtProvider debtProvider) {
        this.house = house;
        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(battleProvider.observeBattles()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(battle -> {
                            updateFromBattles(house, assetProfileProvider, battle);
                        },
                        throwable -> {
                        }
                ));

        compositeDisposable.add(debtProvider.observeDebt(house)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(debtSubject::onNext));
    }

    private void updateFromBattles(House house, HouseAssetProfileProvider assetProfileProvider, Battle battle) {
        battle.getHouseBattleResults()
                .stream().
                filter(combatant -> house.equals(combatant.getHouse()))
                .forEach(combatant -> {
                    soldiersSubject.onNext(combatant.getCurrentArmySize());

                    final int currentDragonCount = combatant.getCurrentDragonCount();
                    final Integer value = dragonsSubject.getValue();
                    if (value == null || !value.equals(currentDragonCount)) {
                        dragonsSubject.onNext(currentDragonCount);
                    }

                    assetProfileProvider.publishHouseAssetProfile(house,
                            combatant.getCurrentArmySize(),
                            currentDragonCount);
                });
    }

    public Observable<String> observeRating() {
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
