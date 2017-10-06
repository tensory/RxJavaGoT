package net.tensory.rxjavatalk.feed;

import android.arch.lifecycle.ViewModel;

import net.tensory.rxjavatalk.models.Battle;
import net.tensory.rxjavatalk.providers.BattleProvider;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class BattleFeedViewModel extends ViewModel {

    private BehaviorSubject<Battle> eventFeed = BehaviorSubject.create();

    public BattleFeedViewModel() {

        // Merge example
        Observable.merge(new BattleProvider().getBattlesFeed(),
                new BattleProvider().getBattlesFeed(),
                new BattleProvider().getBattlesFeed())
                .subscribe(battle -> eventFeed.onNext(
                        battle
                ), throwable -> {
                });
    }

    public Observable<Battle> getEventFeed() {
        return eventFeed;
    }

}