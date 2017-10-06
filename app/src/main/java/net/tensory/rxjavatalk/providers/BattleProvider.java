package net.tensory.rxjavatalk.providers;

import net.tensory.rxjavatalk.models.Battle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

public class BattleProvider {

    public Observable<Battle> getWesterosBattles(Observable<Battle>... battles) {
        return Observable.merge(battles[0], battles[1], battles3);
    }

    public Observable<Battle> getSeaBattles(Observable<Battle> battles, Observable<Battle> battles2, Observable<Battle> battles3) {
        return Observable.merge(battles, battles2, battles3);
    }
}
