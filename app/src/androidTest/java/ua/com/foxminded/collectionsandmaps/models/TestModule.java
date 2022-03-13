package ua.com.foxminded.collectionsandmaps.models;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import ua.com.foxminded.collectionsandmaps.models.benchmark.Benchmark;
import ua.com.foxminded.collectionsandmaps.models.benchmark.TestCollectionsBenchmark;
import ua.com.foxminded.collectionsandmaps.models.benchmark.TestMapsBenchmark;

@Module
public class TestModule {

    @Provides
    @Named("collectionsBenchmark")
    public Benchmark provideTestCollectionsBenchmark() {
        return new TestCollectionsBenchmark();
    }

    @Provides
    @Named("mapsBenchmark")
    public Benchmark provideTestMapsBenchmark() {
        return new TestMapsBenchmark();
    }
}
