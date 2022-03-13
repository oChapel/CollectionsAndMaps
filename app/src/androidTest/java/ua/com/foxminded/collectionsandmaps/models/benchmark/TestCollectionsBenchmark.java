package ua.com.foxminded.collectionsandmaps.models.benchmark;

public class TestCollectionsBenchmark extends CollectionsBenchmark{

    @Override
    public Items measureTime(Items item, int size) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Items(item.operation, item.name, "1000", false);
    }
}
