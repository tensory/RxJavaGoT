package net.tensory.rxjavatalk.injection;

import android.app.Application;
import android.content.Context;

import net.tensory.rxjavatalk.providers.BattleProvider;
import net.tensory.rxjavatalk.providers.CreditRatingProvider;
import net.tensory.rxjavatalk.providers.DebtProvider;
import net.tensory.rxjavatalk.repositories.DebtFeed;
import net.tensory.rxjavatalk.repositories.DragonManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }


    @Provides
    @Singleton
    public DragonManager provideDragonManager() {
        return new DragonManager();
    }

    @Provides
    @Singleton
    public DebtFeed provideDebtFeed() {
        return new DebtFeed();
    }

    @Provides
    @Singleton
    public BattleProvider provideBattles(DragonManager dragonManager) {
        return new BattleProvider(dragonManager);
    }

    @Provides
    @Singleton
    public CreditRatingProvider provideCreditRatings(BattleProvider battleProvider, DebtProvider debtProvider) {
        return new CreditRatingProvider(battleProvider, debtProvider);
    }

}
