package net.tensory.rxjavatalk.providers;

import net.tensory.rxjavatalk.models.House;
import net.tensory.rxjavatalk.models.HouseAssetProfile;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by ari on 10/12/17.
 */

public class HouseAssetProfileProvider {
    private final PublishSubject<HouseAssetProfile> houseAssetProfileSubject = PublishSubject.create();

    public void publishHouseAssetProfile(House house, int armySize, int dragonCount) {
        houseAssetProfileSubject.onNext(new HouseAssetProfile(house, armySize, dragonCount));
    }

    public Observable<HouseAssetProfile> getHouseAssetProfileStream() {
        return houseAssetProfileSubject;
    }
}
