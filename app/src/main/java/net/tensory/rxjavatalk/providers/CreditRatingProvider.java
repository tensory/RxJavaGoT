package net.tensory.rxjavatalk.providers;

import net.tensory.rxjavatalk.models.CreditRating;
import net.tensory.rxjavatalk.models.HouseAssetRating;
import net.tensory.rxjavatalk.models.ShareholderRating;

import io.reactivex.Observable;

public class CreditRatingProvider {

    public Observable<CreditRating> getCreditRating(Observable<HouseAssetRating> houseAssetRating,
                                                    Observable<ShareholderRating> shareholderRating) {
        return Observable.combineLatest(
                houseAssetRating,
                shareholderRating,
                (assetProfileRating, shareholders) -> {
                    return 0.0;
                }
        ).map(CreditRating::new);
    }
}
