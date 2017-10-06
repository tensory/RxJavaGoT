package net.tensory.rxjavatalk.feed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.tensory.rxjavatalk.R;

import java.util.ArrayList;
import java.util.List;

public class BattleFeedAdapter extends RecyclerView.Adapter<BattleFeedAdapter.ViewHolder> {

    private final List<String> items = new ArrayList<>();

    public void updateItems(final List<String> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @Override
    public BattleFeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.battle_feed_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BattleFeedAdapter.ViewHolder holder, int position) {
        holder.textView.setText(items.get(position));
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


}

