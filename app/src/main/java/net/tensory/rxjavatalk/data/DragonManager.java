package net.tensory.rxjavatalk.data;

import net.tensory.rxjavatalk.models.House;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class DragonManager {

    private final Object lock = new Object();

    private final Map<House, Integer> dragonCounts = new HashMap<>();

    public DragonManager() {
        for (House house : House.values()) {
            dragonCounts.put(house, 0);
        }

        // Initialize the dragon count to 3 for Targaryn's
        dragonCounts.put(House.TARGARYEN, 3);
    }

    void considerAllegianceChange(House winningHouse, House losingHouse) {
        synchronized (lock) {
            final Integer currentWinningValue = dragonCounts.get(winningHouse);

            final Integer currentLosingValue = dragonCounts.get(losingHouse);
            final boolean losingHouseHasDragon = currentLosingValue > 0;

            if (losingHouseHasDragon) {
                if (losingHouse == House.TARGARYEN) {
                    final double rollOfTheDice = ThreadLocalRandom.current().nextDouble(0.0, 1.0);

                    // Targaryn's only lose a dragon in extreme odds
                    if (rollOfTheDice > 0.8) {
                        switch (winningHouse) {
                            case STARK:
                            case NIGHT_KING:
                                dragonCounts.put(House.TARGARYEN, currentLosingValue - 1);

                                // Only Starks and Night King can gain a dragon, because they're awesome like that
                                dragonCounts.put(winningHouse, currentWinningValue + 1);
                                break;
                            case LANNISTER:
                                // Lannister's killed a dragon, NOOOOOOOOO!!!!!!
                                dragonCounts.put(House.TARGARYEN, currentLosingValue - 1);
                                break;
                        }
                    }
                } else {
                    dragonCounts.put(losingHouse, currentLosingValue - 1);
                    dragonCounts.put(winningHouse, currentWinningValue + 1);
                }
            }
        }
    }

    int getDragonCount(House house) {
        return dragonCounts.get(house);
    }
}
