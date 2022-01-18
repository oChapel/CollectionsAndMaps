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
            if (type == 0) {
                return (T) new CollectionsViewModel(new CalcUtilsImpl.CollectionsCalcUtils());
            } else if (type == 1) {
                return (T) new CollectionsViewModel(new CalcUtilsImpl.MapsCalcUtils());
            } else throw new RuntimeException("Unsupported type: " + type);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}