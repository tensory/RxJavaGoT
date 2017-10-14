package net.tensory.rxjavatalk.house;

import android.arch.lifecycle.ViewModelProvider;
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

    private HousePresenter presenter;
    private Disposable disposable;

    private TextView nameView;
    private TextView ratingView;
    private TextView debtView;
    private TextView soldiersView;
    private TextView dragonsView;

    private House house;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Assert.assertNotNull(getArguments());

        house = (House) getArguments().getSerializable(ARG_HOUSE);
        Assert.assertNotNull(house);

        presenter = ViewModelProviders.of(this, new HousePresenterFactory(house)).get(HousePresenter.class);
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


        nameView.setText(house.getHouseName());
        disposable = presenter.observeRating().subscribe(s -> ratingView.setText(s));
        disposable = presenter.observeDebt().subscribe(s -> debtView.setText(String.format(
                getString(R.string.double_format), s)));
        disposable = presenter.observeSoldiers().subscribe(s -> soldiersView.setText(String.format(
                getString(R.string.int_format), s)));
        disposable = presenter.observeDragons().subscribe(s -> dragonsView.setText(String.format(
                getString(R.string.int_format), s)));
    }

    @Override
    public void onStop() {
        disposable.dispose();

        super.onStop();
    }

    class HousePresenterFactory implements ViewModelProvider.Factory {

        private final House house;

        HousePresenterFactory(House house) {
            this.house = house;
        }

        @Override
        public HousePresenter create(Class modelClass) {
            return new HousePresenter(house);
        }
    }
}
