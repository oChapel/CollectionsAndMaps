package ua.com.foxminded.collectionsandmaps.models;

import dagger.Component;
import ua.com.foxminded.collectionsandmaps.ui.benchmark.CollectionsViewModelFactory;

@Component(modules = BenchmarkModule.class)
public interface BenchmarkComponent {
    void inject(CollectionsViewModelFactory factory);
 }
