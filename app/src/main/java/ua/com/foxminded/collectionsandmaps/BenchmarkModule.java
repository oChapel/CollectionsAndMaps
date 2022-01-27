package ua.com.foxminded.collectionsandmaps;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class BenchmarkModule {

    @Provides
    @Named("collectionsBenchmark")
    public Benchmark provideCollectionsBenchmark() {
        return new CollectionsBenchmark();
    }

    @Provides
    @Named("mapsBenchmark")
    public Benchmark provideMapsBenchmark() {
        return new MapsBenchmark();
    }
}