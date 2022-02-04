package ua.com.foxminded.collectionsandmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class MapsBenchmark implements Benchmark {

    private final Random randomNumber = new Random();

    public MapsBenchmark() {
    }

    @Override
    public Items measureTime(Items item, int size) {
        final Map<Integer, Integer> map;
        if (item.name == R.string.treeMap) {
            map = new TreeMap<>();
        } else if (item.name == R.string.hashMap) {
            map = new HashMap<>();
        } else throw new RuntimeException("Unknown type: " + item.name);
        fillNewMap(size, map);
        float time;
        if (item.operation == R.string.addToMap) {
            time = addingToMap(size, map);
        } else if (item.operation == R.string.searchByKey) {
            time = searchByKey(size, map);
        } else if (item.operation == R.string.remFromMap) {
            time = removingFromMap(size, map);
        } else throw new RuntimeException("Unknown operation: " + item.operation);
        return new Items(item.operation, item.name, String.valueOf(time), false);
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

    private static void fillNewMap(int size, Map<Integer, Integer> map) {
        for (int i = 0; i < size; i++) {
            map.put(i, 0);
        }
    }

    private float addingToMap(int size, Map<Integer, Integer> map) {
        long start = System.nanoTime();
        map.put(size, randomNumber.nextInt(size));
        long end = System.nanoTime() - start;

        return (float) end / 1000000;
    }

    private float searchByKey(int size, Map<Integer, Integer> map) {
        long start = System.nanoTime();
        map.get(randomNumber.nextInt(size));
        long end = System.nanoTime() - start;

        return (float) end / 1000000;
    }

    private float removingFromMap(int size, Map<Integer, Integer> map) {
        long start = System.nanoTime();
        map.remove(randomNumber.nextInt(size));
        long end = System.nanoTime() - start;

        return (float) end / 1000000;
    }
}