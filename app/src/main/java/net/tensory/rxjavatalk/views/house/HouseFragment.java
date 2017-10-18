package net.tensory.rxjavatalk.views.house;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import junit.framework.Assert;

import net.tensory.rxjavatalk.R;
import net.tensory.rxjavatalk.RxGotApplication;
import net.tensory.rxjavatalk.injection.AppComponent;
import net.tensory.rxjavatalk.models.House;

import java.text.NumberFormat;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class HouseFragment extends Fragment {

    public static final String ARG_HOUSE = "ARG_HOUSE";
    private static final long ANIMATION_DURATION = 8000L;

    private HousePresenter presenter;

    private CompositeDisposable compositeDisposable;

    private ImageView shieldView;
    private TextView nameView;
    private TextView ratingView;
    private TextView debtView;
    private TextView soldiersView;
    private TextView dragonsView;

    private House house;

    private NumberFormat doubleFormat = NumberFormat.getNumberInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final AppComponent appComponent = ((RxGotApplication) getActivity().getApplication()).getAppComponent();
        appComponent.inject(this);

        Assert.assertNotNull(getArguments());

        house = (House) getArguments().getSerializable(ARG_HOUSE);
        Assert.assertNotNull(house);

        final HousePresenterFactory presenterFactory =
                new HousePresenterFactory(house, appComponent);
        presenter = ViewModelProviders.of(this, presenterFactory).get(HousePresenter.class);

        doubleFormat.setMaximumFractionDigits(2);
        doubleFormat.setMinimumFractionDigits(2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.house_fragment, container, false);

        shieldView = view.findViewById(R.id.house_shield);
        nameView = view.findViewById(R.id.house_name);
        ratingView = view.findViewById(R.id.house_rating);
        debtView = view.findViewById(R.id.house_debt);
        soldiersView = view.findViewById(R.id.house_soldiers);
        dragonsView = view.findViewById(R.id.house_dragons);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        shieldView.setImageDrawable(presenter.getShield(getContext()));

        nameView.setText(house.getHouseName());

        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(presenter.observeRating()
                                         .map(rating -> doubleFormat.format(rating))
                                         .observeOn(AndroidSchedulers.mainThread())
                                         .subscribe(displayText -> ratingView.setText(displayText)));

        compositeDisposable.add(presenter.observeDebt()
                                         .map(debt -> {
                                             if (debt > 0) {
                                                 return doubleFormat.format(debt);
                                             } else {
                                                 return getString(R.string.en_dash);
                                             }
                                         })
                                         .observeOn(AndroidSchedulers.mainThread())
                                         .subscribe(displayText -> {
                                             debtView.setText(displayText);
                                             animateTextChange(debtView);
                                         }));

        compositeDisposable.add(presenter.observeSoldiers()
                                         .map(String::valueOf)
                                         .observeOn(AndroidSchedulers.mainThread())
                                         .subscribe(displayText -> {
                                             soldiersView.setText(displayText);
                                             animateTextChange(soldiersView);
                                         }));

        compositeDisposable.add(presenter.observeDragons()
                                         .map(String::valueOf)
                                         .observeOn(AndroidSchedulers.mainThread())
                                         .subscribe(displayText -> {
                                             dragonsView.setText(displayText);
                                             animateTextChange(dragonsView);
                                         }));
    }

    private void animateTextChange(TextView textView) {
        final int defaultTextColor = getResources().getColor(android.R.color.primary_text_light, null);

        final ObjectAnimator animator = ObjectAnimator.ofInt(textView, "textColor", Color.RED, defaultTextColor);
        animator.setDuration(ANIMATION_DURATION);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    @Override
    public void onStop() {
        compositeDisposable.dispose();

        super.onStop();
    }
}
