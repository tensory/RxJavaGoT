package net.tensory.rxjavatalk.feed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.tensory.rxjavatalk.R;
import net.tensory.rxjavatalk.models.Battle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        holder.textView.setText(battle.getFront().name() + ":" + getBattleDescription(battle));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        private ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.feed_description);
        }
    }

    private String getBattleDescription(Battle battle) {
        List<String> names = battle.getHouseBattleResults()
                .stream()
                .map(houseBattleResult -> houseBattleResult.getHouse().getHouseName())
                .collect(Collectors.toList());
        return String.format(context.getResources().getString(R.string.battle_name_format),
                names.get(0), names.get(1));
    }

}

