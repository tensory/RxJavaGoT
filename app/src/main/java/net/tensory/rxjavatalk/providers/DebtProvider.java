package net.tensory.rxjavatalk.providers;

import android.util.Pair;

import net.tensory.rxjavatalk.data.DebtFeed;
import net.tensory.rxjavatalk.models.House;

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
        return Observable.empty();
    }
}
