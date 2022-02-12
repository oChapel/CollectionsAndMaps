package ua.com.foxminded.collectionsandmaps.models;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.foxminded.collectionsandmaps.models.benchmark.Benchmark;
import ua.com.foxminded.collectionsandmaps.models.benchmark.CollectionsBenchmark;
import ua.com.foxminded.collectionsandmaps.models.benchmark.MapsBenchmark;

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
