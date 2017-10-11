package net.tensory.rxjavatalk.providers;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ShareholderRatingProvider {
    private ShareholderRating shareholderRating;
    private PublishSubject<Double> rating = PublishSubject.create();

    public ShareholderRatingProvider() {
        shareholderRating = new ShareholderRating();
        rating.onNext(shareholderRating.getPositiveRatio());
    }

    public void addVote(boolean isPositive) {
        shareholderRating.addVote(isPositive);
        this.rating.onNext(shareholderRating.getPositiveRatio());
    }

    public Observable<Double> getPositiveRating() {
        return rating;
    }

    private final class ShareholderRating {
        private int positive;
        private int total;

        public ShareholderRating() {
            positive = 0;
            total = 0;

        }

        public void addVote(boolean positive) {
            if (positive) {
                this.positive += 1;
            }
            total += 1;
        }

        public double getPositiveRatio() {
            return positive / total * 1.0;
        }
    }
}
