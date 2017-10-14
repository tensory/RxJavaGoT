package net.tensory.rxjavatalk.data;

import android.support.annotation.NonNull;
import android.util.Pair;

import net.tensory.rxjavatalk.models.Battle;
import net.tensory.rxjavatalk.models.House;
import net.tensory.rxjavatalk.models.HouseBattleResult;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class BattleFrontFeed {

    public enum Front {
        NORTHERN,
        SOUTHERN
    }

    private final Front front;
    private final DragonManager dragonManager;

    public BattleFrontFeed(Front front, DragonManager dragonManager) {
        this.front = front;
        this.dragonManager = dragonManager;
    }

    /**
     * Observable emitting a stream of Battle events.
     *
     * @return hot Observable
     */
    public Observable<Battle> observeBattles() {
        return Observable
                .interval(10, TimeUnit.SECONDS)
                .delay(ThreadLocalRandom.current().nextInt(0, 10), TimeUnit.SECONDS)
                .map(timeInterval -> new Battle(front, generateBattleResults()));
    }

    private List<HouseBattleResult> generateBattleResults() {
        final Pair<Integer, Integer> compatants = getCompatants();

        House winningHouse = House.values()[compatants.first];
        House losingHouse = House.values()[compatants.second];

        dragonManager.considerAllegienceChange(winningHouse, losingHouse);

        return Arrays.asList(
                generateHouseBattleResult(winningHouse),
                generateHouseBattleResult(losingHouse));
    }

    @NonNull
    private HouseBattleResult generateHouseBattleResult(House house) {
        return new HouseBattleResult(
                house,
                generateArmySize(),
                dragonManager.getDragonCount(house));
    }

    private int generateArmySize() {
        return ThreadLocalRandom.current().nextInt(2000, 200000);
    }

    private Pair<Integer, Integer> getCompatants() {
        final int first = ThreadLocalRandom.current().nextInt(0, House.values().length);
        int second;

        do {
            second = ThreadLocalRandom.current().nextInt(0, House.values().length);
        } while (second == first);

        return new Pair<>(first, second);
    }

}
