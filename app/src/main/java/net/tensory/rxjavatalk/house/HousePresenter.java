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
import net.tensory.rxjavatalk.providers.HouseAssetProfileProvider;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

public class HousePresenter extends ViewModel {

    private final BehaviorSubject<String> ratingsSubject = BehaviorSubject.create();
    private final BehaviorSubject<Double> debtSubject = BehaviorSubject.create();
    private final BehaviorSubject<Integer> soldiersSubject = BehaviorSubject.create();
    private final BehaviorSubject<Integer> dragonsSubject = BehaviorSubject.create();

    private final Disposable battlesDisposable;

    private final House house;
    private final HouseAssetProfileProvider assetProfileProvider;

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
                    appComponent.providesBattles());
        }
    }

    private HousePresenter(House house,
                           HouseAssetProfileProvider assetProfileProvider,
                           BattleProvider battleProvider) {
        this.house = house;
        this.assetProfileProvider = assetProfileProvider;

        battlesDisposable = battleProvider.observeBattles()
                .filter(battle -> battle
                        .getHouseBattleResults()
                        .stream().filter(combatant -> house == combatant.getHouse())
                        .count() > 0)
                .subscribe(battle -> {
                            updateFromBattles(house, assetProfileProvider, battle);
                        },
                        throwable -> {
                        }
                );
    }

    private void updateFromBattles(House house, HouseAssetProfileProvider assetProfileProvider, Battle battle) {
        battle.getHouseBattleResults()
                .stream().
                filter(combatant -> house == combatant.getHouse())
                .forEach(combatant -> {
                    soldiersSubject.onNext(combatant.getCurrentArmySize());
                    dragonsSubject.onNext(combatant.getCurrentDragonCount());
//                    debtSubject.onNext(combatant.getDebt());

                    assetProfileProvider.publishHouseAssetProfile(house,
                            combatant.getCurrentArmySize(),
                            combatant.getCurrentDragonCount());
                });
    }

    public Flowable<String> observeRating() {
        return ratingsSubject.toFlowable(BackpressureStrategy.LATEST);
    }

    public Flowable<Double> observeDebt() {
        return debtSubject.toFlowable(BackpressureStrategy.LATEST);
    }

    public Flowable<Integer> observeSoldiers() {
        return soldiersSubject.toFlowable(BackpressureStrategy.LATEST);
    }

    public Flowable<Integer> observeDragons() {
        return dragonsSubject.toFlowable(BackpressureStrategy.LATEST);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        battlesDisposable.dispose();
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
