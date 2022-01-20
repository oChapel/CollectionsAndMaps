package ua.com.foxminded.collectionsandmaps;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

public class CollectionsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final int type;
    private final BenchmarkComponent component = DaggerBenchmarkComponent.create();

    @Inject
    @Named("collectionsBenchmark") Benchmark collectionsBenchmark;

    @Inject
    @Named("mapsBenchmark") Benchmark mapsBenchmark;

    public CollectionsViewModelFactory(int type) {
        super();
        this.type = type;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        component.inject(this);
        if (modelClass.isAssignableFrom(CollectionsViewModel.class)) {
            if (type == 0) {
                return (T) new CollectionsViewModel(collectionsBenchmark);
            } else if (type == 1) {
                return (T) new CollectionsViewModel(mapsBenchmark);
            } else throw new RuntimeException("Unsupported type: " + type);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}