package net.tensory.rxjavatalk.data;

import android.util.Log;

import net.tensory.rxjavatalk.models.DebtReport;
import net.tensory.rxjavatalk.models.House;
import net.tensory.rxjavatalk.models.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class DebtFeed {

    private static final double MAX_DEBT_GOLD = 1000000;
    private PublishSubject<Object> didUpdateValuesSubject = PublishSubject.create();
    private PublishSubject<DebtReport> debtReportSubject = PublishSubject.create();

    private Map<House, Value<Double>> debts = new HashMap<>();

    public DebtFeed() {
        Log.i(DebtFeed.class.getName(), "Instantiating debtFeed");
        for (House house : House.values()) {
            debts.put(house, new Value<>(0.0));
        }

        getTimedEmittedValue(3)
                .doOnNext(didUpdateValuesSubject::onNext)
                .subscribe(valueDouble -> debts.put(House.LANNISTER, valueDouble));

        getTimedEmittedValue(14)
                .doOnNext(didUpdateValuesSubject::onNext)
                .subscribe(valueDouble -> debts.put(House.TARGARYEN, valueDouble));

        getTimedEmittedValue(8)
                .doOnNext(didUpdateValuesSubject::onNext)
                .subscribe(valueDouble -> debts.put(House.STARK, valueDouble));

        // White Walkers don't know what money is, so there's no need to count their debts...

        debtReportSubject.doOnNext(s -> Log.i(DebtFeed.class.getName(), "Did emit debt report"));

        didUpdateValuesSubject.subscribe(o -> debtReportSubject.onNext(emitDebtReport()), throwable -> {});

    }

    public Observable<DebtReport> getDebtReports() {
        return debtReportSubject;
    }

    private Observable<Value<Double>> getTimedEmittedValue(long intervalSeconds) {
        return Observable.interval(intervalSeconds, TimeUnit.SECONDS)
                .map(timeInterval ->
                        new Value<>(ThreadLocalRandom.current().nextDouble(MAX_DEBT_GOLD)));
    }

    private DebtReport emitDebtReport() {
        return new DebtReport(debts.get(House.LANNISTER),
                debts.get(House.TARGARYEN),
                debts.get(House.STARK),
                debts.get(House.NIGHT_KING));
    }
}
