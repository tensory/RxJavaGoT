package net.tensory.rxjavatalk.models;

import android.util.Pair;

import net.tensory.rxjavatalk.data.BattleFrontFeed;

public class Battle {

    private final BattleFrontFeed.Front front;
    private final HouseBattleResult winner;
    private final HouseBattleResult loser;

    public Battle(BattleFrontFeed.Front front,
                  Pair<HouseBattleResult, HouseBattleResult> battleResult) {
        this.front = front;
        this.winner = battleResult.first;
        this.loser = battleResult.second;
    }

    public BattleFrontFeed.Front getFront() {
        return front;
    }

    public HouseBattleResult getWinner() {
        return winner;
    }

    public HouseBattleResult getLoser() {
        return loser;
    }

    @Override
    public String toString() {
        return "Battle{" +
                "front=" + front +
                ", winner=" + winner +
                ", loser=" + loser +
                '}';
    }
}
