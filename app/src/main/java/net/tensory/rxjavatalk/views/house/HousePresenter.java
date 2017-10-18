package net.tensory.rxjavatalk.views.house;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.graphics.drawable.Drawable;

import net.tensory.rxjavatalk.R;
import net.tensory.rxjavatalk.models.House;
import net.tensory.rxjavatalk.providers.BattleProvider;
import net.tensory.rxjavatalk.providers.CreditRatingProvider;
import net.tensory.rxjavatalk.providers.DebtProvider;

import io.reactivex.Observable;

class HousePresenter extends ViewModel {

    private final House house;
    private final BattleProvider battleProvider;
    private final DebtProvider debtProvider;
    private final CreditRatingProvider creditRatingProvider;

    HousePresenter(House house,
                   BattleProvider battleProvider,
                   DebtProvider debtProvider,
                   CreditRatingProvider creditRatingProvider) {
        this.house = house;
        this.battleProvider = battleProvider;
        this.debtProvider = debtProvider;
        this.creditRatingProvider = creditRatingProvider;
    }

    Observable<Integer> observeSoldiers() {
        return Observable.never();
    }

    Observable<Integer> observeDragons() {
        return Observable.never();
    }

    Observable<Double> observeRating() {
        return Observable.never();
    }

    public Observable<Double> observeDebt() {
        return Observable.never();
    }

    Drawable getShield(final Context context) {
        switch (house) {
            case STARK:
                return context.getResources().getDrawable(R.drawable.house_stark_shield, null);
            case TARGARYEN:
                return context.getResources().getDrawable(R.drawable.house_targaryen, null);
            case LANNISTER:
                return context.getResources().getDrawable(R.drawable.house_lannister_shield, null);
            case NIGHT_KING:
                return context.getResources().getDrawable(R.drawable.night_king, null);
        }

        return null;
    }
}
