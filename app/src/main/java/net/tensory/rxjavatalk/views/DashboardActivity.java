package net.tensory.rxjavatalk.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import net.tensory.rxjavatalk.R;
import net.tensory.rxjavatalk.models.House;
import net.tensory.rxjavatalk.views.activityfeed.ActivityFragment;
import net.tensory.rxjavatalk.views.house.HouseFragment;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.house_stark, createHouseFragment(House.STARK));
        fragmentTransaction.replace(R.id.house_targaryen, createHouseFragment(House.TARGARYEN));
        fragmentTransaction.replace(R.id.house_lannister, createHouseFragment(House.LANNISTER));
        fragmentTransaction.replace(R.id.house_night_king, createHouseFragment(House.NIGHT_KING));

        fragmentTransaction.replace(R.id.battle_feed_fragment, new ActivityFragment());

        fragmentTransaction.commit();
    }

    @NonNull
    private Fragment createHouseFragment(House house) {
        final Fragment fragment = new HouseFragment();
        final Bundle args = new Bundle();
        args.putSerializable(HouseFragment.ARG_HOUSE, house);
        fragment.setArguments(args);
        return fragment;
    }
}
