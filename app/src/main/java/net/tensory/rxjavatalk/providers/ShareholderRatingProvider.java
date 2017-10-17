package net.tensory.rxjavatalk.providers;

import net.tensory.rxjavatalk.models.ShareholderRating;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ShareholderRatingProvider {
    private Ratio ratio;
    private PublishSubject<Double> rating = PublishSubject.create();

    public ShareholderRatingProvider() {
        ratio = new Ratio();
        rating.onNext(ratio.getRatio());
    }

    public void addVote(boolean isPositive) {
        ratio.increment(isPositive);
        this.rating.onNext(ratio.getRatio());
    }

    public Observable<ShareholderRating> observePositiveRating() {
        return rating.map(ShareholderRating::new);
    }

    private final class Ratio {
        private int numerator;
        private int denominator;

        public Ratio() {
            numerator = 0;
            denominator = 0;

        }

        public void increment(boolean positive) {
            if (positive) {
                this.numerator += 1;
            }
            denominator += 1;
        }

        public double getRatio() {
            return numerator / denominator * 1.0;
        }
    }
}
