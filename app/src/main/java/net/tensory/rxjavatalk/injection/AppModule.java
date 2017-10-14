package net.tensory.rxjavatalk.injection;

import android.app.Application;
import android.content.Context;

import net.tensory.rxjavatalk.providers.BattleProvider;
import net.tensory.rxjavatalk.providers.CreditRatingProvider;
import net.tensory.rxjavatalk.providers.HouseAssetProfileProvider;
import net.tensory.rxjavatalk.providers.ShareholderRatingProvider;

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
    public BattleProvider provideBattles() {
        return new BattleProvider();
    }

    @Provides
    @Singleton
    public HouseAssetProfileProvider provideHouseAssetProfiles() {
        return new HouseAssetProfileProvider();
    }

    @Provides
    @Singleton
    public CreditRatingProvider provideCreditRatings() {
        return new CreditRatingProvider();
    }

    @Provides
    @Singleton
    public ShareholderRatingProvider provideShareholderRatings() {
        return new ShareholderRatingProvider();
    }
}
