package net.tensory.rxjavatalk.data;

import net.tensory.rxjavatalk.models.House;

import java.util.HashMap;
import java.util.Map;

public class DragonManager {

    public enum Dragon {
        DROGON,
        RHAEGAL,
        VISERION,
    }

    private Map<Dragon, House> currentAllegiance = new HashMap<>();

    public DragonManager() {
        currentAllegiance.put(Dragon.DROGON, House.TARGARYEN);
        currentAllegiance.put(Dragon.RHAEGAL, House.TARGARYEN);
        currentAllegiance.put(Dragon.VISERION, House.TARGARYEN);
    }

    public void considerAllegianceChange(House winningHouse, House losingHouse) {
        if (!currentAllegiance.containsValue(losingHouse)) {
            return;
        }

        for (Map.Entry<Dragon, House> entry : currentAllegiance.entrySet()) {
            if (entry.getValue() == losingHouse) {
                currentAllegiance.put(entry.getKey(), winningHouse);
                return;
            }
        }
    }

    public int getDragonCount(House house) {
        int count = 0;

        for (Map.Entry<Dragon, House> entry : currentAllegiance.entrySet()) {
            if (entry.getValue() == house) {
                count += 1;
            }
        }

        return count;
    }
}
