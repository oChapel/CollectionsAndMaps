package ua.com.foxminded.collectionsandmaps.models;

import dagger.Component;
import ua.com.foxminded.collectionsandmaps.ui.benchmark.CollectionsViewModelFactory;

@Component(modules = TestModule.class)
public interface TestComponent {
    void inject(CollectionsViewModelFactory factory);
}
