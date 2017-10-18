package net.tensory.rxjavatalk.views.house;

import android.arch.lifecycle.ViewModelProvider;

import net.tensory.rxjavatalk.injection.AppComponent;
import net.tensory.rxjavatalk.models.House;

public class HousePresenterFactory implements ViewModelProvider.Factory {

    private final House house;
    private final AppComponent appComponent;

    HousePresenterFactory(House house, AppComponent appComponent) {
        this.house = house;
        this.appComponent = appComponent;
    }

    @Override
    public HousePresenter create(Class modelClass) {
        return new HousePresenter(
                house,
                appComponent.providesBattles(),
                appComponent.providesDebts(),
                appComponent.providesCreditRatings()
        );
    }
}
