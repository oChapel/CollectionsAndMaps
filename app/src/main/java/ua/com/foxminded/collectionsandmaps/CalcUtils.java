package ua.com.foxminded.collectionsandmaps;

import java.util.List;

public interface CalcUtils {

    Items measureTime(List<Items> list, int position, int benchmarkSize);

    List<Items> generateCollectionItems(boolean visibilityFlag);

    int getSpanCount();

}