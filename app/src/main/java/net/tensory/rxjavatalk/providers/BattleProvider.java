package net.tensory.rxjavatalk.providers;

import net.tensory.rxjavatalk.data.BattleFrontFeed;
import net.tensory.rxjavatalk.data.DragonManager;
import net.tensory.rxjavatalk.models.Battle;
import net.tensory.rxjavatalk.models.House;
import net.tensory.rxjavatalk.models.HouseBattleResult;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;

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
        return Observable.merge(northernFrontFeed.observeBattles(), southernFrontFeed.observeBattles());
    }
}
