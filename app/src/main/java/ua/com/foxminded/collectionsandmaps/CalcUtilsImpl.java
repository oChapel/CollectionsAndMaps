package ua.com.foxminded.collectionsandmaps;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CalcUtilsImpl {

    static class CollectionsCalcUtils implements CalcUtils {

        @Inject
        public CollectionsCalcUtils() {
        }

        @Override
        public Items measureTime(List<Items> list, int position, int benchmarkSize) {
            return CollectionsOperations.measureTime(list.get(position), benchmarkSize);
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

    static class MapsCalcUtils implements CalcUtils {

        @Inject
        public MapsCalcUtils() {
        }

        @Override
        public Items measureTime(List<Items> list, int position, int benchmarkSize) {
            return MapsOperations.measureTime(list.get(position), benchmarkSize);
        }

        @Override
        public List<Items> generateCollectionItems(boolean visibilityFlag) {
            final List<Items> items = new ArrayList<>();
            //final String naMS = getContext().getResources().getString(R.string.NAms);
            int[] idArrOperations = new int[]{R.string.addToMap, R.string.searchByKey,
                    R.string.remFromMap};
            int[] idArrType  = new int[]{R.string.treeMap, R.string.hashMap};
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
}