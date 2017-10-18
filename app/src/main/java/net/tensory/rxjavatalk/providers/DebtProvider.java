package net.tensory.rxjavatalk.providers;

import android.util.Pair;

import net.tensory.rxjavatalk.models.House;
import net.tensory.rxjavatalk.repositories.DebtFeed;

import javax.inject.Inject;

import io.reactivex.Observable;

public class DebtProvider {

    private DebtFeed debtFeed;

    @Inject
    public DebtProvider(DebtFeed debtFeed) {
        this.debtFeed = debtFeed;
    }

    public Observable<Double> observeDebt(House house) {
        switch (house) {
            case LANNISTER:
                return debtFeed.observeLannisterDebt();
            case STARK:
                return debtFeed.observeStarkDebt();
            case TARGARYEN:
                return debtFeed.observeTargaryenDebt();
        }
        return Observable.just(0.0);
    }

    public Observable<Pair<House, Double>> observeDebt() {
        return Observable.merge(
                observeDebt(House.LANNISTER)
                        .map(debtValue -> new Pair<>(House.LANNISTER, debtValue)),
                observeDebt(House.STARK)
                        .map(debtValue -> new Pair<>(House.STARK, debtValue)),
                observeDebt(House.TARGARYEN)
                        .map(debtValue -> new Pair<>(House.TARGARYEN, debtValue)));
    }
}
