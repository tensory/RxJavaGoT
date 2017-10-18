package net.tensory.rxjavatalk.views.activityfeed;

import android.arch.lifecycle.ViewModelProvider;

import net.tensory.rxjavatalk.injection.AppComponent;

public class ActivityPresenterFactory implements ViewModelProvider.Factory {

    private final AppComponent appComponent;

    ActivityPresenterFactory(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

    @Override
    public ActivityPresenter create(Class modelClass) {
        return new ActivityPresenter(
                appComponent.providesBattles(), appComponent.providesDebts());
    }
}
