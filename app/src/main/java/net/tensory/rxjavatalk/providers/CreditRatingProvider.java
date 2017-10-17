package net.tensory.rxjavatalk.providers;

import net.tensory.rxjavatalk.models.Battle;
import net.tensory.rxjavatalk.models.House;
import net.tensory.rxjavatalk.models.HouseBattleResult;
import net.tensory.rxjavatalk.models.ShareholderRating;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class CreditRatingProvider {

    private static final Double MAX_DEBT_GOLD = 1000000.0;

    private final BehaviorSubject<ShareholderRating> shareholderRatingSubject =
            BehaviorSubject.createDefault(new ShareholderRating(0.0));

    private Map<House, BehaviorSubject<Double>> ratingsSubjects = new HashMap<>();

    public CreditRatingProvider(BattleProvider battleProvider, DebtProvider debtProvider) {
        for (House house : House.values()) {
            final BehaviorSubject<Double> behaviorSubject = BehaviorSubject.create();
            ratingsSubjects.put(house, behaviorSubject);

            Observable.combineLatest(
                    debtProvider.observeDebt(house), observeAssetRating(house, battleProvider), shareholderRatingSubject, this::calculateCreditRating)
                      .subscribe(behaviorSubject::onNext);
        }

    }

    private Observable<Double> observeAssetRating(House house, BattleProvider battleProvider) {
        return battleProvider.observeBattles()
                             .map(battle -> getHouseBattleResult(house, battle))
                             .map(this::calculateAssetRating);
    }

    private HouseBattleResult getHouseBattleResult(House house, Battle battle) {
        boolean isWinningHouse = house.equals(battle.getWinner().getHouse());
        return isWinningHouse ? battle.getWinner() : battle.getLoser();
    }

    private Double calculateCreditRating(double houseDebt, double assetRating, ShareholderRating shareholders) {
        double debtRatio = (houseDebt == 0) ? 1 : houseDebt / MAX_DEBT_GOLD;

        final double liabilitiesRating = ((debtRatio * assetRating) / 10) / MAX_DEBT_GOLD;

        return liabilitiesRating + shareholders.getValue();
    }

    private Double calculateAssetRating(HouseBattleResult houseAssets) {
        return houseAssets.getCurrentArmySize() * 0.35 + houseAssets.getCurrentDragonCount() * 10000;
    }

    public Observable<Double> observeCreditRating(House house) {
        // Example 1: Empty observable, nothing to emit yet
//        return Observable.empty();
        return ratingsSubjects.get(house).toFlowable(BackpressureStrategy.LATEST).toObservable();
    }
}
