package net.tensory.rxjavatalk.house;

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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class HouseFragment extends Fragment {

    public static final String ARG_HOUSE = "ARG_HOUSE";
    private static final long ANIMATION_DURATION = 2000L;

    private HousePresenter presenter;
    private Disposable disposable;

    private ImageView shieldView;
    private TextView nameView;
    private TextView ratingView;
    private TextView debtView;
    private TextView soldiersView;
    private TextView dragonsView;

    private House house;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final AppComponent appComponent = ((RxGotApplication) getActivity().getApplication()).getAppComponent();
        appComponent.inject(this);

        Assert.assertNotNull(getArguments());

        house = (House) getArguments().getSerializable(ARG_HOUSE);
        Assert.assertNotNull(house);

        final HousePresenter.Factory presenterFactory =
                new HousePresenter.Factory(house, appComponent);
        presenter = ViewModelProviders.of(this, presenterFactory).get(HousePresenter.class);
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
        disposable = presenter.observeRating()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    ratingView.setText(s);
                    animateTextChange(ratingView);
                });
        disposable = presenter.observeDebt()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    debtView.setText(String.format(
                            getString(R.string.double_format), s));
                    animateTextChange(debtView);
                });
        disposable = presenter.observeSoldiers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    soldiersView.setText(String.format(
                            getString(R.string.int_format), s));
                    animateTextChange(soldiersView);
                });
        disposable = presenter.observeDragons()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    dragonsView.setText(String.format(
                            getString(R.string.int_format), s));
                    animateTextChange(dragonsView);
                });
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
        disposable.dispose();

        super.onStop();
    }
}
