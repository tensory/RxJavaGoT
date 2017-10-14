package net.tensory.rxjavatalk.models;

import net.tensory.rxjavatalk.data.BattleFrontFeed;

import java.util.List;

public class Battle {

    private final BattleFrontFeed.Front front;
    private List<HouseBattleResult> houseBattleResults;


    public Battle(BattleFrontFeed.Front front, List<HouseBattleResult> houseBattleResults) {
        this.front = front;
        this.houseBattleResults = houseBattleResults;
    }

    public BattleFrontFeed.Front getFront() {
        return front;
    }

    public List<HouseBattleResult> getHouseBattleResults() {
        return houseBattleResults;
    }

    @Override
    public String toString() {
        return "Battle{" +
                "front=" + front +
                ", houseBattleResults=" + houseBattleResults +
                '}';
    }
}
