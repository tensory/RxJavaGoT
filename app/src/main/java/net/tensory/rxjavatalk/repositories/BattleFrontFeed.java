package net.tensory.rxjavatalk.repositories;

import android.support.annotation.NonNull;
import android.util.Pair;

import net.tensory.rxjavatalk.models.Battle;
import net.tensory.rxjavatalk.models.House;
import net.tensory.rxjavatalk.models.HouseBattleResult;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class BattleFrontFeed {
    private static final int EMIT_RATE_SECONDS = 16;

    public enum Front {
        NORTHERN,
        SOUTHERN
    }

    private final DragonManager dragonManager;

    private BehaviorSubject<Battle> battleSubject = BehaviorSubject.create();

    public BattleFrontFeed(Front front, DragonManager dragonManager) {
        this.dragonManager = dragonManager;

        Observable<Battle> observable = Observable.interval(EMIT_RATE_SECONDS, TimeUnit.SECONDS)
                                                  .map(timeInterval -> new Battle(front, generateBattleResults()));

        if (front == Front.SOUTHERN) {
            observable = observable.delay(EMIT_RATE_SECONDS / 2, TimeUnit.SECONDS);
        }

        observable.subscribe(battle -> battleSubject.onNext(battle));
    }

    /**
     * Observable emitting a stream of Battle events.
     *
     * @return hot Observable
     */
    public Observable<Battle> observeBattles() {
        return battleSubject;
    }

    private Pair<HouseBattleResult, HouseBattleResult> generateBattleResults() {
        final Pair<Integer, Integer> combatants = getCombatants();

        House winningHouse = House.values()[combatants.first];
        House losingHouse = House.values()[combatants.second];

        dragonManager.considerAllegianceChange(winningHouse, losingHouse);

        return new Pair<>(
                generateHouseBattleResult(winningHouse),
                generateHouseBattleResult(losingHouse));
    }

    @NonNull
    private HouseBattleResult generateHouseBattleResult(House house) {
        return new HouseBattleResult(
                house,
                generateArmySize(house),
                dragonManager.getDragonCount(house));
    }

    private int generateArmySize(House house) {
        int origin = 2000;
        if (house == House.NIGHT_KING) {
            origin = 10000;
        }
        return ThreadLocalRandom.current().nextInt(origin, 200000);
    }

    private Pair<Integer, Integer> getCombatants() {
        final int first = ThreadLocalRandom.current().nextInt(0, House.values().length);
        int second;

        do {
            second = ThreadLocalRandom.current().nextInt(0, House.values().length);
        } while (second == first);

        return new Pair<>(first, second);
    }

}
