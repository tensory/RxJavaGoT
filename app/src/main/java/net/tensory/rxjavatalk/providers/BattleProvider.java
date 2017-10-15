package net.tensory.rxjavatalk.providers;

import net.tensory.rxjavatalk.data.BattleFrontFeed;
import net.tensory.rxjavatalk.data.DragonManager;
import net.tensory.rxjavatalk.models.Battle;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class BattleProvider {

    private PublishSubject<Battle> battlesSubject = PublishSubject.create();

    public BattleProvider(DragonManager dragonManager) {
        BattleFrontFeed northernFrontFeed = new BattleFrontFeed(BattleFrontFeed.Front.NORTHERN, dragonManager);
        BattleFrontFeed southernFrontFeed = new BattleFrontFeed(BattleFrontFeed.Front.SOUTHERN, dragonManager);
        Observable.merge(
                northernFrontFeed.observeBattles(),
                southernFrontFeed.observeBattles()
                        .delay(2, TimeUnit.SECONDS)
        )
                .subscribe(battlesSubject::onNext);
    }

    /**
     * Observable emitting a stream of Battle events.
     *
     * @return hot Observable
     */
    public Observable<Battle> observeBattles() {
        return battlesSubject;
    }
}
