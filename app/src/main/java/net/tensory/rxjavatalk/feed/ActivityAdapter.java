package net.tensory.rxjavatalk.feed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.tensory.rxjavatalk.R;
import net.tensory.rxjavatalk.models.Battle;
import net.tensory.rxjavatalk.models.HouseBattleResult;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private final List<Battle> items = new ArrayList<>();
    private final Context context;

    public ActivityAdapter(Context context) {
        this.context = context;
    }

    public void update(final Battle battle) {
        items.add(0, battle);
        notifyItemInserted(0);
    }

    @Override
    public ActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.battle_feed_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivityAdapter.ViewHolder holder, int position) {
        final Battle battle = items.get(position);
        holder.frontView.setText(battle.getFront().name());
        holder.winnerView.setText(battle.getWinner().getHouse().getHouseName());
        holder.winnerDescriptionView.setText(getResultsDescription(battle.getWinner()));
        holder.loserView.setText(battle.getLoser().getHouse().getHouseName());
        holder.loserDescriptionView.setText(getResultsDescription(battle.getLoser()));
    }

    private String getResultsDescription(HouseBattleResult result) {
        return "Army: " + result.getCurrentArmySize()
                + " Dragons: " + result.getCurrentDragonCount();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView frontView;
        private TextView winnerView;
        private TextView winnerDescriptionView;
        private TextView loserView;
        private TextView loserDescriptionView;

        private ViewHolder(View view) {
            super(view);
            frontView = view.findViewById(R.id.battle_front);
            winnerView = view.findViewById(R.id.battle_winner);
            winnerDescriptionView = view.findViewById(R.id.battle_winner_description);
            loserView = view.findViewById(R.id.battle_loser);
            loserDescriptionView = view.findViewById(R.id.battle_loser_description);
        }
    }
}

