package ua.com.foxminded.collectionsandmaps.ui.benchmark;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;
import javax.inject.Named;

import ua.com.foxminded.collectionsandmaps.models.BenchmarkComponent;
import ua.com.foxminded.collectionsandmaps.models.DaggerBenchmarkComponent;
import ua.com.foxminded.collectionsandmaps.models.DaggerTestComponent;
import ua.com.foxminded.collectionsandmaps.models.TestComponent;
import ua.com.foxminded.collectionsandmaps.models.benchmark.Benchmark;
import ua.com.foxminded.collectionsandmaps.ui.App;

public class CollectionsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final int type;
    private final int componentInt = App.getComponentInt();

    @Inject
    @Named("collectionsBenchmark")
    Benchmark collectionsBenchmark;

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
        if (componentInt == 0) {
            final BenchmarkComponent component = DaggerBenchmarkComponent.create();
            component.inject(this);
        } else if (componentInt == 1) {
            final TestComponent component = DaggerTestComponent.create();
            component.inject(this);
        } else throw new RuntimeException("Unsupported component integer: " + componentInt);

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
