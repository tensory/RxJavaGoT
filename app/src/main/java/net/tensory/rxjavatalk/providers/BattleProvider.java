package net.tensory.rxjavatalk.providers;

import net.tensory.rxjavatalk.models.Battle;
import net.tensory.rxjavatalk.repositories.BattleFrontFeed;
import net.tensory.rxjavatalk.repositories.DragonManager;

import io.reactivex.Observable;

public class BattleProvider {

    private final BattleFrontFeed northernFrontFeed;
    private final BattleFrontFeed southernFrontFeed;

    public BattleProvider(DragonManager dragonManager) {
        northernFrontFeed = new BattleFrontFeed(BattleFrontFeed.Front.NORTHERN, dragonManager);
        southernFrontFeed = new BattleFrontFeed(BattleFrontFeed.Front.SOUTHERN, dragonManager);
    }

    /**
     * Observable emitting a stream of Battle events.
     *
     * @return hot Observable
     */
    public Observable<Battle> observeBattles() {
        return northernFrontFeed.observeBattles();
    }
}
