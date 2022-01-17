package ua.com.foxminded.collectionsandmaps;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.NonNull;

public class CollectionsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final int type;

    public CollectionsViewModelFactory(int type) {
        super();
        this.type = type;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CollectionsViewModel.class)) {
            return (T) new CollectionsViewModel(type);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}