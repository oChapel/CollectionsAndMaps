package ua.com.foxminded.collectionsandmaps.models.benchmark;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import java.util.List;

import ua.com.foxminded.collectionsandmaps.R;

public class CollectionsBenchmarkTest {

    private final Benchmark collectionsBenchmark = new CollectionsBenchmark();

    @Test
    public void testMeasureTime() {
        final List<Items> collectionsList = collectionsBenchmark.generateCollectionItems(true);
        final Items collectionItem = collectionsBenchmark.measureTime(collectionsList.get(0), 1000000);
        assertFalse(collectionItem.progressBarFlag);
    }

    @Test
    public void testGenerateCollectionItems() {
        final List<Items> collectionsList = collectionsBenchmark.generateCollectionItems(true);

        final int[] idArrOperations = new int[]{R.string.addToStart, R.string.addToMiddle,
                R.string.addToEnd, R.string.searchByValue, R.string.remFromStart,
                R.string.remFromMiddle, R.string.remFromEnd};
        final int[] idArrType = new int[]{R.string.arrayList, R.string.linkedList, R.string.copyOnWriterList};

        int i = 0;
        for (int operation : idArrOperations) {
            for (int listType : idArrType) {
                assertEquals(
                        collectionsList.get(i),
                        new Items(operation, listType, "N/A", true)
                );
                i++;
            }
        }
    }

    @Test
    public void testGenerateCollectionItemsSize() {
        assertEquals(21, collectionsBenchmark.generateCollectionItems(true).size());
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidOperation() {
        final Items item0 = new Items(0, R.string.arrayList, "0", false);
        collectionsBenchmark.measureTime(item0, 1000000);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidName() {
        final Items item0 = new Items(R.string.addToStart, 0, "0", false);
        collectionsBenchmark.measureTime(item0, 1000000);
    }
}
