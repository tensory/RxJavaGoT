package net.tensory.rxjavatalk.data;

import net.tensory.rxjavatalk.models.House;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class DragonManager {

    private final Object lock = new Object();

    private final Map<House, BehaviorSubject<Integer>> dragonCountSubjects = new HashMap<>();

    public DragonManager() {
        for (House house : House.values()) {
            final BehaviorSubject<Integer> subject = BehaviorSubject.create();
            subject.onNext(0);
            dragonCountSubjects.put(house, subject);
        }

        // Initialize the dragon count to 3 for Targaryn's
        dragonCountSubjects.get(House.TARGARYEN).onNext(3);
    }

    void considerAllegianceChange(House winningHouse, House losingHouse) {
        synchronized (lock) {
            final BehaviorSubject<Integer> winningSubject = dragonCountSubjects.get(winningHouse);
            final Integer currentWinningValue = winningSubject.getValue();

            final BehaviorSubject<Integer> losingSubject = dragonCountSubjects.get(losingHouse);
            final Integer currentLosingValue = losingSubject.getValue();
            final boolean losingHouseHasDragon = currentLosingValue > 0;

            if (losingHouseHasDragon && danyShouldLoseDragon(losingHouse)) {
                // Losing House loses a dragon and gives it to the winning house
                losingSubject.onNext(currentLosingValue - 1);

                // Woot, winning house gets a dragon!
                winningSubject.onNext(currentWinningValue + 1);
            }
        }
    }

    private boolean danyShouldLoseDragon(House losingHouse) {
        final double rollOfTheDice = ThreadLocalRandom.current().nextDouble(0.0, 1.0);
        return losingHouse != House.TARGARYEN || rollOfTheDice > 0.5;
    }

    public Observable<Integer> observeDragons(House house) {
        return dragonCountSubjects.get(house).toFlowable(BackpressureStrategy.LATEST).toObservable();
    }
}
