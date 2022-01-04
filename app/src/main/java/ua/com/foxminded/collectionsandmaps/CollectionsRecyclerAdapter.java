package ua.com.foxminded.collectionsandmaps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CollectionsRecyclerAdapter extends RecyclerView.Adapter<CollectionsRecyclerAdapter.CollectionsHolder> {

    private final List<Items> items = new ArrayList<>();

    public CollectionsRecyclerAdapter() {
    }

    public void setItems(List<Items> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CollectionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_collection_item, parent, false);
        return new CollectionsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionsHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class CollectionsHolder extends RecyclerView.ViewHolder {

        final private TextView arrayType;
        final private ProgressBar progressBar;
        final private TextView calcTime;

        public CollectionsHolder(@NonNull View itemView) {
            super(itemView);
            arrayType = itemView.findViewById(R.id.arrayType);
            progressBar = itemView.findViewById(R.id.progressBar);
            calcTime = itemView.findViewById(R.id.calcTime);
        }

        public void bind(@NonNull Items item) {
            arrayType.setText(itemView.getContext().getResources().getString(item.name));
            calcTime.setText(new StringBuilder().append(item.calcResults).append(" ").
                    append(itemView.getContext().getResources().getString(R.string.ms)));
            calcTime.animate().alpha(1).setDuration(500);
            setBooleanVisibility(item.progressBarFlag);
            progressBar.animate().alpha(1).setDuration(500);
        }

        public void setBooleanVisibility(boolean flag) {
            if (flag) {
                calcTime.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
                calcTime.setVisibility(View.VISIBLE);
            }
        }

    }
}