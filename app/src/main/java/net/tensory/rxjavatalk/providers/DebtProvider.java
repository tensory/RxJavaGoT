package net.tensory.rxjavatalk.providers;

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

    public Observable<Double> getLatestForHouse(House house) {
        switch (house) {
            case LANNISTER:
                return debtFeed.getLannisterDebt();
            case STARK:
                return debtFeed.getStarkDebt();
            case TARGARYEN:
                return debtFeed.getTargaryenDebt();
        }
        return Observable.empty();
    }
}
