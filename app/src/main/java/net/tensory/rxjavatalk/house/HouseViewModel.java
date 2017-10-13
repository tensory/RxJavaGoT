package net.tensory.rxjavatalk.house;

import android.arch.lifecycle.ViewModel;

import net.tensory.rxjavatalk.models.Battle;
import net.tensory.rxjavatalk.models.Combatant;
import net.tensory.rxjavatalk.models.House;
import net.tensory.rxjavatalk.providers.BattleProvider;
import net.tensory.rxjavatalk.providers.HouseAssetProfileProvider;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class HouseViewModel extends ViewModel {

    private final BehaviorSubject<String> ratingsSubject = BehaviorSubject.create();
    private final BehaviorSubject<Double> debtSubject = BehaviorSubject.create();
    private final BehaviorSubject<Integer> soldiersSubject = BehaviorSubject.create();
    private final BehaviorSubject<Integer> dragonsSubject = BehaviorSubject.create();

    private final Disposable battlesDisposable;

    public HouseViewModel(House house) {
        // Let's DI this provider
        HouseAssetProfileProvider profileProvider = new HouseAssetProfileProvider();

        battlesDisposable = new BattleProvider().getBattlesFeed()
                .filter(battle -> battle
                        .getCombatants()
                        .stream().filter(combatant -> combatantNameMatchesHouseName(house, combatant))
                        .count() > 0)
                .subscribe(battle -> {
                            battle.getCombatants()
                                    .stream().
                                    filter(combatant -> combatantNameMatchesHouseName(house, combatant))
                                    .forEach(combatant -> {
                                        soldiersSubject.onNext(combatant.getArmySize());
                                        dragonsSubject.onNext(combatant.getDragonCount());
                                        debtSubject.onNext(combatant.getDebt());

                                        profileProvider.publishHouseAssetProfile(house,
                                                combatant.getArmySize(),
                                                combatant.getDragonCount());
                                    });
                        },
                        throwable -> {
                        }
                );
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

    private boolean combatantNameMatchesHouseName(final House house, Combatant combatant) {
        return house.getHouseName().equals(combatant.getName());
    }
}
