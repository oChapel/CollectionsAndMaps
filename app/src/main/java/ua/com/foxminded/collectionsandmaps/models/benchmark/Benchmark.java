package ua.com.foxminded.collectionsandmaps.models.benchmark;

import java.util.List;

public interface Benchmark {

    Items measureTime(Items item, int benchmarkSize);

    List<Items> generateCollectionItems(boolean visibilityFlag);

    int getSpanCount();

}
