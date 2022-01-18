package ua.com.foxminded.collectionsandmaps;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MapsTypeDependence implements CalcTypeDependence {

    @Inject
    public MapsTypeDependence() {
    }

    @Override
    public Items measureTime(Items item, int benchmarkSize) {
        return MapsOperations.measureTime(item, benchmarkSize);
    }

    @Override
    public List<Items> generateCollectionItems(boolean visibilityFlag) {
        final List<Items> items = new ArrayList<>();
        //final String naMS = getContext().getResources().getString(R.string.NAms);
        int[] idArrOperations = new int[]{R.string.addToMap, R.string.searchByKey,
                R.string.remFromMap};
        int[] idArrType = new int[]{R.string.treeMap, R.string.hashMap};
        for (int operation : idArrOperations) {
            for (int listType : idArrType) {
                items.add(new Items(operation, listType, "N/A"/*naMS*/, visibilityFlag));
            }
        }
        return items;
    }

    @Override
    public int getSpanCount() {
        return 2;
    }
}