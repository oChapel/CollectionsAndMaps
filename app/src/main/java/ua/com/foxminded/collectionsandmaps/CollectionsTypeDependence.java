package ua.com.foxminded.collectionsandmaps;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CollectionsTypeDependence implements CalcTypeDependence {

    @Inject
    public CollectionsTypeDependence() {
    }

    @Override
    public Items measureTime(Items item, int benchmarkSize) {
        return CollectionsOperations.measureTime(item, benchmarkSize);
    }

    @Override
    public List<Items> generateCollectionItems(boolean visibilityFlag) {
        final List<Items> items = new ArrayList<>();
        //final String naMS = getContext().getResources().getString(R.string.NAms);
        int[] idArrOperations = new int[]{R.string.addToStart, R.string.addToMiddle,
                R.string.addToEnd, R.string.searchByValue, R.string.remFromStart,
                R.string.remFromMiddle, R.string.remFromEnd};
        int[] idArrType = new int[]{R.string.arrayList, R.string.linkedList, R.string.copyOnWriterList};
        for (int operation : idArrOperations) {
            for (int listType : idArrType) {
                items.add(new Items(operation, listType, "N/A"/*naMS*/, visibilityFlag));
            }
        }
        return items;
    }

    @Override
    public int getSpanCount() {
        return 3;
    }
}