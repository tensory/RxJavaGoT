package net.tensory.rxjavatalk.feed;

import android.arch.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class BattleFeedViewModel extends ViewModel {

    private BehaviorSubject<List<String>> eventFeed = BehaviorSubject.create();

    public BattleFeedViewModel() {
        eventFeed.onNext(Arrays.asList("this", "is", "an", "example", "of rx!"));
    }

    public Observable<List<String>> getEventFeed() {
        return eventFeed;
    }

}