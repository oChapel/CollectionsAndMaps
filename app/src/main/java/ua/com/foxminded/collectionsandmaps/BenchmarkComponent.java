package ua.com.foxminded.collectionsandmaps;

import dagger.Component;

@Component(modules = BenchmarkModule.class)
public interface BenchmarkComponent {
    void inject(CollectionsViewModelFactory factory);
 }