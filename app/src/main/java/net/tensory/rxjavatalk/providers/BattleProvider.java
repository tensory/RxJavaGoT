package net.tensory.rxjavatalk.providers;

import net.tensory.rxjavatalk.models.Combatant;
import net.tensory.rxjavatalk.models.House;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BattleProvider {

    private final Random houseRandom = new Random(House.values().length);
    private final Random debtRandom = new Random(1000000);
    private final Random armySizeRandom = new Random(100000);
    private final Random dragonsRandom = new Random(4);

    private List<Combatant> getRandomCombatants() {
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
