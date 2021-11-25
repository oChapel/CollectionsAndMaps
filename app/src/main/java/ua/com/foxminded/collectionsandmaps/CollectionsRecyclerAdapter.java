package ua.com.foxminded.collectionsandmaps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class CollectionsRecyclerAdapter extends RecyclerView.Adapter<CollectionsRecyclerAdapter.CollectionsHolder> {

    ArrayList<String> data;

    public CollectionsRecyclerAdapter() {}

    @NonNull
    @Override
    public CollectionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_collection_item, parent, false);
        return new CollectionsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionsHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 18;
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

        public void bind(int position) {
            data = new ArrayList<>(Arrays.asList(itemView.getContext().getResources().getStringArray(R.array.strArrayTypes)));
            arrayType.setText(data.get(position));
            calcTime.setText(R.string.NAms);
        }
    }
}