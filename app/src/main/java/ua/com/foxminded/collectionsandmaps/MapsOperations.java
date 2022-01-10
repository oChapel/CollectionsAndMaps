package ua.com.foxminded.collectionsandmaps;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class MapsOperations {

    private static final Random randomNumber = new Random();

    public static Items measureTime(Items item, int size) {
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

    public static void fillNewMap(int size, Map<Integer, Integer> map) {
        for (int i = 0; i < size; i++) {
            map.put(i, 0);
        }
    }

    private static float addingToMap(int size, Map<Integer, Integer> map) {
        long start = System.nanoTime();
        map.put(size, randomNumber.nextInt(size));
        long end = System.nanoTime() - start;

        return (float) end / 1000000;
    }

    private static float searchByKey(int size, Map<Integer, Integer> map) {
        long start = System.nanoTime();
        map.get(randomNumber.nextInt(size));
        long end = System.nanoTime() - start;

        return (float) end / 1000000;
    }

    private static float removingFromMap(int size, Map<Integer, Integer> map) {
        long start = System.nanoTime();
        map.remove(randomNumber.nextInt(size));
        long end = System.nanoTime() - start;

        return (float) end / 1000000;
    }
}
