package ua.com.foxminded.collectionsandmaps;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CollectionsBenchmarkTest {

    private Benchmark collectionsBenchmark;

    @Before
    public void setUp() {
        this.collectionsBenchmark = new CollectionsBenchmark();
    }

    @Test
    public void testMeasureTime() {
        List<Items> collectionsList = collectionsBenchmark.generateCollectionItems(true);
        int size= 1000000;
        Items collectionItem = collectionsBenchmark.measureTime(collectionsList.get(0), size);
        assertFalse(collectionItem.progressBarFlag);
    }

    @Test
    public void testGenerateCollectionItems() {
        List<Items> collectionsList = collectionsBenchmark.generateCollectionItems(true);
        assertEquals(
                collectionsList.get(0),
                new Items(R.string.addToStart, R.string.arrayList, "N/A", true)
        );
    }

    @Test
    public void testGenerateCollectionItemsSize() {
        assertEquals(21, collectionsBenchmark.generateCollectionItems(true).size());
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidOperation() {
        int size= 1000000;
        Items item0 = new Items(0, R.string.arrayList, "0", false);
        collectionsBenchmark.measureTime(item0, size);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidName() {
        int size= 1000000;
        Items item0 = new Items(R.string.addToStart, 0, "0", false);
        collectionsBenchmark.measureTime(item0, size);
    }
}