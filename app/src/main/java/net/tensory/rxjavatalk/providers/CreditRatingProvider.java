package net.tensory.rxjavatalk.providers;

import net.tensory.rxjavatalk.models.CreditRating;
import net.tensory.rxjavatalk.models.HouseAssetRating;
import net.tensory.rxjavatalk.models.ShareholderRating;

import io.reactivex.Observable;

public class CreditRatingProvider {

    // Example 1: Empty observable, nothing to emit yet
    public Observable<CreditRating> getCreditRating(Observable<Double> debt,
                                                    Observable<HouseAssetRating> houseAssetRating,
                                                    Observable<ShareholderRating> shareholderRating) {
        return Observable.empty();
    }


//    public Observable<CreditRating> getCreditRating(Observable<Double> debt,
//                                                    Observable<HouseAssetRating> houseAssetRating,
//                                                    Observable<ShareholderRating> shareholderRating) {
//        return Observable.combineLatest(
//                debt,
//                houseAssetRating,
//                shareholderRating,
//                (houseDebt, assetProfileRating, shareholders) -> {
//                    double debtRatio = (houseDebt == 0) ? 1 : houseDebt / DebtProvider.MAX_DEBT_GOLD;
//                    return ((debtRatio * assetProfileRating.getValue()) / 10) / DebtProvider.MAX_DEBT_GOLD
//                            + shareholders.getValue();
//                }
//        ).map(CreditRating::new);
//    }
}
