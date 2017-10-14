package net.tensory.rxjavatalk.feed;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import net.tensory.rxjavatalk.injection.AppComponent;
import net.tensory.rxjavatalk.models.Battle;
import net.tensory.rxjavatalk.providers.BattleProvider;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class ActivityPresenter extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final AppComponent appComponent;

        public Factory(AppComponent appComponent) {
            this.appComponent = appComponent;
        }

        @Override
        public ActivityPresenter create(Class modelClass) {
            return new ActivityPresenter(
                    appComponent.providesBattles());
        }
    }

    private BehaviorSubject<Battle> eventFeed = BehaviorSubject.create();

    public ActivityPresenter(BattleProvider battleProvider) {
        battleProvider.observeBattles()
                .subscribe(battle -> eventFeed.onNext(battle));
    }

    public Observable<Battle> getEventFeed() {
        return eventFeed;
    }

}