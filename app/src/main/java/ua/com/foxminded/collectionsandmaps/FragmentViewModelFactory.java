package ua.com.foxminded.collectionsandmaps;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class FragmentViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final int type;

    public FragmentViewModelFactory(int type) {
        super();
        this.type = type;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FragmentViewModel.class)) {
            return (T) new FragmentViewModel(type);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}