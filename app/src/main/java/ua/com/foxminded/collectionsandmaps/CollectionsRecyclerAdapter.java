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

public class CollectionsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Items> items = new ArrayList<>();
    private final List<Integer> operations = new ArrayList<>();

    public CollectionsRecyclerAdapter() {
    }

    public void setItems(List<Items> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    public void setOperations(List<Integer> operations) {
        this.operations.addAll(operations);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_benchmark, parent, false);
                return new CollectionsHolder(view);
        } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_operation, parent, false);
                return new OperationsHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                CollectionsHolder collectionsHolder = (CollectionsHolder)holder;
                collectionsHolder.bind(items.get(position));
                break;
            case 1:
                OperationsHolder operationsHolder = (OperationsHolder)holder;
                operationsHolder.operation.setText(operations.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class CollectionsHolder extends RecyclerView.ViewHolder {

        final private TextView arrayType;
        final private ProgressBar progressBar;
        final private TextView calcTime;
        final private int shortAnimationDuration = itemView.getResources().getInteger(android.R.integer.config_shortAnimTime);

        public CollectionsHolder(@NonNull View itemView) {
            super(itemView);
            arrayType = itemView.findViewById(R.id.arrayType);
            progressBar = itemView.findViewById(R.id.progressBar);
            calcTime = itemView.findViewById(R.id.calcTime);
        }

        public void bind(@NonNull Items item) {
            arrayType.setText(itemView.getContext().getResources().getString(item.name));
            calcTime.setText(itemView.getContext().getResources()
                    .getString(R.string.ms, item.calcResults));
            setupVisibility(item.progressBarFlag);
        }

        public void setupVisibility(boolean isVisible) {
            final float progressTargetAlpha = isVisible ? 1F : 0F;
            if (progressTargetAlpha != progressBar.getAlpha()) {
                if (isVisible) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    calcTime.setVisibility(View.VISIBLE);
                }
                progressBar.animate().alpha(progressTargetAlpha)
                        .setDuration(shortAnimationDuration)
                        .withEndAction(isVisible ? null : () -> progressBar.setVisibility(View.INVISIBLE))
                        .start();

                calcTime.animate().alpha(1 - progressTargetAlpha)
                        .withEndAction(isVisible ? () -> calcTime.setVisibility(View.INVISIBLE) : null)
                        .setDuration(shortAnimationDuration)
                        .start();
            }
        }
    }

    static class OperationsHolder extends RecyclerView.ViewHolder {

        final private TextView operation;

        public OperationsHolder(@NonNull View itemView) {
            super(itemView);
            operation = itemView.findViewById(R.id.recyclerViewOperation);
        }

    }
}
