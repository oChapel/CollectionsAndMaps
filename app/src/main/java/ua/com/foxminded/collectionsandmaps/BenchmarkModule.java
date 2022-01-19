package ua.com.foxminded.collectionsandmaps;

import dagger.Module;
import dagger.Provides;

@Module
public class BenchmarkModule {

    @Provides
    public CollectionsBenchmark provideCollectionsBenchmark() {
        return new CollectionsBenchmark();
    }

    @Provides
    public MapsBenchmark provideMapsBenchmark() {
        return new MapsBenchmark();
    }
}