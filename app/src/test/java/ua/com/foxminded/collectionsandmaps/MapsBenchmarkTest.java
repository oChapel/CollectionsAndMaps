package ua.com.foxminded.collectionsandmaps;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MapsBenchmarkTest {

    private Benchmark mapsBenchmark;
    private List<Items> mapsList;
    private int size;

    @Before
    public void setUp() {
        this.mapsBenchmark = new MapsBenchmark();
        this.mapsList = mapsBenchmark.generateCollectionItems(true);
        this.size = 1000000;
    }

    @Test
    public void testMeasureTime() {
        Items mapItem = mapsBenchmark.measureTime(mapsList.get(0), size);
        //check if no exception will be thrown
        Float.parseFloat(mapItem.calcResults);
        assertFalse(mapItem.progressBarFlag);
    }

    @Test
    public void testGenerateCollectionItems() {
        assertEquals(
                mapsList.get(3),
                new Items(R.string.searchByKey, R.string.hashMap, "N/A", true)
        );
    }

    @Test
    public void testGenerateCollectionItemsSize() {
        assertEquals(6, mapsBenchmark.generateCollectionItems(true).size());
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidOperation() {
        Items item1 = new Items(0, R.string.hashMap, "0", false);
        mapsBenchmark.measureTime(item1, size);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidName() {
        Items item1 = new Items(R.string.searchByKey, 0, "0", false);
        mapsBenchmark.measureTime(item1, size);
    }
}