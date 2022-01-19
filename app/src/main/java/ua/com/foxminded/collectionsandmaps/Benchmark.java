package ua.com.foxminded.collectionsandmaps;

import java.util.List;

public interface Benchmark {

    Items measureTime(Items item, int benchmarkSize);

    List<Items> generateCollectionItems(boolean visibilityFlag);

    int getSpanCount();

}