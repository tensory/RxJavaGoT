package net.tensory.rxjavatalk.house;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import net.tensory.rxjavatalk.R;
import net.tensory.rxjavatalk.data.DragonManager;
import net.tensory.rxjavatalk.injection.AppComponent;
import net.tensory.rxjavatalk.models.Battle;
import net.tensory.rxjavatalk.models.House;
import net.tensory.rxjavatalk.providers.BattleProvider;
import net.tensory.rxjavatalk.providers.HouseAssetProfileProvider;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

public class HousePresenter extends ViewModel {

    private final BehaviorSubject<String> ratingsSubject = BehaviorSubject.create();
    private final BehaviorSubject<Double> debtSubject = BehaviorSubject.create();
    private final BehaviorSubject<Integer> soldiersSubject = BehaviorSubject.create();
    private final BehaviorSubject<Integer> dragonsSubject = BehaviorSubject.create();

    private final Disposable battlesDisposable;

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
                    appComponent.providesDragonManager());
        }
    }

    private HousePresenter(House house,
                           HouseAssetProfileProvider assetProfileProvider,
                           BattleProvider battleProvider,
                           DragonManager dragonManager) {
        this.house = house;

        battlesDisposable = battleProvider.observeBattles()
                .subscribe(battle -> {
                            updateFromBattles(house, assetProfileProvider, battle);
                        },
                        throwable -> {
                        }
                );

        dragonManager.observeDragons(house)
                .subscribe(t -> {
                    Log.e(HousePresenter.class.getSimpleName(),"Update dragons for "+house + " to "+ t);
                    dragonsSubject.onNext(t);
                });
    }

    private void updateFromBattles(House house, HouseAssetProfileProvider assetProfileProvider, Battle battle) {
        battle.getHouseBattleResults()
                .stream().
                filter(combatant -> house.equals(combatant.getHouse()))
                .forEach(combatant -> {
                    soldiersSubject.onNext(combatant.getCurrentArmySize());
//                    debtSubject.onNext(combatant.getDebt());

//                    assetProfileProvider.publishHouseAssetProfile(house,
//                            combatant.getCurrentArmySize());
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
