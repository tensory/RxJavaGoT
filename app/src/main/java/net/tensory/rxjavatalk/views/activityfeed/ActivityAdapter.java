package net.tensory.rxjavatalk.views.activityfeed;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import junit.framework.Assert;

import net.tensory.rxjavatalk.R;
import net.tensory.rxjavatalk.models.Battle;
import net.tensory.rxjavatalk.models.House;
import net.tensory.rxjavatalk.models.HouseBattleResult;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class ActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Object> items = new ArrayList<>();

    void update(Object item) {
        Assert.assertTrue(item instanceof Battle || item instanceof Pair);

        items.add(0, item);
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        final Object item = items.get(position);
        return item instanceof Battle ? 0 : 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            final View view = LayoutInflater.from(parent.getContext())
                                            .inflate(R.layout.battle_feed_item, parent, false);
            return new BattleViewHolder(view);
        } else {
            final View view = LayoutInflater.from(parent.getContext())
                                            .inflate(R.layout.debt_feed_item, parent, false);
            return new DebtViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 0) {
            ((BattleViewHolder) holder).bind((Battle) items.get(position));
        } else {
            ((DebtViewHolder) holder).bind((Pair<House, Double>) items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private static class BattleViewHolder extends RecyclerView.ViewHolder {

        private TextView frontView;
        private TextView winnerView;
        private TextView winnerDescriptionView;
        private TextView loserView;
        private TextView loserDescriptionView;

        private BattleViewHolder(View view) {
            super(view);
            frontView = view.findViewById(R.id.battle_front);
            winnerView = view.findViewById(R.id.battle_winner);
            winnerDescriptionView = view.findViewById(R.id.battle_winner_description);
            loserView = view.findViewById(R.id.battle_loser);
            loserDescriptionView = view.findViewById(R.id.battle_loser_description);
        }

        private void bind(Battle battle) {
            frontView.setText(battle.getFront().name());
            winnerView.setText(battle.getWinner().getHouse().getHouseName());
            winnerDescriptionView.setText(getResultsDescription(battle.getWinner()));
            loserView.setText(battle.getLoser().getHouse().getHouseName());
            loserDescriptionView.setText(getResultsDescription(battle.getLoser()));
        }

        private String getResultsDescription(HouseBattleResult result) {
            return "Army: " + result.getCurrentArmySize()
                    + " Dragons: " + result.getCurrentDragonCount();
        }
    }

    private static class DebtViewHolder extends RecyclerView.ViewHolder {

        private NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);

        private TextView debtView;

        private DebtViewHolder(View view) {
            super(view);
            debtView = view.findViewById(R.id.debt_event);
            formatter.setMaximumFractionDigits(0);
            formatter.setGroupingUsed(true);
        }

        private void bind(Pair<House, Double> debtEvent) {
            debtView.setText(getResultsDescription(debtEvent));
        }

        private String getResultsDescription(Pair<House, Double> result) {
            return result.first.getHouseName()
                    + " current debt " + formatter.format(result.second);
        }
    }
}

