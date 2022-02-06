package ua.com.foxminded.collectionsandmaps;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CollectionsBenchmarkTest {

    private Benchmark collectionsBenchmark;
    private List<Items> collectionsList;
    private int size;

    @Before
    public void setUp() {
        this.collectionsBenchmark = new CollectionsBenchmark();
        this.collectionsList = collectionsBenchmark.generateCollectionItems(true);
        this.size = 1000000;
    }

    @Test
    public void testMeasureTime() {
        Items collectionItem = collectionsBenchmark.measureTime(collectionsList.get(0), size);
        //check if no exception will be thrown
        Float.parseFloat(collectionItem.calcResults);
        assertFalse(collectionItem.progressBarFlag);
    }

    @Test
    public void testGenerateCollectionItems() {
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
        Items item0 = new Items(0, R.string.arrayList, "0", false);
        collectionsBenchmark.measureTime(item0, size);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidName() {
        Items item0 = new Items(R.string.addToStart, 0, "0", false);
        collectionsBenchmark.measureTime(item0, size);
    }
}