package net.tensory.rxjavatalk.providers;

import net.tensory.rxjavatalk.data.DebtFeed;
import net.tensory.rxjavatalk.models.DebtReport;
import net.tensory.rxjavatalk.models.House;
import net.tensory.rxjavatalk.models.Value;

import javax.inject.Inject;

import io.reactivex.Observable;

public class DebtProvider {

    private DebtFeed debtFeed;

    @Inject
    public DebtProvider(DebtFeed debtFeed) {
        this.debtFeed = debtFeed;
    }

    public Observable<Value<Double>> getLatestForHouse(House house) {
        switch (house) {
            case LANNISTER:
                return debtFeed.getDebtReports().map(DebtReport::getLannisterDebt);
            case STARK:
                return debtFeed.getDebtReports().map(DebtReport::getStarkDebt);
            case TARGARYEN:
                return debtFeed.getDebtReports().map(DebtReport::getTargaryenDebt);
            case NIGHT_KING:
                return debtFeed.getDebtReports().map(DebtReport::getNightKingDebt);
        }
        return Observable.empty();
    }
}
