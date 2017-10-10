package net.tensory.rxjavatalk.house;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import junit.framework.Assert;

import net.tensory.rxjavatalk.R;
import net.tensory.rxjavatalk.models.House;

import io.reactivex.disposables.Disposable;

public class HouseFragment extends Fragment {

    public static final String ARG_HOUSE = "ARG_HOUSE";

    private HouseViewModel viewModel;
    private Disposable disposable;

    private TextView nameView;
    private TextView ratingView;
    private TextView debtView;
    private TextView soldiersView;
    private TextView dragonsView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(HouseViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.house_fragment, container, false);

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

        Assert.assertNotNull(getArguments());

        final House house = (House) getArguments().getSerializable(ARG_HOUSE);
        Assert.assertNotNull(house);

        nameView.setText(house.getHouseName());
        disposable = viewModel.observeRating().subscribe(s -> ratingView.setText(s));
        disposable = viewModel.observeDebt().subscribe(s -> debtView.setText(s));
        disposable = viewModel.observeSoldiers().subscribe(s -> soldiersView.setText(s));
        disposable = viewModel.observeDragons().subscribe(s -> dragonsView.setText(s));
    }

    @Override
    public void onStop() {
        disposable.dispose();

        super.onStop();
    }
}
