package net.tensory.rxjavatalk.providers;

import android.util.Pair;

import net.tensory.rxjavatalk.data.DebtFeed;
import net.tensory.rxjavatalk.models.House;

import javax.inject.Inject;

import io.reactivex.Observable;

public class DebtProvider {

    public static final double MAX_DEBT_GOLD = 1000000;
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
        return Observable.empty();
    }

    public Observable<Pair<House, Double>> observeDebt() {
        return Observable.merge(
                debtFeed.observeLannisterDebt()
                        .map(debtValue -> new Pair<>(House.LANNISTER, debtValue)),
                debtFeed.observeStarkDebt()
                        .map(debtValue -> new Pair<>(House.STARK, debtValue)),
                debtFeed.observeTargaryenDebt()
                        .map(debtValue -> new Pair<>(House.TARGARYEN, debtValue)));
    }
}
