package ua.com.foxminded.collectionsandmaps;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class ItemsDiffUtilCallback extends DiffUtil.Callback {

    private final List<Items> oldList;
    private final List<Items> newList;

    public ItemsDiffUtilCallback(List<Items> oldList, List<Items> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Items oldItem = oldList.get(oldItemPosition);
        Items newItem = newList.get(newItemPosition);
        return oldItem.name == newItem.name && oldItem.operation == newItem.operation;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Items oldItem = oldList.get(oldItemPosition);
        Items newItem = newList.get(newItemPosition);
        return oldItem.equals(newItem);
    }
}