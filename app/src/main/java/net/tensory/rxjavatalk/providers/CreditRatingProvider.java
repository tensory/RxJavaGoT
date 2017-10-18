package net.tensory.rxjavatalk.providers;

import net.tensory.rxjavatalk.models.Battle;
import net.tensory.rxjavatalk.models.House;
import net.tensory.rxjavatalk.models.HouseBattleResult;

import io.reactivex.Observable;

public class CreditRatingProvider {

    private static final Double MAX_DEBT_GOLD = 100000.0;

    private BattleProvider battleProvider;
    private DebtProvider debtProvider;

    public CreditRatingProvider(BattleProvider battleProvider, DebtProvider debtProvider) {
        setupSubscription(battleProvider, debtProvider);
    }

    private void setupSubscription(BattleProvider battleProvider, DebtProvider debtProvider) {
        this.battleProvider = battleProvider;
        this.debtProvider = debtProvider;
    }

    public Observable<Double> observeCreditRating(House house) {
        return Observable.combineLatest(
                debtProvider.observeDebt(house),
                observeAssetRating(house, battleProvider),
                this::calculateCreditRating);
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

    private Double calculateAssetRating(HouseBattleResult houseAssets) {
        return houseAssets.getCurrentArmySize() * 0.35 + houseAssets.getCurrentDragonCount() * 10000;
    }

    private Double calculateCreditRating(double houseDebt, double assetRating) {
        double debtRatio = Math.abs((MAX_DEBT_GOLD - houseDebt) / MAX_DEBT_GOLD);

        if (houseDebt == 0.0) {
            debtRatio = 0.5;
        }

        final double liabilitiesRating = (debtRatio * assetRating) / MAX_DEBT_GOLD;

        return liabilitiesRating * 10;
    }
}
