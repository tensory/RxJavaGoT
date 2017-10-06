package net.tensory.rxjavatalk.providers;

import net.tensory.rxjavatalk.models.Battle;
import net.tensory.rxjavatalk.models.Combatant;
import net.tensory.rxjavatalk.models.House;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.reactivex.Observable;

public class BattleProvider {

    private final Random houseRandom = new Random(House.values().length);
    private final Random debtRandom = new Random(1000000);
    private final Random armySizeRandom = new Random(100000);
    private final Random dragonsRandom = new Random(4);


    /**
     * Observable emitting a stream of Battle events.
     *
     * @return hot Observable
     */
    public Observable<Battle> getBattlesFeed() {
        return Observable
                .timer(new Random(10).nextInt(), TimeUnit.MILLISECONDS)
                .timeInterval()
                .map(timeInterval -> new Battle(getTwoRandomCombatants()));
    }

    private List<Combatant> getTwoRandomCombatants() {
        final int firstIndex = houseRandom.nextInt();
        int secondIndex;

        do {
            secondIndex = houseRandom.nextInt();
        } while (secondIndex == firstIndex);

        return Stream.of(firstIndex, secondIndex)
                .map(enumIndex -> new Combatant(
                        House.values()[enumIndex],
                        debtRandom.nextDouble(),
                        armySizeRandom.nextInt(),
                        dragonsRandom.nextInt()
                ))
                .collect(Collectors.toList());
    }
}
