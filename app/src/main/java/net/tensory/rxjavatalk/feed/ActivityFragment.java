package net.tensory.rxjavatalk.feed;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.tensory.rxjavatalk.R;

import io.reactivex.disposables.Disposable;

public class ActivityFragment extends Fragment {

    private ActivityAdapter adapter;
    private ActivityPresenter viewModel;
    private Disposable disposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(ActivityPresenter.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.battle_feed_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.event_feed_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ActivityAdapter(getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        disposable = viewModel.getEventFeed().subscribe(battle -> adapter.update(battle));
    }

    @Override
    public void onStop() {
        disposable.dispose();

        super.onStop();
    }
}
