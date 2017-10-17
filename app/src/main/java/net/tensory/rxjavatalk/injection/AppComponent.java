package net.tensory.rxjavatalk.injection;

import net.tensory.rxjavatalk.providers.BattleProvider;
import net.tensory.rxjavatalk.providers.CreditRatingProvider;
import net.tensory.rxjavatalk.providers.DebtProvider;
import net.tensory.rxjavatalk.views.house.HouseFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(HouseFragment fragment);

    BattleProvider providesBattles();

    DebtProvider providesDebts();

    CreditRatingProvider providesCreditRatings();
}
