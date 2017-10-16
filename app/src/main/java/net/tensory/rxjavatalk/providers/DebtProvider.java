package net.tensory.rxjavatalk.providers;

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
                return debtFeed.getLannisterDebt();
            case STARK:
                return debtFeed.getStarkDebt();
            case TARGARYEN:
                return debtFeed.getTargaryenDebt();
        }
        return Observable.empty();
    }
}
