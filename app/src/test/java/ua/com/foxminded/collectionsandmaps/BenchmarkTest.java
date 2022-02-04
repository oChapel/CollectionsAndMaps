package ua.com.foxminded.collectionsandmaps;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BenchmarkTest {

    private static Benchmark collectionsBenchmark;
    private static Benchmark mapsBenchmark;
    private static List<Items> collectionsList;
    private static List<Items> mapsList;
    private static int size;

    @BeforeClass
    public static void setUp() {
        collectionsBenchmark = new CollectionsBenchmark();
        mapsBenchmark = new MapsBenchmark();
        collectionsList = collectionsBenchmark.generateCollectionItems(true);
        mapsList = mapsBenchmark.generateCollectionItems(true);
        size = 1000000;
    }

    @Test
    public void testMeasureTime() {
        Items collectionItem = collectionsBenchmark.measureTime(collectionsList.get(0), size);
        Items mapItem = mapsBenchmark.measureTime(mapsList.get(0), size);
        //check if no exception will be thrown
        Float.parseFloat(collectionItem.calcResults);
        Float.parseFloat(mapItem.calcResults);
        assertFalse(collectionItem.progressBarFlag);
        assertFalse(mapItem.progressBarFlag);
    }

    @Test
    public void testGenerateCollectionItems() {
        assertEquals(
                collectionsList.get(0),
                new Items(R.string.addToStart, R.string.arrayList, "N/A", true)
        );
        assertEquals(
                mapsList.get(3),
                new Items(R.string.searchByKey, R.string.hashMap, "N/A", true)
        );
    }

    @Test
    public void testGenerateCollectionItemsSize() {
        assertEquals(21, collectionsBenchmark.generateCollectionItems(true).size());
        assertEquals(6, mapsBenchmark.generateCollectionItems(true).size());
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidOperation() {
        Items item0 = new Items(0, R.string.arrayList, "0", false);
        Items item1 = new Items(0, R.string.hashMap, "0", false);
        collectionsBenchmark.measureTime(item0, size);
        mapsBenchmark.measureTime(item1, size);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidName() {
        Items item0 = new Items(R.string.addToStart, 0, "0", false);
        Items item1 = new Items(R.string.searchByKey, 0, "0", false);
        collectionsBenchmark.measureTime(item0, size);
        mapsBenchmark.measureTime(item1, size);
    }
}