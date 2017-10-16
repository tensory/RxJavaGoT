package net.tensory.rxjavatalk.injection;

import net.tensory.rxjavatalk.house.HouseFragment;
import net.tensory.rxjavatalk.providers.BattleProvider;
import net.tensory.rxjavatalk.providers.DebtProvider;
import net.tensory.rxjavatalk.providers.HouseAssetProfileProvider;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(HouseFragment fragment);

    HouseAssetProfileProvider providesHouseAssetProfile();

    BattleProvider providesBattles();

    DebtProvider providesDebts();
}
