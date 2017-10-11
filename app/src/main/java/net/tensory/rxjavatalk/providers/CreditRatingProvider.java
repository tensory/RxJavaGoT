package net.tensory.rxjavatalk.providers;

import net.tensory.rxjavatalk.models.HouseAssetProfile;

import io.reactivex.Observable;

public class CreditRatingProvider {

    public Observable<Double> getCreditRating(HouseAssetProfile houseAssetProfile,
                                              Observable<Double> shareholderRating) {
        return Observable.combineLatest(
                HouseAssetProfile.getAssetRating(houseAssetProfile),
                shareholderRating,
                (assetProfileRating, shareholders) -> {
                    return 0.0;
                }
        );
    }
}
