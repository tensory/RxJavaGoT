package net.tensory.rxjavatalk;

import android.app.Application;

import net.tensory.rxjavatalk.injection.AppComponent;
import net.tensory.rxjavatalk.injection.AppModule;
import net.tensory.rxjavatalk.injection.DaggerAppComponent;

public class RxGotApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = initDagger(this);
    }

    private AppComponent initDagger(RxGotApplication application) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
